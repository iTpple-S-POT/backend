package org.com.itpple.spot.server.domain.location.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.location.service.LocationService;
import org.com.itpple.spot.server.global.auth.Auth;
import org.com.itpple.spot.server.global.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.domain.location.dto.request.LocationRequest;
import org.com.itpple.spot.server.domain.location.dto.response.LocationResponse;
import org.com.itpple.spot.server.domain.location.service.impl.LocationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<LocationResponse> location(
            @Auth CustomUserDetails customUserDetails,
            @Valid @RequestBody LocationRequest locationRequest
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationService.save(userId,locationRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocation(
            @Auth CustomUserDetails customUserDetails,
            @PathVariable Long id
    ){
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationService.getLocation(userId,id));
    }
}
