package com.backendufbaendereco.demo.DTO;

import com.backendufbaendereco.demo.entities.user.User;

public record UserFindResponseDTO(

        String name,
        String email,
        boolean enabled
) {
    public static UserFindResponseDTO fromUser(User user) {
        return new UserFindResponseDTO(user.getName(), user.getEmail(), user.isEnabled());
    }
}
