package com.backendufbaendereco.demo.DTO;

import com.backendufbaendereco.demo.entities.user.User;

public record UserFindResponse(

        String name,
        String email,
        boolean enabled
) {
    public static UserFindResponse fromUser(User user) {
        return new UserFindResponse(user.getName(), user.getEmail(), user.isEnabled());
    }
}
