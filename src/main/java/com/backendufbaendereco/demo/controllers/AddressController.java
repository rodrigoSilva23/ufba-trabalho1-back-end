package com.backendufbaendereco.demo.controllers;


import com.backendufbaendereco.demo.DTO.CityResponseDTO;
import com.backendufbaendereco.demo.DTO.StateResponseDTO;
import com.backendufbaendereco.demo.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/citiesByState/{stateId}")
    public List<CityResponseDTO> getCitiesByState(@PathVariable("stateId") Long stateId){
        return addressService.getCitiesByState(stateId);
    }

    @GetMapping("/state")
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
    public List<StateResponseDTO> findAllStates(){
        return addressService.findAllStates();
    }


}
