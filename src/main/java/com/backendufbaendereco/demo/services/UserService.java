package com.backendufbaendereco.demo.services;




import com.backendufbaendereco.demo.DTO.AddressRequestDTO;
import com.backendufbaendereco.demo.DTO.AddressResponseDTO;
import com.backendufbaendereco.demo.DTO.UserFindResponseDTO;
import com.backendufbaendereco.demo.DTO.UserResponseDTO;
import com.backendufbaendereco.demo.Exeption.ValidationException;
import com.backendufbaendereco.demo.Projections.AddressResponseProjections;
import com.backendufbaendereco.demo.entities.address.Address;
import com.backendufbaendereco.demo.entities.address.City;
import com.backendufbaendereco.demo.entities.address.State;
import com.backendufbaendereco.demo.entities.user.User;
import com.backendufbaendereco.demo.entities.user.UserAddressMapping;
import com.backendufbaendereco.demo.repositories.AddressRepository;
import com.backendufbaendereco.demo.repositories.CityRepository;
import com.backendufbaendereco.demo.repositories.UserAddressMappingRepository;
import com.backendufbaendereco.demo.repositories.UserRepository;
import com.backendufbaendereco.demo.util.RandomString;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AddressService addressService;

    @Autowired
    private UserAddressMappingRepository userAddressMappingRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public UserResponseDTO registerUser(User user) throws MessagingException, UnsupportedEncodingException {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new ValidationException("This email already exists");
        }

        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncoded);

        String randomCode = RandomString.generateRandomString(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        User savedUser = userRepository.save(user);
     //  mailService.sendVerificationEmail(savedUser);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),savedUser.getVerificationCode());
    }
    public boolean verify(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);
        if(user == null || user.isEnabled()) {
            return false;
        }
        user.setVerificationCode(null);
        user.setEnabled(true);
        userRepository.save(user);
        User savedUser = userRepository.save(user);
        return  (savedUser != null) ? true : false;
    }
    public List<UserFindResponseDTO> findAll(){

        return userRepository.findAll()
                .stream()
                .map(UserFindResponseDTO::fromUser)
                .collect(Collectors.toList());
    }

    public UserFindResponseDTO findById(Long id){
        User response = userRepository.findById(id).orElseThrow(() -> new ValidationException("User not found"));
        return UserFindResponseDTO.fromUser(response);
    }

    @Transactional
    public void createUserAddress (AddressRequestDTO address, Long userId) {

        Address addressData = addressService.createAddress(address);
        User user = userRepository.findById(userId).orElseThrow(() -> new ValidationException("User not found"));


        if(address.getIsMainAddress()){
            userAddressMappingRepository.updateIsMainAddressByUserId(userId);
        }

        UserAddressMapping userAddressMapping = new UserAddressMapping();
        userAddressMapping.setUserId(user);
        userAddressMapping.setAddressId(addressData);
        userAddressMapping.setMainAddress(address.getIsMainAddress());

        userAddressMappingRepository.save(userAddressMapping);

    }

    @Transactional
    public AddressResponseDTO updateUserAddress (AddressRequestDTO address, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ValidationException("User not found"));

        City city = cityRepository.findById(address.getCityId())
                .orElseThrow(() -> new ValidationException("City not found with id: " + address.getCityId()));

        State state = addressService.getStateIdByCityId(address.getCityId());
        Address addressData = address.toPutAddress(city, state);

        UserAddressMapping userAddressMapping = userAddressMappingRepository.findByUserIdAndByAddressId(userId,addressData.getId())
                                            .orElseThrow(
                                                        () -> new ValidationException("not found for user ID " + user.getId() + " and address ID " + addressData.getId()
                                                    ));


        if(address.getIsMainAddress()){
            userAddressMappingRepository.updateIsMainAddressByUserId(userId);
        }

        userAddressMapping.setMainAddress(address.getIsMainAddress());
        userAddressMappingRepository.save(userAddressMapping);

        Address addressUpdated = addressRepository.save(addressData);
        return AddressResponseDTO.addressResponse(addressUpdated,userAddressMapping.isMainAddress());

    }
    @Transactional
    public void deleteUserAddress (Long addressId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ValidationException("User not found"));


        UserAddressMapping userAddressMapping = userAddressMappingRepository.findByUserIdAndByAddressId(userId,addressId)
                .orElseThrow(() -> new ValidationException("not found for user ID " + user.getId() + " and address ID " + addressId));

        userAddressMappingRepository.deleteById(userAddressMapping.getId());

        addressRepository.deleteById(addressId);

    }

    public Page<AddressResponseDTO> findAllUserAddress (Long userId,Pageable pageable) {

        Page<AddressResponseProjections> addressResponseProjectionsPage = userAddressMappingRepository.findAllUserAddress(pageable, userId);
        List<AddressResponseDTO> addressResponseDTOs = addressResponseProjectionsPage.getContent().stream()
                .map(AddressResponseDTO::createFromProjection)
                .collect(Collectors.toList());
        return new PageImpl<>(addressResponseDTOs, pageable, addressResponseProjectionsPage.getTotalElements());


    }
    public AddressResponseDTO findByAddressIdUserAddress (Long userId, Long addressId) {

        AddressResponseProjections addressResponseProjections  = userAddressMappingRepository.findByAddressIdUserAddress(userId,addressId)
                .orElseThrow(() -> new ValidationException("not found for user ID " + userId + " and address ID " + addressId));
        return AddressResponseDTO.createFromProjection(addressResponseProjections);


    }


}
