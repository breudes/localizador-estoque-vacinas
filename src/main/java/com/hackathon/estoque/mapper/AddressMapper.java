package com.hackathon.estoque.mapper;

import com.hackathon.estoque.dto.AddressResponseDTO;
import com.hackathon.estoque.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressResponseDTO toResponseDto(Address address);
}
