package com.gpsolutions.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record HotelDetailResponse(

        @Schema(example = "1")
        Long id,

        @Schema(example = "DoubleTree by Hilton Minsk")
        String name,

        @Schema(example = "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital ...")
        String description,

        @Schema(example = "Hilton")
        String brand,

        AddressDto address,

        ContactsDto contacts,

        ArrivalTimeDto arrivalTime,

        @Schema(example = "[\"Free parking\", \"Free WiFi\"]")
        List<String> amenities) {
}
