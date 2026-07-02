package com.shivam.blog.services;

import com.shivam.blog.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
