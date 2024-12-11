package com.pluralsight.CarDealershipAPI.Dao.interfaces;

import com.pluralsight.CarDealershipAPI.models.Dealership;

import java.util.List;

public interface DealershipDao {
    List<Dealership> getAll();
    Dealership insert(Dealership dealership);
}
