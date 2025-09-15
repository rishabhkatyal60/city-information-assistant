package com.cityassistant.controller;

import com.cityassistant.model.CityInfoRequest;
import com.cityassistant.model.CityInfoResponse;
import com.cityassistant.service.CityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CityController {
    
    @Autowired
    private CityInformationService cityService;
    
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "healthy");
        status.put("timestamp", LocalDateTime.now().toString());
        status.put("version", "1.0.0");
        return status;
    }
    
    @PostMapping("/city-info")
    public ResponseEntity<CityInfoResponse> getCityInfo(@RequestBody CityInfoRequest request) {
        try {
            CityInfoResponse response = cityService.getCityInformation(request.getCity());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CityInfoResponse errorResponse = new CityInfoResponse();
            errorResponse.setError("Failed to get city information: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @GetMapping("/city-info/{cityName}")
    public ResponseEntity<CityInfoResponse> getCityInfoByPath(@PathVariable String cityName) {
        try {
            CityInfoResponse response = cityService.getCityInformation(cityName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            CityInfoResponse errorResponse = new CityInfoResponse();
            errorResponse.setError("Failed to get city information: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
