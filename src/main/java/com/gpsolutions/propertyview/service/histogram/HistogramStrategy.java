package com.gpsolutions.propertyview.service.histogram;

import com.gpsolutions.propertyview.repository.HistogramEntry;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface HistogramStrategy {

    String param();

    Map<String, Long> calculate();

    default Map<String, Long> toHistogram(List<HistogramEntry> entries) {
        return entries.stream()
                .sorted(Comparator.comparing(HistogramEntry::getValue))
                .collect(Collectors.toMap(
                        HistogramEntry::getValue,
                        HistogramEntry::getCount,
                        (first, second) -> first,
                        LinkedHashMap::new));
    }
}
