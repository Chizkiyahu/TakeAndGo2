package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;

import java.util.Date;
import java.util.Objects;


public class OrderUpdateFragment extends Fragment {

     private final static Integer  PRICE_FOR_DAY = 100;
    private final static Integer PRICE_1L_FUEL = 100;

    AutoCompleteTextView idView, carIdView,  quantityOfLitersView, startKmView, endKmView;
    Button startButton, endButton, saveButton, closedButton;
    Spinner filledTankSpinner;
    ArrayAdapter<CharSequence> filledTankAdapter = null;
    protected DataSource dataSource  = BackendFactory.getDataSource();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    void init(View view){
        idView = view.findViewById(R.id.order_update_id);
        carIdView = view.findViewById(R.id.order_update_car_id);
        startButton = view.findViewById(R.id.order_update_start);
        endButton = view.findViewById(R.id.order_update_end);
        quantityOfLitersView = view.findViewById(R.id.order_update_quantity_of_liters);
        startKmView = view.findViewById(R.id.order_update_start_km);
        endKmView = view.findViewById(R.id.order_update_end_km);
        filledTankSpinner = view.findViewById(R.id.order_update_filled_tank);
        saveButton = view.findViewById(R.id.order_update_save);
        closedButton = view.findViewById(R.id.order_update_closed);
        if (filledTankAdapter == null){
            filledTankAdapter =ArrayAdapter.createFromResource(getActivity(),R.array.filled_tank_strings, (android.R.layout.simple_spinner_item));
            filledTankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            filledTankAdapter.setNotifyOnChange(true);
            filledTankSpinner.setAdapter(filledTankAdapter);
            filledTankSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    if (item.equals("Yes")){
                        quantityOfLitersView.setVisibility(View.VISIBLE);
                    }
                    else {
                        quantityOfLitersView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                updateOrderAction( view, Order.STATUS.OPEN);
            }
        });

        closedButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                updateOrderAction( view, Order.STATUS.CLOSED);
            }
        });





    }

    void updateOrderAction(View view, Order.STATUS status){

        try {
            Date startDate = sdf.parse(startButton.getText().toString());
            Date endDate = sdf.parse(endButton.getText().toString());
            if (endDate.before(startDate)){
                throw new Exception("the end Date can't before start Date");
            }

            if (status.equals(Order.STATUS.CLOSED)){
                if (Integer.parseInt(startKmView.getText().toString()) > Integer.parseInt(endKmView.getText().toString())){
                    throw new Exception("the end KM can't bigger than start km");
                }
            }
           Integer quantityOfLiters = Integer.parseInt( (quantityOfLitersView.getText().toString().equals(""))? "-1" :quantityOfLitersView.getText().toString() );
            final Order order = new Order(
                    Integer.parseInt(idView.getText().toString()),
                    status,
                    startDate,
                    endDate,
                    Integer.parseInt( (startKmView.getText().toString().equals(""))? "-1" :startKmView.getText().toString() ),
                    Integer.parseInt( (endKmView.getText().toString().equals(""))? "-1" :endKmView.getText().toString() ),
                    (filledTankSpinner.getSelectedItem().toString().equals("Yes"))? false :true,
                    quantityOfLiters,
                    costCalculation(startDate, endDate,quantityOfLiters )
                    );

            new AsyncTask<Order, Void,Void>(){

                @Override
                protected void onPostExecute(Void aVoid) {
                    changeFragment(new MyOrdersFragment());
                }

                @Override
                protected Void doInBackground(Order... orders) {
                    try {
                        dataSource.updateOrder(orders[0]);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute(order);

        }catch (Exception e) {
            AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(getContext())).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(e.getMessage());
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            e.printStackTrace();
        }


    }

    private Integer costCalculation(Date start, Date end, Integer fule){
        int sum =  0;
        sum += (end.getDay() - start.getDay()) * PRICE_FOR_DAY;
        if (fule > 0){
            sum += PRICE_1L_FUEL * fule;
        }
        return sum;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_update, container, false);
        final int orderID = Objects.requireNonNull(getArguments()).getInt("orderID");
        init(view);

        new AsyncTask<Integer, Void, Order>(){
            @Override
            protected void onPostExecute(Order order) {
                super.onPostExecute(order);
                idView.setText(String.valueOf(order.getOrderID()));
                carIdView.setText(String.valueOf(order.getCarID()));
                startButton.setText(sdf.format(order.getStart()));
                endButton.setText(sdf.format(order.getEnd()));
                if (order.getQuantityOfLitersPerBill() != -1){
                    quantityOfLitersView.setText(String.valueOf(order.getQuantityOfLitersPerBill()));
                }
                if (order.getStartKM() != -1){
                    startKmView.setText(String.valueOf(order.getStartKM()));
                }
                if (order.getEndKM() != -1){
                    endKmView.setText(String.valueOf(order.getEndKM()));
                }
            }

            @Override
            protected Order doInBackground(Integer... integers) {
                return dataSource.getOrderById(integers[0]);
            }
        }.execute(orderID);


        return view;
    }


    public  void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
