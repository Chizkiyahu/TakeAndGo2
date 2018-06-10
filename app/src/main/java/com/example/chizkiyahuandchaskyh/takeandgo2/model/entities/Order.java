package com.example.chizkiyahuandchaskyh.takeandgo2.model.entities;

import android.renderscript.Element;

import java.util.Date;

public class Order {
    protected int orderID;
    protected int customerID;
    protected int carID;
    protected STATUS status;
    protected Date start;
    protected Date end;
    protected int startKM;
    protected int endKM;
    protected boolean returnNonFilledTank;
    protected int quantityOfLitersPerBill;
    protected int amountToPay;

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public boolean isReturnNonFilledTank() {
        return returnNonFilledTank;
    }


    public Order(int orderID, int customerID, int carID, STATUS status, Date start, Date end, int startKM, int endKM, boolean returnNonFilledTank, int quantityOfLitersPerBill, int amountToPay) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.carID = carID;
        this.status = status;
        this.start = start;
        this.end = end;
        this.startKM = startKM;
        this.endKM = endKM;
        this.returnNonFilledTank = returnNonFilledTank;
        this.quantityOfLitersPerBill = quantityOfLitersPerBill;
        this.amountToPay = amountToPay;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public int getStartKM() {
        return startKM;
    }

    public void setStartKM(int startKM) {
        this.startKM = startKM;
    }

    public int getEndKM() {
        return endKM;
    }

    public void setEndKM(int endKM) {
        this.endKM = endKM;
    }

    public boolean getIsReturnNonFilledTank() {
        return returnNonFilledTank;
    }

    public void setReturnNonFilledTank(boolean returnNonFilledTank) {
        this.returnNonFilledTank = returnNonFilledTank;
    }

    public int getQuantityOfLitersPerBill() {
        return quantityOfLitersPerBill;
    }

    public void setQuantityOfLitersPerBill(int quantityOfLitersPerBill) {
        this.quantityOfLitersPerBill = quantityOfLitersPerBill;
    }

    public int getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(int amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public enum STATUS{
        OPEN,
        CLOSED
    }
}
