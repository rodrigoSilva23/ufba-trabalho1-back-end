package com.backendufbaendereco.demo.services;




import com.backendufbaendereco.demo.DTO.AddressRequest;
import com.backendufbaendereco.demo.DTO.UserFindResponse;
import com.backendufbaendereco.demo.DTO.UserResponse;
import com.backendufbaendereco.demo.Exeption.ValidationException;
import com.backendufbaendereco.demo.entities.andress.Address;
import com.backendufbaendereco.demo.entities.user.User;
import com.backendufbaendereco.demo.entities.user.UserAddressMapping;
import com.backendufbaendereco.demo.repositories.CityRepository;
import com.backendufbaendereco.demo.repositories.UserAddressMappingRepository;
import com.backendufbaendereco.demo.repositories.UserRepository;
import com.backendufbaendereco.demo.util.RandomString;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public UserResponse registerUser(User user) throws MessagingException, UnsupportedEncodingException {

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

        return new UserResponse(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
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
    public List<UserFindResponse> findAll(){

        return userRepository.findAll()
                .stream()
                .map(UserFindResponse::fromUser)
                .collect(Collectors.toList());
    }

    public UserFindResponse findById(Long id){
        User response = userRepository.findById(id).orElseThrow(() -> new ValidationException("User not found"));
        return UserFindResponse.fromUser(response);
    }

    @Transactional
    public void createUserAddress (AddressRequest address, Long userId) {

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
}
