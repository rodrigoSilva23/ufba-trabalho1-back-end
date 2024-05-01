package com.backendufbaendereco.demo.DTO;


import com.backendufbaendereco.demo.entities.andress.Address;
import com.backendufbaendereco.demo.entities.andress.City;
import com.backendufbaendereco.demo.entities.andress.State;
import com.backendufbaendereco.demo.repositories.CityRepository;
import com.backendufbaendereco.demo.services.AddressService;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class AddressRequest{

    @NotBlank
    @Size(max = 8, min = 8)
    private String postalCode;
    @NotBlank
    private  String street;
    @NotBlank
    private   String location;
    @NotBlank
    private  String locationType;
    @NotBlank

    private  String neighborhood;
    @NotNull
    private  Integer streetType;
    @NotBlank
    private String number;
    private  String block;
    private String lot;
    private  String complement;
    @NotNull

    private  Long cityId;
    @NotNull
    private  Long stateId;


    private Boolean isMainAddress;


    public Address toAddress( City city, State state) {
        Address address = new Address();
        address.setPostalCode(this.getPostalCode());
        address.setStreet(this.getStreet());
        address.setLocation(this.getLocation());
        address.setLocationType(this.getLocationType());
        address.setNeighborhood(this.getNeighborhood());
        address.setStreetType(this.getStreetType());
        address.setNumber(this.getNumber());
        address.setBlock(this.getBlock());
        address.setLot(this.getLot());
        address.setComplement(this.getComplement());



        address.setStateId(state);
        address.setCityId(city);

        return address;
    }
}
