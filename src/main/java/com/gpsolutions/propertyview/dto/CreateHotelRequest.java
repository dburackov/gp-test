package com.gpsolutions.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateHotelRequest(

        @Schema(example = "DoubleTree by Hilton Minsk")
        @NotNull
        @Size(max = 255)
        String name,

        @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital ...")
        @Size(max = 2000)
        String description,

        @Schema(example = "Hilton")
        @NotNull
        @Size(max = 255)
        String brand,

        @NotNull
        @Valid
        AddressDto address,

        @NotNull
        @Valid
        ContactsDto contacts,

        @NotNull
        @Valid
        ArrivalTimeDto arrivalTime) {
}
