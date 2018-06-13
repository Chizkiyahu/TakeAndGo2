package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Car;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.CarModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AvailableCarsFragment extends Fragment implements ItemListDialogFragment.Listener{


    protected DataSource dataSource  = BackendFactory.getDataSource();
    protected ListView listView;
    ArrayList<Car> carArrayList = new ArrayList<>();
    Map<Integer, CarModel> carModelMap = new HashMap<>();
    ArrayAdapter<Car> adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_available_cars, container, false);
        listView = view.findViewById(R.id.available_cars_view);
        refreshData();
        return view;
    }

    void refreshData(){
        new  AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPostExecute(Void aVoid) {

                getListViewAdapter().clear();
                getListViewAdapter().addAll(carArrayList);
                getListViewAdapter().notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                carArrayList = new ArrayList<>(dataSource.getFreeCarList());
                for (CarModel carModel: dataSource.getCarModelList()){
                    carModelMap.put(carModel.getCodeModel(), carModel);
                }
                return null;
            }

        }.execute();
        adapter = null;
        getListViewAdapter();
        listView.setAdapter(getListViewAdapter());
        getListViewAdapter().notifyDataSetChanged();

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
            adapter =  new ArrayAdapter<Car>(getContext(), R.layout.car_line, carArrayList) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(getContext(), R.layout.car_line, null);
                    }

                    Car car = this.getItem(position);
                    TextView idView = convertView.findViewById( R.id.car_line_id );
                    TextView branchIdView = convertView.findViewById( R.id.car_line_branch_id );
                    TextView kmView = convertView.findViewById( R.id.car_line_km );
                    TextView modelIdView = convertView.findViewById( R.id.car_line_model_id );

                    idView.setText("Car number:"  + car.getId() );
                    branchIdView.setText("Branch ID:" + car.getBranchID() );
                    kmView.setText("KM: " + car.getKm() );
                    modelIdView.setText("Model : " + carModelMap.get(car.getModelID()).toString() );

                    return convertView;
                }
            };
        }
        return adapter;
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshData();
    }

    void onClickFilterItem(View view)
    {
        new  AsyncTask<Void, Void, Void>(){
            private String[] mValues;

            @Override
            protected void onPostExecute(Void aVoid) {
                ItemListDialogFragment.newInstance(mValues, "choose car modle").show(getChildFragmentManager(), "dialog");
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

        ArrayList<CarModel> carModels = getCarModelOfCars();
        for (CarModel carModel : carModels) {
            String carModelName = carModel.toString();
            if(!arrayList.contains(carModelName)) {
                arrayList.add(carModelName);
            }
        }

        return arrayList.toArray(new String[0]);
    }


    private ArrayList<CarModel> getCarModelOfCars(){
        Map<Integer ,CarModel> newCarModelMap = new HashMap<>();
        for (Car car : carArrayList){
            if (newCarModelMap.get(car.getModelID()) == null){
                newCarModelMap.put(car.getModelID(), carModelMap.get(car.getModelID()));
            }
        }
        return new ArrayList<>(newCarModelMap.values());
    }

    @Override
    public void onItemClicked(final String value) {
        new  AsyncTask<Void, Void, Void>(){

            @Override
            protected void onPostExecute(Void aVoid) {

                getListViewAdapter().clear();
                getListViewAdapter().addAll(carArrayList);
                getListViewAdapter().notifyDataSetChanged();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    carArrayList = getCarsOfModel(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();
    }


    private ArrayList<Car> getCarsOfModel(String name) throws Exception {
        ArrayList<Car> newCarArrayList = new ArrayList<>();
        Integer modelId = -1;
        for (CarModel carModel: carModelMap.values()){
            if (name.equals(carModel.toString())){
                modelId = carModel.getCodeModel();
                break;
            }
        }
        if (modelId == -1){
            throw new Exception("the model not found");
        }
        for (Car car : dataSource.getFreeCarList()){
            if (modelId.equals(car.getModelID())){
                newCarArrayList.add(car);
            }
        }
        return newCarArrayList;
    }





}
