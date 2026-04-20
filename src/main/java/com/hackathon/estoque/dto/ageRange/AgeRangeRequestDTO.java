package com.hackathon.estoque.dto.ageRange;

public record AgeRangeRequestDTO(
        Integer initialAge,
        Integer finalAge,
        Boolean isInMonths
) {
}
