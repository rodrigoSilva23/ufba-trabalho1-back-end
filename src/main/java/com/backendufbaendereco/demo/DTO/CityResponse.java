package com.backendufbaendereco.demo.DTO;

import com.backendufbaendereco.demo.entities.andress.City;


public record CityResponse(
    Long id,
    String name,

    Integer ibgeCode
) {

    public static CityResponse fromCity(City city) {
        return new CityResponse(city.getId(), city.getName(), city.getIbgeCode());
    }
}
