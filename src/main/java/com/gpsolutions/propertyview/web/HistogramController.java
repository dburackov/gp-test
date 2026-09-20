package com.gpsolutions.propertyview.web;

import com.gpsolutions.propertyview.service.HistogramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Histogram", description = "Количество отелей по значениям параметра")
public class HistogramController {

    private final HistogramService histogramService;

    @GetMapping("/histogram/{param}")
    @Operation(summary = "Гистограмма по brand, city, country или amenities")
    @ApiResponse(responseCode = "200", description = "Значение параметра -> количество отелей")
    @ApiResponse(responseCode = "400", description = "Неизвестный параметр", content = @io.swagger.v3.oas.annotations.media.Content)
    public Map<String, Long> calculate(@PathVariable String param) {
        return histogramService.calculate(param);
    }
}
