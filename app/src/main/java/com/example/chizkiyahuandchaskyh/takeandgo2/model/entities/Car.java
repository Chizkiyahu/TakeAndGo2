package com.example.chizkiyahuandchaskyh.takeandgo2.model.entities;

public class Car  {

    protected int id;
    protected int  branchID;
    protected int km;
    protected int modelID;


    public Car(int id, int branchID, int km, CarModel model) {
        this.id = id;
        this.branchID = branchID;
        this.km = km;
        this.modelID = model.getCodeModel();
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

}
