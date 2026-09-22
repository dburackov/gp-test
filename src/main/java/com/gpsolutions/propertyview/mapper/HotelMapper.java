package com.gpsolutions.propertyview.mapper;

import com.gpsolutions.propertyview.domain.Address;
import com.gpsolutions.propertyview.domain.Amenity;
import com.gpsolutions.propertyview.domain.ArrivalTime;
import com.gpsolutions.propertyview.domain.Contacts;
import com.gpsolutions.propertyview.domain.Hotel;
import com.gpsolutions.propertyview.dto.AddressDto;
import com.gpsolutions.propertyview.dto.ArrivalTimeDto;
import com.gpsolutions.propertyview.dto.ContactsDto;
import com.gpsolutions.propertyview.dto.CreateHotelRequest;
import com.gpsolutions.propertyview.dto.HotelDetailResponse;
import com.gpsolutions.propertyview.dto.HotelSummaryResponse;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public HotelSummaryResponse toSummary(Hotel hotel) {
        if (hotel == null) {
            return null;
        }
        return new HotelSummaryResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                addressLine(hotel.getAddress()),
                hotel.getContacts() == null ? null : hotel.getContacts().getPhone());
    }

    public HotelDetailResponse toDetail(Hotel hotel) {
        if (hotel == null) {
            return null;
        }
        return new HotelDetailResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                toAddressDto(hotel.getAddress()),
                toContactsDto(hotel.getContacts()),
                toArrivalTimeDto(hotel.getArrivalTime()),
                amenityNames(hotel.getAmenities()));
    }

    public Hotel toEntity(CreateHotelRequest request) {
        if (request == null) {
            return null;
        }
        return Hotel.builder()
                .name(request.name())
                .description(request.description())
                .brand(request.brand())
                .address(toAddress(request.address()))
                .contacts(toContacts(request.contacts()))
                .arrivalTime(toArrivalTime(request.arrivalTime()))
                .build();
    }

    private String addressLine(Address address) {
        if (address == null) {
            return null;
        }
        return "%d %s, %s, %s, %s".formatted(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry()
        );
    }

    private List<String> amenityNames(Set<Amenity> amenities) {
        if (amenities == null) {
            return List.of();
        }
        return amenities.stream()
                .sorted(Comparator.comparing(Amenity::getId))
                .map(Amenity::getName)
                .toList();
    }

    private AddressDto toAddressDto(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDto(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode()
        );
    }

    private ContactsDto toContactsDto(Contacts contacts) {
        if (contacts == null) {
            return null;
        }
        return new ContactsDto(contacts.getPhone(), contacts.getEmail());
    }

    private ArrivalTimeDto toArrivalTimeDto(ArrivalTime arrivalTime) {
        if (arrivalTime == null) {
            return null;
        }
        return new ArrivalTimeDto(arrivalTime.getCheckIn(), arrivalTime.getCheckOut());
    }

    private Address toAddress(AddressDto address) {
        if (address == null) {
            return null;
        }
        return Address.builder()
                .houseNumber(address.houseNumber())
                .street(address.street())
                .city(address.city())
                .country(address.country())
                .postCode(address.postCode())
                .build();
    }

    private Contacts toContacts(ContactsDto contacts) {
        if (contacts == null) {
            return null;
        }
        return Contacts.builder()
                .phone(contacts.phone())
                .email(contacts.email())
                .build();
    }

    private ArrivalTime toArrivalTime(ArrivalTimeDto arrivalTime) {
        if (arrivalTime == null) {
            return null;
        }
        return ArrivalTime.builder()
                .checkIn(arrivalTime.checkIn())
                .checkOut(arrivalTime.checkOut())
                .build();
    }
}
