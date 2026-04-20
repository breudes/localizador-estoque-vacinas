package com.hackathon.estoque.mapper;

import com.hackathon.estoque.dto.CreateUserDTO;
import com.hackathon.estoque.dto.RegisterRequestDto;
import com.hackathon.estoque.dto.UserResponseDTO;
import com.hackathon.estoque.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public User toEntity(RegisterRequestDto registerRequestDto) {
        return new User(
                registerRequestDto.getName(),
                registerRequestDto.getCpf(),
                registerRequestDto.getEmail(),
                registerRequestDto.getPassword(),
                registerRequestDto.getDataNasc()
        );
    }

    @Override
    public UserResponseDTO toResponseDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .cpf(user.getCpf())
                .build();
    }

    @Override
    public List<UserResponseDTO> toResponseDtoList(List<User> users) {
        return users.stream()
                .map(this::toResponseDto)
                .toList();
    }


    @Override
    public User toEntityUser(CreateUserDTO registerRequestDto) {
        return new User(
                registerRequestDto.getName(),
                registerRequestDto.getCpf(),
                registerRequestDto.getEmail(),
                registerRequestDto.getPassword(),
                registerRequestDto.getDataNasc()
        );
    }
}
