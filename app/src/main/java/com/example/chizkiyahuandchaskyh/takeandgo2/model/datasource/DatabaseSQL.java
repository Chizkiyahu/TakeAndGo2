package com.example.chizkiyahuandchaskyh.takeandgo2.model.datasource;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Address;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Car;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.CarModel;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.CreditCard;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Customer;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.utils.Constants;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.utils.Php;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseSQL implements DataSource {

    private static final String WEB_URL = "http://rafol.vlab.jct.ac.il/TakeAndGo/";
    private Map<String, String> usersPassMap = new HashMap<>();

    private Map<Integer, Address> addresses ;
    private Map<Integer, Branch> branches ;
    private Map<Integer, Car> cars ;
    private Map<Integer, CarModel> carModels ;
    private Map<Integer, CreditCard> creditCards ;
    private Map<Integer, Customer> customers ;
    private Map<Integer, Order> orders ;
    private DateFormat format = new SimpleDateFormat("yyyy-mm-d");

    @SuppressLint("UseSparseArrays")
    @Override
    public Address getAddressByID(int id) {
        try {
            if (id == 0){
                return null;
            }
            if (addresses == null){
                addresses = new HashMap<>();
            }
            String url = WEB_URL + "getAddressesList.php" ;
            final ContentValues values = new ContentValues();
            values.put("id", id);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Address" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                addresses.put(jsonObject.getInt( "id" ),
                        new Address(jsonObject.getInt( "id" ),
                                jsonObject.getString( "country" ),
                                jsonObject.getString("city"),
                                jsonObject.getString("street"),
                                jsonObject.getInt("houseNum"),
                                jsonObject.getDouble("Latitude"),
                                jsonObject.getDouble("longitude")));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return addresses.get(id);
    }

    @SuppressLint("UseSparseArrays")
    public int getAddressIdByParms(Address address){
        try {
            if (addresses == null){
                addresses = new HashMap<>();
            }

            String url = WEB_URL + "getAddressesList.php" ;
            final ContentValues values = new ContentValues();
            values.put("country",address.getCountry());
            values.put("city", address.getCity());
            values.put("street", address.getStreet());
            values.put("houseNum", address.getHouseNum());
            if (address.getLatitude() == 0.0 ){
                values.put("Latitude",  0 );
            }
            else {
                values.put("Latitude",  address.getLatitude() );
            }
            if (address.getLatitude() == 0.0 ){
                values.put("Latitude", 0 );
            }
            else {
                values.put("Latitude", address.getLatitude() );
            }
            //values.put("Latitude", (address.getLatitude() == 0.0 ) ? 0 : address.getLatitude() );
            //values.put("longitude", (address.getLongitude()  == 0.0 ) ? 0 : address.getLongitude());
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Address" );
            if( array.length() == 1) {
                JSONObject jsonObject = array.getJSONObject( 0 );
                return  jsonObject.getInt( "id" );
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return -1;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public Branch getBranchByID(int id) {

        try {
            if (id == 0){
                return null;
            }
            if(branches == null){
                branches = new HashMap<>();
            }
            String url = WEB_URL + "getBranchList.php" ;
            final ContentValues values = new ContentValues();
            values.put("id", id);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Branch" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                branches.put(jsonObject.getInt( "id" ),
                        new Branch(jsonObject.getInt( "id" ),
                                jsonObject.getInt( "numParkingSpaces" ),
                                new Address(getAddressByID( jsonObject.getInt( "addressID")))));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return branches.get(id);
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public Car getCarByID(int id) {
        try {
            if (id == 0){
                return null;
            }
            if (addresses == null){
                addresses = new HashMap<>();
            }
            if (cars == null){
                cars = new HashMap<>();
            }
            String url = WEB_URL + "getCarList.php" ;
            final ContentValues values = new ContentValues();
            values.put("id", id);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Car" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                cars.put(jsonObject.getInt( "id" ),
                        new Car(jsonObject.getInt( "id" ),
                                jsonObject.getInt( "branchID" ),
                                jsonObject.getInt("km"),
                                new CarModel(getCarModelById(jsonObject.getInt("modelID")))));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return cars.get(id);
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public Customer getCustomerById(int id) {
        try {
            if (id == 0){
                return null;
            }
            if (customers == null){
                customers = new HashMap<>();
            }
            String url = WEB_URL + "getCustomerList.php" ;
            final ContentValues values = new ContentValues();
            values.put("id", id);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Customer" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                customers.put(jsonObject.getInt( "id" ),
                        new Customer(
                                jsonObject.getString( "lastName" ),
                                jsonObject.getString( "firstName" ),
                                jsonObject.getInt( "id" ),
                                jsonObject.getString( "phoneNumber" ),
                                jsonObject.getString( "email"),
                                getCreditCardByID(jsonObject.getInt( "creditCardID" ))
                        )
                );
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return customers.get(id);
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public Order getOrderById(int id) {
        try {
            if (id == 0){
                return null;
            }
            if (orders == null){
                orders = new HashMap<>();
            }
            String url = WEB_URL + "getOrdersList.php" ;
            final ContentValues values = new ContentValues();
            values.put("id", id);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Order" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                orders.put(jsonObject.getInt( "orderID" ),
                        new Order(jsonObject.getInt( "orderID" ),
                                jsonObject.getInt( "customerID" ),
                                jsonObject.getInt("carID"),
                                Order.STATUS.valueOf(jsonObject.getString("status")),
                                format.parse( jsonObject.getString("start")),
                                format.parse( jsonObject.getString("end")),
                                jsonObject.getInt("startKM"),
                                jsonObject.getInt("endKM"),
                                Boolean.getBoolean(jsonObject.getString("returnNonFilledTank")),
                                jsonObject.getInt("quantityOfLitersPerBill"),
                                jsonObject.getInt("amountToPay")));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return orders.get(id);
    }

    @Override
    public Customer isExist(Integer id) {
        return getCustomerById(id);
    }

    @Override
    public void addCreditCard(CreditCard creditCard) {
        try {
            String url = WEB_URL + "addCreditCard.php" ;

            final ContentValues values = new ContentValues();
            values.put("customerID", creditCard.getCustomerID());
            values.put("digits", creditCard.getDigits());
            values.put("expiration", creditCard.getExpiration().toString());
            values.put("cvv", creditCard.getCvv());
            values.put("Issuer", creditCard.getIssuer().toString());
            Php.POST( url, values );
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public CarModel getCarModelById(int id) {
        try {
            if (id == 0){
                return null;
            }
            if (carModels == null){
                carModels = new HashMap<>();
            }
            String url = WEB_URL + "etCarModelList.php" ;
            final ContentValues values = new ContentValues();
            values.put("id", id);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "CarModel" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                carModels.put(jsonObject.getInt( "codeModel" ),
                        new CarModel(
                                jsonObject.getInt( "codeModel" ),
                                jsonObject.getString( "manufacturerName" ),
                                jsonObject.getString( "modelName" ),
                                jsonObject.getInt( "engineCapacity" ),
                                CarModel.GEAR_BOX.valueOf( jsonObject.getString( "gearBox" )),
                                jsonObject.getInt( "seating" )
                        ));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return carModels.get(id);
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            String url = WEB_URL + "addCustomer.php" ;

            final ContentValues values = new ContentValues();
            values.put("id", customer.getId());
            values.put("lastName", customer.getLastName());
            values.put("firstName",customer.getFirstName());
            values.put("phoneNumber", customer.getPhoneNumber());
            values.put("email", customer.getEmail());
            values.put("pass", customer.getPassword());

            if (customer.getCreditCard() != null){
                customer.getCreditCard().setCustomerID(customer.getId());
                addCreditCard(customer.getCreditCard());
            }
            Php.POST( url, values );
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }

    }

    @Override
    public void addCarModle(CarModel carModel) {
        try {
            String url = WEB_URL + "addCarModel.php" ;

            final ContentValues values = new ContentValues();
            values.put("manufacturerName", carModel.getManufacturerName());
            values.put("modelName", carModel.getModelName());
            values.put("engineCapacity", carModel.getEngineCapacity());
            values.put("seating", carModel.getSeating());
            values.put("gearBox", carModel.getGearBox().toString());

            Php.POST( url, values );
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
    }

    @Override
    public void addCar(Car car) {
        try {
            String url = WEB_URL + "addCar.php" ;

            final ContentValues values = new ContentValues();
            values.put("id", car.getId());
            values.put("branchID", car.getBranchID());
            values.put("km", car.getKm());
            values.put("modelID", car.getModelID());

            Php.POST( url, values );
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }

    }

    @Override
    public void addBranch(Branch branch) {
        try {

            String url = WEB_URL + "addBranch.php" ;
            final ContentValues values = new ContentValues();
            values.put("id", branch.getId());
            values.put("numParkingSpaces", branch.getNumParkingSpaces());
            values.put("addressID",addAddress(branch.getAddress()));
            Php.POST( url, values );
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }

    }

    private int addAddress(Address address) {
        try {
            String url = WEB_URL + "addAddress.php" ;

            final ContentValues values = new ContentValues();
            values.put("country",address.getCountry());
            values.put("city",  address.getCity());
            values.put("street", address.getStreet());
            values.put("houseNum", address.getHouseNum());
            if(address.getLatitude() == 0.0){
                values.put("Latitude",  0);
            }else {
                values.put("Latitude",  address.getLatitude());
            }
            if (address.getLongitude() == 0.0){
                values.put("longitude",  0 );
            }else {
                values.put("longitude", address.getLongitude());
            }
            String json =  Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Address" );
            JSONObject jsonObject = array.getJSONObject( 0 );
            return jsonObject.getInt( "id" );
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return -1;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public ArrayList<CarModel> getCarModelList() {
        try {
            if (carModels == null){
                carModels = new HashMap<>();
            }
            carModels.clear();

            String json = Php.GET( WEB_URL + "getCarModelList.php" );
            JSONArray array = new JSONObject( json ).getJSONArray( "CarModel" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                carModels.put(jsonObject.getInt( "codeModel" ),
                        new CarModel(
                                jsonObject.getInt( "codeModel" ),
                                jsonObject.getString( "manufacturerName" ),
                                jsonObject.getString( "modelName" ),
                                jsonObject.getInt( "engineCapacity" ),
                                CarModel.GEAR_BOX.valueOf( jsonObject.getString( "gearBox" )),
                                jsonObject.getInt( "seating" )
                        ));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return new ArrayList<>(carModels.values());
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public ArrayList<Customer> getCustomerList() {
        try {
            if (customers == null){
                customers = new HashMap<>();
            }
            customers.clear();

            String json = Php.GET( WEB_URL + "getCustomerList.php" );
            JSONArray array = new JSONObject( json ).getJSONArray( "Customer" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                customers.put(jsonObject.getInt( "id" ),
                        new Customer(
                                jsonObject.getString( "lastName" ),
                                jsonObject.getString( "firstName" ),
                                jsonObject.getInt( "id" ),
                                jsonObject.getString( "phoneNumber" ),
                                jsonObject.getString( "email")
                                )
                        );
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return new ArrayList<>(customers.values());
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public ArrayList<Branch> getBranchList() {
        try {
            if (branches == null){
                branches = new HashMap<>();
            }
            branches.clear();

            String json = Php.GET( WEB_URL + "getBranchList.php" );
            JSONArray array = new JSONObject( json ).getJSONArray( "Branch" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                branches.put(jsonObject.getInt( "id" ),
                        new Branch(jsonObject.getInt( "id" ),
                                jsonObject.getInt( "numParkingSpaces" ),
                                new Address(getAddressByID( jsonObject.getInt( "addressID")))));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return new ArrayList<>(branches.values());

    }

    @SuppressLint("UseSparseArrays")
    @Override
    public ArrayList<Address> getAddressesList() {
        try {
            if (addresses == null){
                addresses = new HashMap<>();
            }
            addresses.clear();

            String json = Php.GET( WEB_URL + "getAddressesList.php" );
            JSONArray array = new JSONObject( json ).getJSONArray( "Address" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                addresses.put(jsonObject.getInt( "id" ),
                        new Address(jsonObject.getInt( "id" ),
                                jsonObject.getString( "country" ),
                                jsonObject.getString("city"),
                                jsonObject.getString("street"),
                                jsonObject.getInt("houseNum"),
                                jsonObject.getDouble("Latitude"),
                                jsonObject.getDouble("longitude")));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return new ArrayList<>(addresses.values());
    }

    @Override
    public ArrayList<CreditCard> getCreditCardsList() {
       /* try {
            if (creditCards == null){
                creditCards = new HashMap<>();
            }
            creditCards.clear();

            String json = Php.GET( WEB_URL + "getCreditCardsList.php" );
            JSONArray array = new JSONObject( json ).getJSONArray( "Branch" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                creditCards.put(jsonObject.getInt( "id" ),
                        new Address(jsonObject.getInt( "id" ),
                                jsonObject.getString( "country" ),
                                jsonObject.getString("city"),
                                jsonObject.getString("street"),
                                jsonObject.getInt("houseNum"),
                                jsonObject.getDouble("Latitude"),
                                jsonObject.getDouble("longitude")));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return new ArrayList<>(creditCards.values());*/
       return null;
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public ArrayList<Order> getOrdersList() {
        try {
            if (orders == null){
                orders = new HashMap<>();
            }
            orders.clear();

            String json = Php.GET( WEB_URL + "getOrdersList.php" );
            JSONArray array = new JSONObject( json ).getJSONArray( "Order" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                orders.put(jsonObject.getInt( "orderID" ),
                        new Order(jsonObject.getInt( "orderID" ),
                                jsonObject.getInt( "customerID" ),
                                jsonObject.getInt("carID"),
                                Order.STATUS.valueOf(jsonObject.getString("status")),
                                format.parse( jsonObject.getString("start")),
                                format.parse( jsonObject.getString("end")),
                                jsonObject.getInt("startKM"),
                                jsonObject.getInt("endKM"),
                                Boolean.getBoolean(jsonObject.getString("returnNonFilledTank")),
                                jsonObject.getInt("quantityOfLitersPerBill"),
                                jsonObject.getInt("amountToPay")));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return new ArrayList<>(orders.values());
    }

    @SuppressLint("UseSparseArrays")
    @Override
    public ArrayList<Car> getCarList() {
        try {
            if (cars == null){
                cars = new HashMap<>();
            }
            cars.clear();

            String json = Php.GET( WEB_URL + "getCarList.php" );
            JSONArray array = new JSONObject( json ).getJSONArray( "Car" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                cars.put(jsonObject.getInt( "id" ),
                        new Car(jsonObject.getInt( "id" ),
                                jsonObject.getInt( "branchID" ),
                                jsonObject.getInt("km"),
                                new CarModel(getCarModelById(jsonObject.getInt("modelID")))));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return new ArrayList<>(cars.values());
    }

    @Override
    public Customer tryUserPass(String username, String password) {


        try {
            String url = WEB_URL + "getCustomerList.php" ;
            final ContentValues values = new ContentValues();
            values.put("email", username);
            values.put("pass", password);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Customer" );
            JSONObject jsonObject = array.getJSONObject( 0 );
            return new Customer(
                jsonObject.getString( "lastName" ),
                jsonObject.getString( "firstName" ),
                jsonObject.getInt( "id" ),
                jsonObject.getString( "phoneNumber" ),
                jsonObject.getString( "email"));

        }catch (Exception e){
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return null;
    }

    @Override
    public boolean checkUserIsFree(String username) {
            try {
                String url = WEB_URL + "tryUserPass.php" ;
                final ContentValues values = new ContentValues();
                values.put("email", username);
                String json = Php.POST( url, values );
                return ! Boolean.valueOf(json);
            }catch (Exception e){
                Log.e(Constants.Log.TAG,e.getMessage());
            }
            return true;
    }

    @Override
    public void addUserPass(String username, String password) {
        try {
            String url = WEB_URL + "addUser.php" ;

            final ContentValues values = new ContentValues();
            values.put("email", username);
            values.put("pass",password );
            Php.POST( url, values );
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
    }

    ////////---
    //
    @SuppressLint("UseSparseArrays")
    @Override
    public CreditCard getCreditCardByID(int id) {
        if (creditCards == null){
            creditCards = new HashMap<>();
        }
       /* if (id == 0){
            return null;
        }
        try {
            if (id == 0){
                return null;
            }
            if (creditCards == null){
                creditCards = new HashMap<>();
            }
            String url = WEB_URL + "getCreditCards.php" ;
            final ContentValues values = new ContentValues();
            values.put("id", id);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Car" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                cars.put(jsonObject.getInt( "id" ),
                        new Car(jsonObject.getInt( "id" ),
                                jsonObject.getInt( "branchID" ),
                                jsonObject.getInt("km"),
                                new CarModel(getCarModelById(jsonObject.getInt("modelID")))));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }*/
        return creditCards.get(id);
    }


    @Override
    public void addOrder(Order order) {
        try {
            String url = WEB_URL + "addOrder.php" ;

            final ContentValues values = new ContentValues();
            values.put("orderID", order.getOrderID());
            values.put("customerID", order.getCustomerID());
            values.put("carID", order.getCarID());
            values.put("status",order.getStatus().toString());
            values.put("start", order.getStart().toString());
            values.put("end", order.getEnd().toString());
            values.put("startKM", order.getStartKM());
            values.put("endKM", order.getEndKM());
            values.put("returnNonFilledTank", order.getIsReturnNonFilledTank());
            values.put("quantityOfLitersPerBill", order.getQuantityOfLitersPerBill());
            values.put("amountToPay", order.getAmountToPay());

            Php.POST( url, values );
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }


    }



    @Override
    public ArrayList<Branch> getBranchList(int carModelId) {
        ArrayList<Branch> branches = new ArrayList<>();
        try {

            String url = WEB_URL + "getBranchListWithCarModel.php" ;
            final ContentValues values = new ContentValues();
            values.put("modelID", carModelId);
            String json =  Php.POST( url  ,values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Branch" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                branches.add(new Branch(jsonObject.getInt( "id" ),
                                jsonObject.getInt( "numParkingSpaces" ),
                                new Address(getAddressByID( jsonObject.getInt( "addressID")))));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return branches;

    }

    @Override
    public ArrayList<Car> getFreeCarList() {
        ArrayList<Car> carsFree = new ArrayList<>();
        try {
            String json = Php.GET( WEB_URL + "getFreeCarList.php" );
            JSONArray array = new JSONObject( json ).getJSONArray( "Car" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                carsFree.add(new Car(jsonObject.getInt( "id" ),
                                jsonObject.getInt( "branchID" ),
                                jsonObject.getInt("km"),
                                new CarModel(getCarModelById(jsonObject.getInt("modelID")))));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return carsFree;
    }

    @Override
    public ArrayList<Car> getFreeCarList(int branchId) {
        ArrayList<Car> carsFree = new ArrayList<>();
        try {
            String url = WEB_URL + "getFreeCarList.php";
            final ContentValues values = new ContentValues();
            values.put("branchID", branchId);
            String json = Php.POST( url, values );
            JSONArray array = new JSONObject( json ).getJSONArray( "Car" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                carsFree.add(new Car(jsonObject.getInt( "id" ),
                        jsonObject.getInt( "branchID" ),
                        jsonObject.getInt("km"),
                        new CarModel(getCarModelById(jsonObject.getInt("modelID")))));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return carsFree;
    }

    @Override
    public ArrayList<Car> getFreeCarList(double latitude, double longitude, int km) {
        return null;
    }

    @Override
    public ArrayList<Order> getOpenOrdersList() {

        ArrayList<Order> orders = new ArrayList<>();
        try {
            String url = WEB_URL + "getOrdersList.php";
            final ContentValues values = new ContentValues();
            values.put("status", "OPEN");
            String json = Php.POST(url, values  );
            JSONArray array = new JSONObject( json ).getJSONArray( "Order" );
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject( i );
                orders.add(new Order(jsonObject.getInt( "orderID" ),
                                jsonObject.getInt( "customerID" ),
                                jsonObject.getInt("carID"),
                                Order.STATUS.valueOf(jsonObject.getString("status")),
                                format.parse( jsonObject.getString("start")),
                                format.parse( jsonObject.getString("end")),
                                jsonObject.getInt("startKM"),
                                jsonObject.getInt("endKM"),
                                Boolean.getBoolean(jsonObject.getString("returnNonFilledTank")),
                                jsonObject.getInt("quantityOfLitersPerBill"),
                                jsonObject.getInt("amountToPay")));
            }
        } catch (Exception e) {
            Log.e(Constants.Log.TAG,e.getMessage());
        }
        return orders;
    }

    @Override
    public void updateKm(int carId, int km) {

    }

    @Override
    public void closeOrder(Order order) {

    }

    @Override
    public ArrayList<Order> checkOrderCloseRecently() {
        return null;
    }
}
