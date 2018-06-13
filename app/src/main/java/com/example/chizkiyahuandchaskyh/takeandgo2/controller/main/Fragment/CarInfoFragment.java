package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Car;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.CarModel;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.utils.Constants;


public class CarInfoFragment extends Fragment {

    protected DataSource dataSource  = BackendFactory.getDataSource();
    Car car ;
    CarModel carModel;
    Branch branch;
    TextView carIdView, branchIdView, kmView, modelIdView, manufacturerMameView ,modelNameView ;
    TextView gearBoxView, addressView, numParkingSpacesView, engineCapacityView, seatingView;
    Button addOrderButton;

    @SuppressLint("SetTextI18n")
    void init(View view, Car car){
        carIdView = view.findViewById(R.id.car_info_car_id);
        branchIdView = view.findViewById(R.id.car_info_branch_id);
        kmView = view.findViewById(R.id.car_info_km);
        modelIdView = view.findViewById(R.id.car_info_model_id);
        manufacturerMameView = view.findViewById(R.id.car_info_manufacturer_name);
        modelNameView = view.findViewById(R.id.car_info_model_name);
        gearBoxView = view.findViewById(R.id.car_info_gear_box);
        addressView = view.findViewById(R.id.car_info_address);
        numParkingSpacesView = view.findViewById(R.id.car_info_num_parking_spaces);
        engineCapacityView = view.findViewById(R.id.car_info_engine_capacity);
        seatingView = view.findViewById(R.id.car_info_seating);


        carIdView.setText(getString(R.string.car_id__) + car.getId());
        branchIdView.setText(getString(R.string.branch_number_) + car.getBranchID());
        kmView.setText(getString(R.string.km__) + car.getKm());
        modelIdView.setText(getString(R.string.model_id_) + carModel.getCodeModel());
        manufacturerMameView.setText(getString(R.string.manufacturer) + carModel.getManufacturerName());
        modelNameView.setText(getString(R.string.model) + carModel.getModelName());
        gearBoxView.setText(getString(R.string.gear_box_) + carModel.getGearBox().name());
        addressView.setText(getString(R.string.address) + branch.getAddress().toString());
        numParkingSpacesView.setText(getString(R.string.parking_spaces) + branch.getNumParkingSpaces());
        engineCapacityView.setText(getString(R.string.engine_capacity_) + carModel.getEngineCapacity());
        seatingView.setText(getString(R.string.seating_) + carModel.getSeating());

        addOrderButton.setEnabled(true);
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_info, container, false);
        addOrderButton = view.findViewById(R.id.car_info_add_order);
        addOrderButton.setEnabled(false);
        new AsyncTask<View, Void, View>(){

            @Override
            protected void onPostExecute(View view) {
                init(view, car);
            }
            @Override
            protected View doInBackground(View... views) {

                try {
                    car = dataSource.getCarByID(getArguments().getInt("carID"));
                    carModel = dataSource.getCarModelById(car.getModelID());
                    branch = dataSource.getBranchByID(car.getBranchID());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(Constants.Log.TAG, e.getMessage());
                }
                return views[0];
            }

        }.execute(view);


        addOrderButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                OrderFragment orderFragment = new OrderFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("carID",car.getId());
                bundle.putBoolean("create", true);
                orderFragment.setArguments(bundle);
                changeFragment(orderFragment);
            }
        });


        return view;

    }

    public  void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
