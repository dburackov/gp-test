package com.gpsolutions.propertyview.service.histogram;

import com.gpsolutions.propertyview.repository.HotelRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandHistogramStrategy implements HistogramStrategy {

    private final HotelRepository hotelRepository;

    @Override
    public String param() {
        return "brand";
    }

    @Override
    public Map<String, Long> calculate() {
        return toHistogram(hotelRepository.countByBrand());
    }
}
