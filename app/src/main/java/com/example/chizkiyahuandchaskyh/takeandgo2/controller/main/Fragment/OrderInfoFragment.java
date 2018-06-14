package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;

import java.util.Objects;


public class OrderInfoFragment extends Fragment {

    AutoCompleteTextView orderIdView, carIdView, customerIdView, statusView, startView, endView, starKmView, endKmView;
    AutoCompleteTextView returnNonFilledTankView, quantityOfLitersPerBillView, amountToPayView;

    protected DataSource dataSource  = BackendFactory.getDataSource();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_info, container, false);
        final int orderID = Objects.requireNonNull(getArguments()).getInt("orderID");
        init(view);
        new AsyncTask<Integer, Void, Order>(){
            @Override
            protected void onPostExecute(Order order) {
                super.onPostExecute(order);
                orderIdView.setText(Integer.toString(order.getOrderID()));
                carIdView.setText(Integer.toString(order.getCarID()));
                customerIdView.setText(Integer.toString(order.getCustomerID()));
                statusView.setText(order.getStatus().toString());
                startView.setText(order.getStart().toString());
                endView.setText(order.getEnd().toString());
                starKmView.setText(Integer.toString(order.getStartKM()));
                endKmView.setText(Integer.toString(order.getEndKM()));
                returnNonFilledTankView.setText(String.valueOf(!order.getIsReturnNonFilledTank()));
                if (order.getIsReturnNonFilledTank()){
                    quantityOfLitersPerBillView.setVisibility(View.VISIBLE);
                }
                quantityOfLitersPerBillView.setText(Integer.toString(order.getQuantityOfLitersPerBill()));
                amountToPayView.setText(Integer.toString(order.getAmountToPay()));
            }

            @Override
            protected Order doInBackground(Integer... integers) {
                return dataSource.getOrderById(integers[0]);
            }
        }.execute(orderID);

        return view;
    }

    void init(View view){
        orderIdView = view.findViewById(R.id.order_info_order_id);
        carIdView = view.findViewById(R.id.order_info_car_id);
        customerIdView = view.findViewById(R.id.order_info_customer_id);
        statusView = view.findViewById(R.id.order_info_status);
        startView = view.findViewById(R.id.order_info_start);
        endView = view.findViewById(R.id.order_info_end);
        starKmView = view.findViewById(R.id.order_info_start_km);
        customerIdView = view.findViewById(R.id.order_info_customer_id);
        endKmView = view.findViewById(R.id.order_info_end_km);
        returnNonFilledTankView = view.findViewById(R.id.order_info_return_non_filled_tank);
        quantityOfLitersPerBillView = view.findViewById(R.id.order_info_quantity_of_liters_per_bill);
        amountToPayView = view.findViewById(R.id.order_info_amount_to_pay);
    }







}
