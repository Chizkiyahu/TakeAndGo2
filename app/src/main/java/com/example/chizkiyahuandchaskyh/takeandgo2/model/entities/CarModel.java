package com.example.chizkiyahuandchaskyh.takeandgo2.model.entities;

public class CarModel {

    public CarModel(int codeModel, String manufacturerName, String modelName, int engineCapacity, GEAR_BOX gearBox, int seating) {
        this.codeModel = codeModel;
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.engineCapacity = engineCapacity;
        this.gearBox = gearBox;
        this.seating = seating;
    }

    public CarModel(CarModel oldCarModel) {
        this.codeModel = oldCarModel.codeModel;
        this.manufacturerName = oldCarModel.manufacturerName;
        this.modelName = oldCarModel.modelName;
        this.engineCapacity = oldCarModel.engineCapacity;
        this.gearBox = oldCarModel.gearBox;
        this.seating = oldCarModel.seating;
    }

    private int codeModel;
    private String manufacturerName;
    private String modelName;
    private int engineCapacity;
    private GEAR_BOX gearBox;
    protected int seating;

    public enum GEAR_BOX{
        MANUAL,
        AUTOMATIC
    }

    public int getCodeModel() {
        return codeModel;
    }

    public void setCodeModel(int codeModel) {
        this.codeModel = codeModel;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public GEAR_BOX getGearBox() {
        return gearBox;
    }

    public void setGearBox(GEAR_BOX gearBox) {
        this.gearBox = gearBox;
    }

    public int getSeating() {
        return seating;
    }

    public void setSeating(int seating) {
        this.seating = seating;
    }

    @Override
    public String toString() {
        return manufacturerName + " " + modelName;
    }
}
