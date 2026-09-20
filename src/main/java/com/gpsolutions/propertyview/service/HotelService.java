package com.gpsolutions.propertyview.service;

import com.gpsolutions.propertyview.dto.CreateHotelRequest;
import com.gpsolutions.propertyview.dto.HotelDetailResponse;
import com.gpsolutions.propertyview.dto.HotelSearchCriteria;
import com.gpsolutions.propertyview.dto.HotelSummaryResponse;
import java.util.List;

public interface HotelService {

    List<HotelSummaryResponse> findAll();

    HotelDetailResponse findById(Long id);

    List<HotelSummaryResponse> search(HotelSearchCriteria criteria);

    HotelSummaryResponse create(CreateHotelRequest request);

    HotelDetailResponse addAmenities(Long id, List<String> amenityNames);
}
