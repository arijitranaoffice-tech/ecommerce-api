package com.ecommerce.service;

import com.ecommerce.dto.OAuth2UserInfo;
import com.ecommerce.entity.User;

import java.util.Map;
import java.util.Optional;

/**
 * Service interface for OAuth2 operations.
 */
public interface OAuth2UserService {

    User processOAuth2User(String provider, Map<String, Object> attributes);

    Optional<User> findExistingUser(String email);

    User createNewUser(OAuth2UserInfo userInfo);

    void linkOAuth2Account(User user, String provider, String providerId, Map<String, Object> attributes);
}
