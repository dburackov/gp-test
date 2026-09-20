package com.gpsolutions.propertyview.repository;

import com.gpsolutions.propertyview.domain.Amenity;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    List<Amenity> findByNameIn(Collection<String> names);
}
