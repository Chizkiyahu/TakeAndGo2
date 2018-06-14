package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;

import java.util.Objects;


public class OrderUpdateFragment extends Fragment {


    AutoCompleteTextView idView, carIdView, startView, endView, quantityOfLitersView;
    Button startKmButton, endKmButton, saveButton, closedButton;
    Spinner filledTankSpinner;
    protected DataSource dataSource  = BackendFactory.getDataSource();


    void init(View view){
        idView = view.findViewById(R.id.order_update_id);
        carIdView = view.findViewById(R.id.order_update_car_id);
        startView = view.findViewById(R.id.order_update_start);
        endView = view.findViewById(R.id.order_update_end);
        quantityOfLitersView = view.findViewById(R.id.order_update_quantity_of_liters);
        startKmButton = view.findViewById(R.id.order_update_start_km);
        endKmButton = view.findViewById(R.id.order_update_end_km);
        filledTankSpinner = view.findViewById(R.id.order_update_filled_tank);
        saveButton = view.findViewById(R.id.order_update_save);
        closedButton = view.findViewById(R.id.order_update_closed);

    }

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


            }

            @Override
            protected Order doInBackground(Integer... integers) {
                return dataSource.getOrderById(integers[0]);
            }
        }.execute(orderID);


        return view;
    }

}
