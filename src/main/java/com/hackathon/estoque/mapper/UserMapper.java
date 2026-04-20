package com.hackathon.estoque.mapper;

import com.hackathon.estoque.dto.CreateUserDTO;
import com.hackathon.estoque.dto.RegisterRequestDto;
import com.hackathon.estoque.dto.UpdateUserResponseDTO;
import com.hackathon.estoque.dto.UserResponseDTO;
import com.hackathon.estoque.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(RegisterRequestDto registerRequestDto);

    UserResponseDTO toResponseDto(User user);

    List<UserResponseDTO> toResponseDtoList(List<User> users);

    User toEntityUser(CreateUserDTO registerRequestDto);

}
