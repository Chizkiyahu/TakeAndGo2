package com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend;

import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Address;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Car;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.CarModel;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.CreditCard;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Customer;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Order;

import java.util.ArrayList;

public interface DataSource {

    Customer isExist(Integer id);
    void addCustomer(Customer customer) throws Exception;
    void addCarModle(CarModel carModel) throws Exception;
    void addCar(Car car) throws Exception;
    void addBranch(Branch branch) throws Exception;
    void addCreditCard(CreditCard creditCard) throws Exception;
    void addOrder(Order order) throws Exception;

    void updateOrder(Order order) throws Exception;



    ArrayList<CarModel> getCarModelList();
    ArrayList<Customer> getCustomerList();
    ArrayList<Branch> getBranchList();
    ArrayList<Branch> getBranchList(int carModelId);
    ArrayList<Car> getCarList();
    ArrayList<Car> getFreeCarList();
    ArrayList<Car> getFreeCarList(int branchId);
    ArrayList<Car> getFreeCarList(double latitude, double longitude, int km);
    ArrayList<Address> getAddressesList();
    ArrayList<CreditCard> getCreditCardsList();
    ArrayList<Order> getOrdersList(int customerID);
    ArrayList<Order> getOpenOrdersList(int customerID);



    Address getAddressByID(int id);
    Branch getBranchByID(int id);
    Car getCarByID(int id);
    CreditCard getCreditCardByID(int id);
    Customer getCustomerById(int id);
    Order getOrderById(int id);
    CarModel getCarModelById(int id);


    void updateKm(int carId, int km);
    void closeOrder(Order order);
    ArrayList<Order> checkOrderCloseRecently();



    Customer tryUserPass(String username, String Password) throws Exception;
    boolean checkUserIsFree(String username) throws Exception;
    void addUserPass(String username, String Password) throws Exception;
}
