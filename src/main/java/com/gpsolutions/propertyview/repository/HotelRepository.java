package com.gpsolutions.propertyview.repository;

import com.gpsolutions.propertyview.domain.Hotel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Query("""
            select h.brand as value, count(h) as count
            from Hotel h
            where h.brand is not null
            group by h.brand
            """)
    List<HistogramEntry> countByBrand();

    @Query("""
            select h.address.city as value, count(h) as count
            from Hotel h
            where h.address.city is not null
            group by h.address.city
            """)
    List<HistogramEntry> countByCity();

    @Query("""
            select h.address.country as value, count(h) as count
            from Hotel h
            where h.address.country is not null
            group by h.address.country
            """)
    List<HistogramEntry> countByCountry();

    @Query("""
            select a.name as value, count(h) as count
            from Hotel h
            join h.amenities a
            group by a.name
            """)
    List<HistogramEntry> countByAmenity();
}
