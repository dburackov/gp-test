package com.gpsolutions.propertyview.web;

import com.gpsolutions.propertyview.dto.CreateHotelRequest;
import com.gpsolutions.propertyview.dto.HotelDetailResponse;
import com.gpsolutions.propertyview.dto.HotelSearchCriteria;
import com.gpsolutions.propertyview.dto.HotelSummaryResponse;
import com.gpsolutions.propertyview.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Hotels", description = "Отели: список, карточка, поиск, создание, удобства")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/hotels")
    @Operation(summary = "Список всех отелей с краткой информацией")
    @ApiResponse(responseCode = "200", description = "Список отелей")
    public List<HotelSummaryResponse> findAll() {
        return hotelService.findAll();
    }

    @GetMapping("/hotels/{id}")
    @Operation(summary = "Расширенная информация по конкретному отелю")
    @ApiResponse(responseCode = "200", description = "Карточка отеля")
    @ApiResponse(responseCode = "404", description = "Отель не найден", content = @io.swagger.v3.oas.annotations.media.Content)
    public HotelDetailResponse findById(@PathVariable Long id) {
        return hotelService.findById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск отелей по name, brand, city, country, amenities")
    @ApiResponse(responseCode = "200", description = "Найденные отели")
    public List<HotelSummaryResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities) {
        return hotelService.search(new HotelSearchCriteria(name, brand, city, country, amenities));
    }

    @PostMapping("/hotels")
    @Operation(summary = "Создание нового отеля")
    @ApiResponse(responseCode = "200", description = "Отель создан")
    @ApiResponse(responseCode = "400", description = "Ошибка валидации", content = @io.swagger.v3.oas.annotations.media.Content)
    public HotelSummaryResponse create(@Valid @RequestBody CreateHotelRequest request) {
        return hotelService.create(request);
    }

    @PostMapping("/hotels/{id}/amenities")
    @Operation(summary = "Добавление списка amenities к отелю")
    @ApiResponse(responseCode = "200", description = "Краткая информация об отеле")
    @ApiResponse(responseCode = "404", description = "Отель не найден", content = @io.swagger.v3.oas.annotations.media.Content)
    public HotelSummaryResponse addAmenities(
            @PathVariable Long id,
            @RequestBody List<@Size(max = 255) String> amenities) {
        return hotelService.addAmenities(id, amenities);
    }
}
