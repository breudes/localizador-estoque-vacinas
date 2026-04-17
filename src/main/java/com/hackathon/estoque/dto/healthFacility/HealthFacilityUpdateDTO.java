package com.hackathon.estoque.dto.healthFacility;

import com.hackathon.estoque.dto.address.AddressRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthFacilityUpdateDTO {
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 20, message = "CNES must not exceed 20 characters")
    private String cnes;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    private String phone;

    private AddressRequestDTO address;
}
