package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganisationDto {
    private Long id;
    private String name;
    private String address;
    private int numberOfEmployees;
    private LocalDate lastUpdateDate;
}
