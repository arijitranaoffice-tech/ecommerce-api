package com.ecommerce.controller;

import com.ecommerce.entity.Address;
import com.ecommerce.exception.ApiResponse;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for address endpoints.
 */
@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AddressController {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<Address>>> getUserAddresses(@PathVariable UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(addressRepository.findAllByUserId(userId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Address>> getAddressById(@PathVariable UUID id) {
        return addressRepository.findById(id)
                .map(address -> ResponseEntity.ok(ApiResponse.success(address)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}/default")
    public ResponseEntity<ApiResponse<Address>> getDefaultAddress(@PathVariable UUID userId) {
        return addressRepository.findDefaultAddressByUserId(userId)
                .map(address -> ResponseEntity.ok(ApiResponse.success(address)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Address>> createAddress(@RequestBody Address address, @RequestParam UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return ResponseEntity.ok(ApiResponse.success("Address created successfully", savedAddress));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Address>> updateAddress(
            @PathVariable UUID id,
            @RequestBody Address address
    ) {
        return addressRepository.findById(id)
                .map(existingAddress -> {
                    existingAddress.setStreet(address.getStreet());
                    existingAddress.setStreet2(address.getStreet2());
                    existingAddress.setCity(address.getCity());
                    existingAddress.setState(address.getState());
                    existingAddress.setPostalCode(address.getPostalCode());
                    existingAddress.setCountry(address.getCountry());
                    existingAddress.setType(address.getType());
                    existingAddress.setPhone(address.getPhone());
                    return ResponseEntity.ok(ApiResponse.success("Address updated successfully", 
                            addressRepository.save(existingAddress)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteAddress(@PathVariable UUID id) {
        return addressRepository.findById(id)
                .map(address -> {
                    addressRepository.delete(address);
                    return ResponseEntity.ok(ApiResponse.success("Address deleted successfully", null));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/set-default")
    public ResponseEntity<ApiResponse<Address>> setDefaultAddress(@PathVariable UUID id) {
        return addressRepository.findById(id)
                .map(address -> {
                    // Unset all other default addresses for this user
                    addressRepository.findAllByUserId(address.getUser().getId()).forEach(addr -> {
                        addr.setDefault(false);
                        addressRepository.save(addr);
                    });
                    // Set this address as default
                    address.setDefault(true);
                    return ResponseEntity.ok(ApiResponse.success("Default address set successfully",
                            addressRepository.save(address)));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
