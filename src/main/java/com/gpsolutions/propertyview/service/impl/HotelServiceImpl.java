package com.gpsolutions.propertyview.service.impl;

import com.gpsolutions.propertyview.domain.Amenity;
import com.gpsolutions.propertyview.domain.Hotel;
import com.gpsolutions.propertyview.dto.CreateHotelRequest;
import com.gpsolutions.propertyview.dto.HotelDetailResponse;
import com.gpsolutions.propertyview.dto.HotelSearchCriteria;
import com.gpsolutions.propertyview.dto.HotelSummaryResponse;
import com.gpsolutions.propertyview.exception.HotelNotFoundException;
import com.gpsolutions.propertyview.mapper.HotelMapper;
import com.gpsolutions.propertyview.repository.AmenityRepository;
import com.gpsolutions.propertyview.repository.HotelRepository;
import com.gpsolutions.propertyview.repository.spec.HotelSpecifications;
import com.gpsolutions.propertyview.service.AmenityService;
import com.gpsolutions.propertyview.service.HotelService;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelServiceImpl implements HotelService {

    private static final Sort BY_ID = Sort.by(Sort.Direction.ASC, "id");

    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final AmenityService amenityService;
    private final HotelMapper hotelMapper;

    @Override
    public List<HotelSummaryResponse> findAll() {
        return hotelRepository.findAll(BY_ID).stream()
                .map(hotelMapper::toSummary)
                .toList();
    }

    @Override
    public HotelDetailResponse findById(Long id) {
        return hotelMapper.toDetail(getHotel(id));
    }

    @Override
    public List<HotelSummaryResponse> search(HotelSearchCriteria criteria) {
        List<Specification<Hotel>> specifications = new ArrayList<>();
        specifications.add(HotelSpecifications.nameContains(criteria.name()));
        specifications.add(HotelSpecifications.brandContains(criteria.brand()));
        specifications.add(HotelSpecifications.cityContains(criteria.city()));
        specifications.add(HotelSpecifications.countryContains(criteria.country()));
        if (criteria.amenities() != null) {
            criteria.amenities().stream()
                    .map(HotelSpecifications::hasAmenity)
                    .forEach(specifications::add);
        }
        List<Specification<Hotel>> applied = specifications.stream()
                .filter(Objects::nonNull)
                .toList();
        if (applied.isEmpty()) {
            return findAll();
        }
        return hotelRepository.findAll(Specification.allOf(applied), BY_ID).stream()
                .map(hotelMapper::toSummary)
                .toList();
    }

    @Override
    @Transactional
    public HotelSummaryResponse create(CreateHotelRequest request) {
        Hotel hotel = hotelMapper.toEntity(request);
        return hotelMapper.toSummary(hotelRepository.save(hotel));
    }

    @Override
    @Transactional
    public HotelDetailResponse addAmenities(Long id, List<String> amenityNames) {
        Hotel hotel = getHotel(id);
        Set<String> requested = amenityNames == null ? Set.of() : amenityNames.stream()
                .filter(Objects::nonNull)
                .filter(name -> !name.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (requested.isEmpty()) {
            return hotelMapper.toDetail(hotel);
        }
        Map<String, Amenity> existing = amenityRepository.findByNameIn(requested).stream()
                .collect(Collectors.toMap(Amenity::getName, Function.identity()));
        for (String name : requested) {
            Amenity amenity = existing.get(name);
            if (amenity == null) {
                amenity = resolveAmenity(name);
            }
            hotel.getAmenities().add(amenity);
        }
        return hotelMapper.toDetail(hotel);
    }

    private Amenity resolveAmenity(String name) {
        try {
            return amenityService.create(name);
        } catch (DataIntegrityViolationException conflict) {
            return amenityService.findByName(name).orElseThrow(() -> conflict);
        }
    }

    private Hotel getHotel(Long id) {
        return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException(id));
    }
}
