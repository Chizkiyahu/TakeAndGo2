package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class BranchFragment extends BottomSheetDialogFragment {

    private static final String ARG_BRANCH_ID = "branchID";
    protected DataSource dataSource  = BackendFactory.getDataSource();
    private int branchId = 0;
    private Branch branch;

    public static BranchFragment newInstance(int id) {
        final BranchFragment fragment = new BranchFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_BRANCH_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        branchId = getArguments().getInt(ARG_BRANCH_ID);
        return inflater.inflate(R.layout.fragment_branch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(branch != null) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("google.navigation:q=" + branch.getAddress().toString()));
                    getActivity().startActivity(intent);
                }
            }
        });

        final SupportMapFragment mapFragment = new SupportMapFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_container, mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();

        final AvailableCarsFragment carsFragment = new AvailableCarsFragment();
        carsFragment.branchId = branchId;
        transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.cars_fragment, carsFragment);
        transaction.addToBackStack(null);
        transaction.commit();


        new  AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPostExecute(Void aVoid) {
                TextView addressTextView = getView().findViewById(R.id.address_text_view);
                addressTextView.setText(branch.getAddress().toString());

                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng coordinates = new LatLng(branch.getAddress().getLatitude(), branch.getAddress().getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(coordinates)
                                .icon(BitmapDescriptorFactory.defaultMarker()));

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(coordinates).zoom(14.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.animateCamera(cameraUpdate);
                        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));

                    }
                });
            }

            @Override
            protected Void doInBackground(Void... voids) {
                branch = dataSource.getBranchByID(branchId);
                return null;
            }

        }.execute();
    }

}