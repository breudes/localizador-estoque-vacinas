package com.hackathon.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDto {

    private String oldPassword;

    @NotBlank(message = "New password is required")
    private String newPassword;
}
