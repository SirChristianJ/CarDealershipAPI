package com.pluralsight.CarDealershipAPI.controllers;

import com.pluralsight.CarDealershipAPI.Dao.interfaces.VehicleDao;
import com.pluralsight.CarDealershipAPI.Dao.wrapper.Inventory;
import com.pluralsight.CarDealershipAPI.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VehiclesController {
    private VehicleDao vehicleDao;

    @Autowired
    public VehiclesController(VehicleDao vehicleDao){
        this.vehicleDao = vehicleDao;
    }

    @RequestMapping(path="/dealerships/{id}", method = RequestMethod.GET)
    public List<Vehicle> getVehiclesByDealershipID(@PathVariable int id){
        return vehicleDao.getAll(id);
    }

    @RequestMapping(path="/dealerships/{id}/price_search/min={min}&&max={max}", method = RequestMethod.GET)
    public List<Vehicle> getVehiclesByPrice(@PathVariable int id,
                                            @PathVariable int min,
                                            @PathVariable int max)
    {
        return vehicleDao.getVehiclesByPriceRange(id,min,max);
    }

    @RequestMapping(path="/dealerships/{id}/make_model_search/make={make}&&model={model}", method = RequestMethod.GET)
    public List<Vehicle> getVehiclesByMakeModel(@PathVariable int id,
                                                @PathVariable String make,
                                                @PathVariable String model)
    {
        return vehicleDao.getVehiclesByMake(id,make,model);
    }

    @RequestMapping(path="/dealerships/{id}/year_search/min_year={min}&&max_year={max}", method = RequestMethod.GET)
    public List<Vehicle> getVehiclesByYear(@PathVariable int id,
                                           @PathVariable int min,
                                           @PathVariable int max)
    {
        return vehicleDao.getVehiclesByYear(id,min,max);
    }

    @RequestMapping(path="/dealerships/{id}/color_search/color={color}", method = RequestMethod.GET)
    public List<Vehicle> getVehiclesByColor(@PathVariable int id,
                                            @PathVariable String color)
    {
        return vehicleDao.getVehiclesByColor(id,color);
    }

    @RequestMapping(path="/dealerships/{id}/odometer_search/min={min}&&max={max}", method = RequestMethod.GET)
    public List<Vehicle> getVehiclesByOdometer(@PathVariable int id,
                                               @PathVariable int min,
                                               @PathVariable int max)
    {
        return vehicleDao.getVehiclesByMilage(id,min,max);
    }


    @RequestMapping(path="/dealerships/{id}/car_type_search/type={type}", method = RequestMethod.GET)
    public List<Vehicle> getVehiclesByType(@PathVariable int id,
                                           @PathVariable String type)
    {
        return vehicleDao.getVehiclesByType(id,type);
    }

    @RequestMapping(path="/AddVehicle", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Vehicle addVehicle(@RequestBody Inventory i)
    {
        int id = i.getID();
        Vehicle vehicle = i.getVehicle();

        return vehicleDao.insert(id,vehicle);
    }

    @RequestMapping(path="/RemoveVehicle", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void removeVehicle(@RequestBody Inventory i)
    {
        int id = i.getID();
        Vehicle vehicle = i.getVehicle();

        vehicleDao.delete(id,vehicle);
    }

}
