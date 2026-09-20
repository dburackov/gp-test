package com.gpsolutions.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record ArrivalTimeDto(

        @Schema(type = "string", example = "14:00")
        @NotNull
        LocalTime checkIn,

        @Schema(type = "string", example = "12:00")
        LocalTime checkOut) {
}
