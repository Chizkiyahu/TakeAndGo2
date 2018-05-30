package com.example.chizkiyahuandchaskyh.takeandgo2.model.entities;


import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;

import java.util.ArrayList;

/**
 * Created by chezkiaho on 23.3.2018.
 */

public class Branch  {

    public Branch(int numParkingSpaces, Address address) throws Exception {

        ArrayList<Branch> branchArrayList = BackendFactory.getDataSource().getBranchList();
        this.id = 0;
        for (Branch branch : branchArrayList) {
            if (branch.getAddress().equals(address))
                throw new Exception("the branch already exsit in the address");
            if (branch.getId() > id){
                this.id = branch.getId();
            }
        }
        this.id++;
        this.numParkingSpaces = numParkingSpaces;
        this.address = address;
    }

    public Branch(int id, int numParkingSpaces, Address address) {
        this.id = id;
        this.numParkingSpaces = numParkingSpaces;
        this.address = address;
    }

    protected int id;
    protected int numParkingSpaces;
    protected Address address;


    public int getId() {
        return id;
    }

    public int getNumParkingSpaces() {
        return numParkingSpaces;
    }

    public void setNumParkingSpaces(int numParkingSpaces) throws Exception {

        if (numParkingSpaces < 0){
            throw new Exception("numParkingSpaces can't < 0");
        }
        this.numParkingSpaces = numParkingSpaces;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
