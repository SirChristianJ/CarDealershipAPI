package com.pluralsight.CarDealershipAPI.controllers;

import com.pluralsight.CarDealershipAPI.Dao.interfaces.DealershipDao;
import com.pluralsight.CarDealershipAPI.models.Dealership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DealershipsController {

    private DealershipDao dealershipDao;

    @Autowired
    public DealershipsController(DealershipDao dealershipDao){
        this.dealershipDao = dealershipDao;
    }

    @RequestMapping(path="/dealerships", method = RequestMethod.GET)
    public List<Dealership> getDealerships(){
        return dealershipDao.getAll();
    }

    @RequestMapping(path="/dealerships", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Dealership addDealership(@RequestBody Dealership dealership){
        return dealershipDao.insert(dealership);
    }
}
