package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;


public class OrderInfoFragment extends Fragment {

    EditText orderIdView, carIdView, customerIdView, statusView, startView, endView, starKmView, endKmView;
    EditText returnNonFilledTankView, quantityOfLitersPerBillView, amountToPayView;

    protected DataSource dataSource  = BackendFactory.getDataSource();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_info, container, false);
        final int orderID = getArguments().getInt("orderID");
        init(view);
        new AsyncTask<Integer, Void, Order>(){
            @Override
            protected void onPostExecute(Order order) {
                super.onPostExecute(order);
                orderIdView.setText( order.getOrderID());
                carIdView.setText(order.getCarID());
                customerIdView.setText(order.getCustomerID());
                statusView.setText(order.getStatus().toString());
                startView.setText(order.getStart().toString());
                endView.setText(order.getEnd().toString());
                starKmView.setText(order.getStartKM());
                endKmView.setText(order.getEndKM());
                returnNonFilledTankView.setText(String.valueOf(order.getIsReturnNonFilledTank()));
                quantityOfLitersPerBillView.setText(order.getQuantityOfLitersPerBill());
                amountToPayView.setText(order.getAmountToPay());
            }

            @Override
            protected Order doInBackground(Integer... integers) {
                return dataSource.getOrderById(integers[0]);
            }
        }.execute();

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
