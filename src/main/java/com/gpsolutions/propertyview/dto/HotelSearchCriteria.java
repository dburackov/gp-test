package com.gpsolutions.propertyview.dto;

import java.util.List;

public record HotelSearchCriteria(

        String name,

        String brand,

        String city,

        String country,

        List<String> amenities) {
}
