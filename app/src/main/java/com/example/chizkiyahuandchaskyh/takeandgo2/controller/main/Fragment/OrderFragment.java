package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;


public class OrderFragment extends Fragment {
    protected DataSource dataSource  = BackendFactory.getDataSource();
    Button orderStartButton, orderEndButton, createNewOrderButton;
    AutoCompleteTextView  orderIdView, carIdView, startKmView, endKmView;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        final Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date
        init(view);
        createNewOrderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    if (orderStartButton.getText().toString().equals("")){
                        throw new Exception("You must select a start date");
                    }
                    if (orderEndButton.getText().toString().equals("")){
                        throw new Exception("You must select a end date");
                    }
                    Date startDate = sdf.parse(orderStartButton.getText().toString());
                    Date endDate = sdf.parse(orderEndButton.getText().toString());
                    if (endDate.before(startDate)){
                        throw new Exception("the end Date can't before start Date");
                    }
                    if (!startKmView.getText().toString().equals("")){
                        if (!endKmView.getText().toString().equals("")) {
                            if (Integer.parseInt(startKmView.getText().toString()) > Integer.parseInt(endKmView.getText().toString())) {
                                throw new Exception("the start KM can't big from end KM");
                            }
                        }
                    }else if (!endKmView.getText().toString().equals("")){
                        throw new Exception("Can not enter end km without start km");
                    }

                    SharedPreferences prefs = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
                    int customerId = prefs.getInt("customerId",0);

                   final Order order = new Order(customerId,
                            getArguments().getInt("carID"),
                            Order.STATUS.OPEN,
                            startDate,
                            endDate,
                            Integer.parseInt( (startKmView.getText().toString().equals(""))? "-1" :startKmView.getText().toString() ),
                            Integer.parseInt( (endKmView.getText().toString().equals(""))? "-1" :endKmView.getText().toString() ));

                    new AsyncTask<Order, Void,Void>(){


                        @Override
                        protected void onPostExecute(Void aVoid) {
                                changeFragment(new MyOrdersFragment());
                        }

                        @Override
                        protected Void doInBackground(Order... orders) {
                            try {
                                dataSource.addOrder(orders[0]);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute(order);

                }catch (Exception e) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
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
        });

        orderStartButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                        startDatePickerListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });

        orderEndButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                        endDatePickerListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();
            }
        });

        return view;
    }


    void init(View view){
        orderStartButton = view.findViewById(R.id.order_start);
        orderEndButton = view.findViewById(R.id.order_end);
        createNewOrderButton = view.findViewById(R.id.create_new_order);
        orderIdView = view.findViewById(R.id.order_id);
        carIdView = view.findViewById(R.id.order_car_id);
        startKmView = view.findViewById(R.id.order_start_km);
        endKmView = view.findViewById(R.id.order_end_km);
        Integer carid = getArguments().getInt("carID");
        carIdView.setText(carid.toString());
    }


    private DatePickerDialog.OnDateSetListener startDatePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay ) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            orderStartButton.setText(year1 + "-" + month1 + "-" +day1);


        }
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay ) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            orderEndButton.setText(year1 + "-" + month1 + "-" +day1);

        }
    };

    public  void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}
