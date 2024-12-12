package com.pluralsight.CarDealershipAPI.Dao.wrapper;

import com.pluralsight.CarDealershipAPI.models.Dealership;
import com.pluralsight.CarDealershipAPI.models.Vehicle;

public class Inventory {
    private int ID;
    private Vehicle vehicle;

    public Inventory(int ID, Vehicle vehicle) {
        this.ID = ID;
        this.vehicle = vehicle;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
