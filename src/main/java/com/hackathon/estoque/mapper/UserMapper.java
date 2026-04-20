package com.hackathon.estoque.mapper;

import com.hackathon.estoque.dto.CreateUserDTO;
import com.hackathon.estoque.dto.RegisterRequestDto;
import com.hackathon.estoque.dto.UserResponseDTO;
import com.hackathon.estoque.model.User;
import java.util.List;

public interface UserMapper {
    User toEntity(RegisterRequestDto registerRequestDto);
    UserResponseDTO toResponseDto(User user);
    List<UserResponseDTO> toResponseDtoList(List<User> users);
    User toEntityUser(CreateUserDTO registerRequestDto);
}