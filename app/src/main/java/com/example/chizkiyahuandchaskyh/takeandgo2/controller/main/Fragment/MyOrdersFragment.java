package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SharedElementCallback;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;

import java.util.ArrayList;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class MyOrdersFragment extends Fragment implements ItemListDialogFragment.Listener {


    protected DataSource dataSource  = BackendFactory.getDataSource();
    protected ListView listView;
    protected ArrayList<Order> orderArrayList = new ArrayList<>();
    protected ArrayAdapter<Order> adapter = null;
    private Integer customerID = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (customerID == null){
            SharedPreferences prefs =getActivity().getSharedPreferences("UserData",MODE_PRIVATE);
            customerID =prefs.getInt("customerId", 0);
        }
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_filter:
                onClickFilterItem(item.getActionView());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new AvailableCarsFragment());
            }
        });
        listView = view.findViewById(R.id.order_view);

        new AsyncTask<Void, Void, Void>() {


            @Override
            protected void onPostExecute(Void aVoid) {

                getListViewAdapter().clear();
                getListViewAdapter().addAll(orderArrayList);
                getListViewAdapter().notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {

                orderArrayList = new ArrayList<>(dataSource.getOrdersList(customerID));
                return null;
            }

        }.execute();
        adapter = null;
        getListViewAdapter();
        listView.setAdapter(getListViewAdapter());
        getListViewAdapter().notifyDataSetChanged();


        return view;
    }


    protected ArrayAdapter getListViewAdapter() {

        if (adapter == null) {
            adapter = new ArrayAdapter<Order>(Objects.requireNonNull(getContext()), R.layout.order_line, orderArrayList) {
                @SuppressLint("SetTextI18n")
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

                    idView.setText(getString(R.string.Order_ID)  + order.getOrderID());
                    carIdView.setText(getString(R.string.Car_ID) +  order.getCarID());
                    statusView.setText(getString(R.string.Status) + order.getStatus());
                    return convertView;
                }
            };

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Order order = (Order) parent.getItemAtPosition(position);
                    if (order.getStatus().equals(Order.STATUS.CLOSED)){
                        OrderInfoFragment orderInfoFragment = new OrderInfoFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("orderID",order.getOrderID());
                        orderInfoFragment.setArguments(bundle);
                        changeFragment(orderInfoFragment);
                    }else {
                        OrderUpdateFragment orderUpdateFragment = new OrderUpdateFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("orderID",order.getOrderID());
                        orderUpdateFragment.setArguments(bundle);
                        changeFragment(orderUpdateFragment);

                    }

                }
            });
        }
        return adapter;
    }



    public  void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }

    void onClickFilterItem(View view)
    {
        new  AsyncTask<Void, Void, Void>(){
            private String[] mValues;

            @Override
            protected void onPostExecute(Void aVoid) {
                ItemListDialogFragment.newInstance(mValues, "choose city").show(getChildFragmentManager(), "dialog");
            }

            @Override
            protected Void doInBackground(Void... voids) {
                mValues = getValuesForFilter();
                return null;
            }

        }.execute();
    }

    private String[] getValuesForFilter() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("ALL");
        arrayList.add("OPEN");
        arrayList.add("CLOSED");

        return arrayList.toArray(new String[0]);
    }

    @Override
    public void onItemClicked(final String value) {
        new  AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPostExecute(Void aVoid) {

                getListViewAdapter().clear();
                getListViewAdapter().addAll(orderArrayList);
                getListViewAdapter().notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                orderArrayList = getOrdersWithStatus(value);
                return null;
            }

        }.execute();
    }

    private ArrayList<Order> getOrdersWithStatus(String status) {
        ArrayList<Order> res = new ArrayList<>();

        for (Order order: dataSource.getOrdersList(customerID)) {
            if(status.equals("ALL")) {
                res.add(order);
            }
            else if(status.equals("OPEN") && order.getStatus() == Order.STATUS.OPEN) {
                res.add(order);
            }
            else if(status.equals("CLOSED") && order.getStatus() == Order.STATUS.CLOSED) {
                res.add(order);
            }
        }
        return res;
    }
}
