package com.backendufbaendereco.demo.services;

import com.backendufbaendereco.demo.DTO.AddressRequest;
import com.backendufbaendereco.demo.DTO.CityResponse;
import com.backendufbaendereco.demo.DTO.StateResponse;
import com.backendufbaendereco.demo.Exeption.ValidationException;
import com.backendufbaendereco.demo.entities.andress.Address;
import com.backendufbaendereco.demo.entities.andress.City;
import com.backendufbaendereco.demo.entities.andress.State;
import com.backendufbaendereco.demo.repositories.AddressRepository;
import com.backendufbaendereco.demo.repositories.CityRepository;
import com.backendufbaendereco.demo.repositories.StateRepository;
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
    @Autowired
    private AddressRepository addressRepository;



    public List<CityResponse> getCitiesByState(Long stateId){

        return cityRepository.findByStateId(stateId).stream() .map(CityResponse::fromCity).collect(Collectors.toList());

    }

    public List<StateResponse> findAllStates(){
        return stateRepository.findAll().stream().map(StateResponse::fromState).collect(Collectors.toList());
    }

    public State getStateIdByCityId(Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new ValidationException("City not found"));

        State state = city.getState();
        if (state == null) new ValidationException("State not found");

        return state;
    }

    public Address createAddress(AddressRequest address) {

        City city = cityRepository.findById(address.getCityId())
                            .orElseThrow(() -> new jakarta.validation.ValidationException("City not found with id: " + address.getCityId()));

        State state = this.getStateIdByCityId(address.getCityId());
        Address addressData = address.toAddress(city, state);

        return  addressRepository.save(addressData);

    }

}
