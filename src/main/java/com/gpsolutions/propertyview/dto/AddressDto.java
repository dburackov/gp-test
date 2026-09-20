package com.gpsolutions.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AddressDto(

        @Schema(example = "9")
        @NotNull
        @Positive
        Integer houseNumber,

        @Schema(example = "Pobediteley Avenue")
        @NotBlank
        @Size(max = 255)
        String street,

        @Schema(example = "Minsk")
        @NotBlank
        @Size(max = 255)
        String city,

        @Schema(example = "Belarus")
        @NotBlank
        @Size(max = 255)
        String country,

        @Schema(example = "220004")
        @NotBlank
        @Size(max = 20)
        String postCode) {
}
