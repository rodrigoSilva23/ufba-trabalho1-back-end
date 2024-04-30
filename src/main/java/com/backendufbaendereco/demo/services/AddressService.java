package com.backendufbaendereco.demo.services;

import com.backendufbaendereco.demo.DTO.CityResponse;
import com.backendufbaendereco.demo.DTO.UserFindResponse;
import com.backendufbaendereco.demo.entities.andress.City;
import com.backendufbaendereco.demo.repositories.CityRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    @Autowired
    private CityRepository cityRepository;


    public List<CityResponse> getCitiesByState(Long stateId){

        return cityRepository.findByStateId(stateId).stream() .map(CityResponse::fromCity).collect(Collectors.toList());

    }

}
