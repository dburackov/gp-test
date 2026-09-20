package com.gpsolutions.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record HotelSummaryResponse(

        @Schema(example = "1")
        Long id,

        @Schema(example = "DoubleTree by Hilton Minsk")
        String name,

        @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital ...")
        String description,

        @Schema(example = "9 Pobediteley Avenue, Minsk, 220004, Belarus")
        String address,

        @Schema(example = "+375 17 309-80-00")
        String phone) {
}
