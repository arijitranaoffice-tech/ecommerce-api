package com.ecommerce.mapper;

import com.ecommerce.dto.AddressDTO;
import com.ecommerce.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for Address entity and DTO conversion.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", expression = "java(address.getId().toString())")
    @Mapping(target = "type", expression = "java(address.getType().name())")
    @Mapping(target = "createdAt", expression = "java(address.getCreatedAt() != null ? address.getCreatedAt().toString() : null)")
    AddressDTO toDTO(Address address);
}
