package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
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
import android.widget.Toast;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.MainActivity;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;

import java.util.ArrayList;
import java.util.Set;


public class BranchesFragment extends Fragment implements ItemListDialogFragment.Listener, BranchFragment.Listener {


    protected DataSource dataSource  = BackendFactory.getDataSource();
    protected ListView listView;
    ArrayList<Branch> branchArrayList = new ArrayList<>();
    ArrayAdapter<Branch> adapter = null;


    public BranchesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branches, container, false);
        listView = view.findViewById(R.id.branches_view);
        new  AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPostExecute(Void aVoid) {

                getListViewAdapter().clear();
                getListViewAdapter().addAll(branchArrayList);
                getListViewAdapter().notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                branchArrayList = new ArrayList<>(dataSource.getBranchList());
                return null;
            }

        }.execute();
        getListViewAdapter();
        listView.setAdapter(getListViewAdapter());
        getListViewAdapter().notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Branch branch = branchArrayList.get(position);
                BranchFragment.newInstance(branch.getId()).show(getChildFragmentManager(), "dialog");
            }
        });
        return view;
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

    protected ArrayAdapter getListViewAdapter() {
        if(adapter == null) {
            adapter =  new ArrayAdapter<Branch>(getContext(), R.layout.branch_line, branchArrayList) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(getContext(), R.layout.branch_line, null);
                    }

                    Branch branch = this.getItem(position);

                    TextView addressView = convertView.findViewById(R.id.branch_line_address);
                    TextView numberOfParkingSpacesView = convertView.findViewById(R.id.branch_line_num_parking_spaces);
                    TextView branchIDView = convertView.findViewById(R.id.branch_line_id);

                    addressView.setText("Address: " + branch.getAddress().toString());
                    numberOfParkingSpacesView.setText("Parking spaces:" + branch.getNumParkingSpaces());
                    branchIDView.setText("Branch ID: " + branch.getId());

                    return convertView;
                }
            };
        }
        return adapter;
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

        arrayList.add("All");

        ArrayList<Branch> branches = dataSource.getBranchList();
        for (Branch branch : branches) {
            String city = branch.getAddress().getCity();
            if(!arrayList.contains(city)) {
                arrayList.add(city);
            }
        }

        return arrayList.toArray(new String[0]);
    }

    private ArrayList<Branch> getBranchesInCity(String city) {
        ArrayList<Branch> res = new ArrayList<>();
        ArrayList<Branch> allBranches = dataSource.getBranchList();
        for (Branch branch : allBranches) {
            if (city.equals("All") || branch.getAddress().getCity().equals(city)) {
                res.add(branch);
            }
        }
        return res;
    }


    @Override
    public void onItemClicked(final String value) {
        new  AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPostExecute(Void aVoid) {

                getListViewAdapter().clear();
                getListViewAdapter().addAll(branchArrayList);
                getListViewAdapter().notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                branchArrayList = getBranchesInCity(value);
                return null;
            }

        }.execute();
    }
}
