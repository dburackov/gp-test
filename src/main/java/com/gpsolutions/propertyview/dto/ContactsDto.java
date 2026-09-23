package com.gpsolutions.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactsDto(

        @Schema(example = "+375 17 309-80-00")
        @NotNull
        @Size(max = 50)
        String phone,

        @Schema(example = "doubletreeminsk.info@hilton.com")
        @NotNull
        @Size(max = 255)
        String email) {
}
