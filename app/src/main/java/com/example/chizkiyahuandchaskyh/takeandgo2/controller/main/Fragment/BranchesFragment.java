package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;

import java.util.ArrayList;


public class BranchesFragment extends Fragment {


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
        return view;
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

}
