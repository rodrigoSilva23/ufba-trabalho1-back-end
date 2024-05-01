package com.backendufbaendereco.demo.controllers;


import com.backendufbaendereco.demo.DTO.CityResponse;
import com.backendufbaendereco.demo.DTO.StateResponse;
import com.backendufbaendereco.demo.entities.andress.City;
import com.backendufbaendereco.demo.entities.andress.State;
import com.backendufbaendereco.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/citiesByState/{stateId}")
    public List<CityResponse> getCitiesByState(@PathVariable("stateId") Long stateId){
        return addressService.getCitiesByState(stateId);
    }

    @GetMapping("/state")
    public List<StateResponse> findAllStates(){
        return addressService.findAllStates();
    }


}
