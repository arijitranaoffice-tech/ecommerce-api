package com.ecommerce.service.impl;

import com.ecommerce.dto.OAuth2UserInfo;
import com.ecommerce.entity.OAuth2UserEntity;
import com.ecommerce.entity.User;
import com.ecommerce.entity.UserRole;
import com.ecommerce.entity.UserStatus;
import com.ecommerce.repository.OAuth2UserRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.OAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of OAuth2UserService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl implements OAuth2UserService {

    private final OAuth2UserRepository oauth2UserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User processOAuth2User(String provider, Map<String, Object> attributes) {
        String providerId = getProviderId(provider, attributes);
        String email = (String) attributes.get("email");
        
        Optional<OAuth2UserEntity> existingOAuth2User = oauth2UserRepository.findByProviderAndProviderId(provider, providerId);
        
        if (existingOAuth2User.isPresent()) {
            OAuth2UserEntity oauth2User = existingOAuth2User.get();
            oauth2User.setLastLoginAt(LocalDateTime.now());
            oauth2UserRepository.save(oauth2User);
            return oauth2User.getUser();
        }
        
        Optional<User> existingUser = userRepository.findByEmail(email);
        
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            linkOAuth2Account(user, provider, providerId, attributes);
            return user;
        }
        
        OAuth2UserInfo userInfo = OAuth2UserInfo.builder()
                .id(providerId)
                .email(email)
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .locale((String) attributes.get("locale"))
                .provider(provider)
                .build();
        
        return createNewUser(userInfo);
    }

    @Override
    public Optional<User> findExistingUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User createNewUser(OAuth2UserInfo userInfo) {
        User user = User.builder()
                .email(userInfo.getEmail())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .firstName(userInfo.getName() != null ? userInfo.getName().split(" ")[0] : "User")
                .lastName(userInfo.getName() != null && userInfo.getName().contains(" ") ? 
                    userInfo.getName().split(" ")[1] : "")
                .role(UserRole.CUSTOMER)
                .status(UserStatus.ACTIVE)
                .emailVerified(true)
                .build();
        
        user = userRepository.save(user);
        
        OAuth2UserEntity oauth2User = OAuth2UserEntity.builder()
                .user(user)
                .provider(userInfo.getProvider())
                .providerId(userInfo.getId())
                .email(userInfo.getEmail())
                .name(userInfo.getName())
                .picture(userInfo.getPicture())
                .locale(userInfo.getLocale())
                .isActive(true)
                .lastLoginAt(LocalDateTime.now())
                .build();
        
        oauth2UserRepository.save(oauth2User);
        
        return user;
    }

    @Override
    @Transactional
    public void linkOAuth2Account(User user, String provider, String providerId, Map<String, Object> attributes) {
        OAuth2UserEntity oauth2User = OAuth2UserEntity.builder()
                .user(user)
                .provider(provider)
                .providerId(providerId)
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .isActive(true)
                .lastLoginAt(LocalDateTime.now())
                .build();
        
        oauth2UserRepository.save(oauth2User);
    }

    private String getProviderId(String provider, Map<String, Object> attributes) {
        if ("google".equals(provider)) {
            return (String) attributes.get("sub");
        } else if ("facebook".equals(provider)) {
            return (String) attributes.get("id");
        } else if ("github".equals(provider)) {
            return String.valueOf(attributes.get("id"));
        }
        return UUID.randomUUID().toString();
    }
}
