package com.example.chizkiyahuandchaskyh.takeandgo2.model.entities;

import android.renderscript.Element;

public class Order {
    protected int orderID;
    protected int customerID;
    protected STATUS status;
    protected Element.DataType start;
    protected Element.DataType end;
    protected int startKM;
    protected int endKM;
    protected boolean returnNonFilledTank;
    protected int quantityOfLitersPerBill;
    protected int amountToPay;

    public Order(int orderID, int customerID, STATUS status, Element.DataType start, Element.DataType end, int startKM, int endKM, boolean returnNonFilledTank, int quantityOfLitersPerBill, int amountToPay) {
        this.orderID = orderID;
        this.customerID = customerID;
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

    public Element.DataType getStart() {
        return start;
    }

    public void setStart(Element.DataType start) {
        this.start = start;
    }

    public Element.DataType getEnd() {
        return end;
    }

    public void setEnd(Element.DataType end) {
        this.end = end;
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

    public boolean isReturnNonFilledTank() {
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


   public enum STATUS{
        OPEN,
        CLOSED
    }
}
