package com.gpsolutions.propertyview.exception;

import java.util.Collection;

public class UnknownHistogramParameterException extends RuntimeException {

    public UnknownHistogramParameterException(String param, Collection<String> supported) {
        super("Unknown histogram parameter '%s'. Supported parameters: %s".formatted(param, String.join(", ", supported)));
    }
}
