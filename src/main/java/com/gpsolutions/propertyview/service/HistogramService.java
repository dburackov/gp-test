package com.gpsolutions.propertyview.service;

import com.gpsolutions.propertyview.exception.UnknownHistogramParameterException;
import com.gpsolutions.propertyview.service.histogram.HistogramStrategy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class HistogramService {

    private final Map<String, HistogramStrategy> strategies;

    public HistogramService(List<HistogramStrategy> strategies) {
        this.strategies = strategies.stream()
                .sorted((first, second) -> first.param().compareTo(second.param()))
                .collect(Collectors.toMap(
                        strategy -> strategy.param().toLowerCase(Locale.ROOT),
                        Function.identity(),
                        (first, second) -> first,
                        LinkedHashMap::new));
    }

    public Map<String, Long> calculate(String param) {
        HistogramStrategy strategy = param == null ? null : strategies.get(param.toLowerCase(Locale.ROOT));
        if (strategy == null) {
            throw new UnknownHistogramParameterException(param, strategies.keySet());
        }
        return strategy.calculate();
    }
}
