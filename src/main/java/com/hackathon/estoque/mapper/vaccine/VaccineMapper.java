package com.hackathon.estoque.mapper.vaccine;

import com.hackathon.estoque.dto.vaccine.VaccineRequestDTO;
import com.hackathon.estoque.dto.vaccine.VaccineResponseDTO;
import com.hackathon.estoque.model.health.Vaccine;
import java.util.List;

public interface VaccineMapper {
    Vaccine toEntity(VaccineRequestDTO vaccineRequestDTO);
    VaccineResponseDTO toResponseDTO(Vaccine vaccine);
    List<VaccineResponseDTO> toResponseDTOList(List<Vaccine> vaccines);
}
