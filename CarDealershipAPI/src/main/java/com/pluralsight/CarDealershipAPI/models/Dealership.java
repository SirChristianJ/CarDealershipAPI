package com.pluralsight.CarDealershipAPI.models;

import java.util.ArrayList;

public class Dealership {
    private int id;
    private String name;
    private String address;
    private String phone;

    public Dealership(){}

    public Dealership(int id, String name, String address, String phone) {
        this.id =id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString(){
        return String.format("%d %-10s %-10s %-10s\n", id,name,address,phone);
    }
}
