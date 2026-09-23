package com.gpsolutions.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressDto(

        @Schema(example = "9")
        @NotNull
        Integer houseNumber,

        @Schema(example = "Pobediteley Avenue")
        @NotNull
        @Size(max = 255)
        String street,

        @Schema(example = "Minsk")
        @NotNull
        @Size(max = 255)
        String city,

        @Schema(example = "Belarus")
        @NotNull
        @Size(max = 255)
        String country,

        @Schema(example = "220004")
        @NotNull
        @Size(max = 20)
        String postCode) {
}
