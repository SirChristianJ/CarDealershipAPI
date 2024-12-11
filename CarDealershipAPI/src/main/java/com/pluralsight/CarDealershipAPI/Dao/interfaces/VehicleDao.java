package com.pluralsight.CarDealershipAPI.Dao.interfaces;

import com.pluralsight.CarDealershipAPI.models.Vehicle;

import java.util.List;

public interface VehicleDao {
    List<Vehicle> getAll(int dealershipID);
    List<Vehicle> getVehiclesByPriceRange(int dealershipID, int min, int max);
    List<Vehicle> getVehiclesByMake(int dealershipID, String make, String model);
    List<Vehicle> getVehiclesByYear(int dealershipID, int minYear, int maxYear);
    List<Vehicle> getVehiclesByColor(int dealershipID, String color);
    List<Vehicle> getVehiclesByMilage(int dealershipID, int min, int max);
    List<Vehicle> getVehiclesByType(int dealershipID, String type);
}
