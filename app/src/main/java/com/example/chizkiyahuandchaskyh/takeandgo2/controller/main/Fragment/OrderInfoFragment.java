package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;


public class OrderInfoFragment extends Fragment {

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
        int orderID = getArguments().getInt("orderID");
        new AsyncTask<Integer, Void, Order>(){
            @Override
            protected void onPostExecute(Order order) {
                super.onPostExecute(order);

            }

            @Override
            protected Order doInBackground(Integer... integers) {
                return dataSource.getOrderById(integers[0]);
            }
        }.execute();

        int test = 6;
        return view;
    }





}
