package com.ecommerce.service;

import com.ecommerce.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for User operations.
 */
public interface UserService {

    UserDTO createUser(RegisterRequest request);

    UserDTO getUserById(UUID id);

    UserDTO getUserByEmail(String email);

    Page<UserDTO> getAllUsers(Pageable pageable);

    UserDTO updateUser(UUID id, UpdateUserRequest request);

    void deleteUser(UUID id);

    void verifyEmail(UUID id);

    UserDTO getCurrentUser();

    List<UserDTO> searchUsers(String keyword, Pageable pageable);
}
