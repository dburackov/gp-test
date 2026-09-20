package com.gpsolutions.propertyview.mapper;

import com.gpsolutions.propertyview.domain.Address;
import com.gpsolutions.propertyview.domain.Amenity;
import com.gpsolutions.propertyview.domain.Hotel;
import com.gpsolutions.propertyview.dto.CreateHotelRequest;
import com.gpsolutions.propertyview.dto.HotelDetailResponse;
import com.gpsolutions.propertyview.dto.HotelSummaryResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface HotelMapper {

    @Mapping(target = "address", source = "address", qualifiedByName = "addressLine")
    @Mapping(target = "phone", source = "contacts.phone")
    HotelSummaryResponse toSummary(Hotel hotel);

    @Mapping(target = "amenities", source = "amenities", qualifiedByName = "amenityNames")
    HotelDetailResponse toDetail(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Hotel toEntity(CreateHotelRequest request);

    @Named("addressLine")
    default String addressLine(Address address) {
        if (address == null) {
            return null;
        }
        return "%d %s, %s, %s, %s".formatted(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry());
    }

    @Named("amenityNames")
    default List<String> amenityNames(Set<Amenity> amenities) {
        if (amenities == null) {
            return List.of();
        }
        return amenities.stream()
                .sorted(Comparator.comparing(Amenity::getId))
                .map(Amenity::getName)
                .toList();
    }
}
