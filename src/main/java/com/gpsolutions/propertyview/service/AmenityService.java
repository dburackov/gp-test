package com.gpsolutions.propertyview.service;

import com.gpsolutions.propertyview.domain.Amenity;
import com.gpsolutions.propertyview.repository.AmenityRepository;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityRepository amenityRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Amenity create(String name) {
        return amenityRepository.saveAndFlush(Amenity.builder().name(name).build());
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Optional<Amenity> findByName(String name) {
        return amenityRepository.findByNameIn(Set.of(name)).stream().findFirst();
    }
}
