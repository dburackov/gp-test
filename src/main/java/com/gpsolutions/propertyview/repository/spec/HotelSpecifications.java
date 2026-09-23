package com.gpsolutions.propertyview.repository.spec;

import com.gpsolutions.propertyview.domain.Amenity;
import com.gpsolutions.propertyview.domain.Hotel;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;

public final class HotelSpecifications {

    private static final char ESCAPE_CHAR = '\\';

    private HotelSpecifications() {
    }

    public static Specification<Hotel> nameContains(String value) {
        return contains(value, root -> root.get("name"));
    }

    public static Specification<Hotel> brandEquals(String value) {
        return equalsIgnoreCase(value, root -> root.get("brand"));
    }

    public static Specification<Hotel> cityEquals(String value) {
        return equalsIgnoreCase(value, root -> root.get("address").get("city"));
    }

    public static Specification<Hotel> countryEquals(String value) {
        return equalsIgnoreCase(value, root -> root.get("address").get("country"));
    }

    public static Specification<Hotel> hasAmenity(String value) {
        if (isBlank(value)) {
            return null;
        }
        String normalized = normalize(value);
        return (root, query, builder) -> {
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Hotel> hotel = subquery.from(Hotel.class);
            Join<Hotel, Amenity> amenity = hotel.join("amenities");
            subquery.select(builder.literal(1))
                    .where(builder.equal(hotel.get("id"), root.get("id")),
                            builder.equal(builder.lower(amenity.get("name")), normalized));
            return builder.exists(subquery);
        };
    }

    private static Specification<Hotel> contains(String value, PathResolver resolver) {
        if (isBlank(value)) {
            return null;
        }
        String pattern = toPattern(value);
        return (root, query, builder) ->
                builder.like(builder.lower(resolver.resolve(root)), pattern, ESCAPE_CHAR);
    }

    private static Specification<Hotel> equalsIgnoreCase(String value, PathResolver resolver) {
        if (isBlank(value)) {
            return null;
        }
        String normalized = normalize(value);
        return (root, query, builder) ->
                builder.equal(builder.lower(resolver.resolve(root)), normalized);
    }

    private static String toPattern(String value) {
        String escaped = normalize(value)
                .replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
        return "%" + escaped + "%";
    }

    private static String normalize(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    @FunctionalInterface
    private interface PathResolver {

        Expression<String> resolve(Root<Hotel> root);
    }
}
