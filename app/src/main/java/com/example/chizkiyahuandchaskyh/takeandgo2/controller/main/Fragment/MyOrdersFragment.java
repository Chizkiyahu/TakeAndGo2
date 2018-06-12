package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SharedElementCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.Login.TryUserPass;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;

import java.util.ArrayList;


public class MyOrdersFragment extends Fragment {


    protected DataSource dataSource  = BackendFactory.getDataSource();
    protected ListView listView;
    protected ArrayList<Order> orderArrayList = new ArrayList<>();
    protected ArrayAdapter<Order> adapter = null;
    LinearLayout orderLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCreateNew();
            }
        });

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPostExecute(Void aVoid) {

                getListViewAdapter().clear();
                getListViewAdapter().addAll(orderArrayList);
                getListViewAdapter().notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                orderArrayList = new ArrayList<>(dataSource.getOrdersList());
                return null;
            }

        }.execute();
        listView = view.findViewById(R.id.order_view);
        adapter = null;
        getListViewAdapter();
        listView.setAdapter(getListViewAdapter());
        getListViewAdapter().notifyDataSetChanged();


        return view;
    }

    private void onClickCreateNew() {

    }

    protected ArrayAdapter getListViewAdapter() {

        if (adapter == null) {
            adapter = new ArrayAdapter<Order>( getContext(), R.layout.order_line, orderArrayList) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                    if (convertView == null) {
                        convertView = View.inflate( getContext(),R.layout.order_line,null );
                    }
                    Order order = this.getItem(position);
                    TextView idView = convertView.findViewById( R.id.order_id );
                    TextView carIdView = convertView.findViewById( R.id.car_id );
                    TextView statusView = convertView.findViewById( R.id.order_status );

                    idView.setText("Order ID: "  + order.getOrderID());
                    carIdView.setText("Car ID: " +  order.getCarID());
                    statusView.setText("Order ID " + order.getStatus());
                    return convertView;
                }
            };

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    OrderInfoFragment orderInfoFragment = new OrderInfoFragment();
                    Bundle bundle = new Bundle();
                    Order order = (Order) parent.getItemAtPosition(position);
                    bundle.putInt("orderID",order.getOrderID());
                    orderInfoFragment.setArguments(bundle);
                    changeFragement(orderInfoFragment);
                }
            });
        }
        return adapter;
    }



    public  void changeFragement(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }



}
