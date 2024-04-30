package com.backendufbaendereco.demo.services;

import com.backendufbaendereco.demo.DTO.CityResponse;
import com.backendufbaendereco.demo.DTO.StateResponse;
import com.backendufbaendereco.demo.DTO.UserFindResponse;
import com.backendufbaendereco.demo.entities.andress.City;
import com.backendufbaendereco.demo.entities.andress.State;
import com.backendufbaendereco.demo.repositories.CityRepository;
import com.backendufbaendereco.demo.repositories.StateRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;


    public List<CityResponse> getCitiesByState(Long stateId){

        return cityRepository.findByStateId(stateId).stream() .map(CityResponse::fromCity).collect(Collectors.toList());

    }

    public List<StateResponse> findAllStates(){
        return stateRepository.findAll().stream().map(StateResponse::fromState).collect(Collectors.toList());
    }

}
