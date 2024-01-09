package org.com.itpple.spot.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.common.auth.Auth;
import org.com.itpple.spot.server.common.auth.userDetails.CustomUserDetails;
import org.com.itpple.spot.server.dto.location.request.LocationRequest;
import org.com.itpple.spot.server.dto.location.response.LocationResponse;
import org.com.itpple.spot.server.service.impl.LocationServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationServiceImpl locationServiceImpl;

    @PostMapping
    public ResponseEntity<LocationResponse> location(
            @Auth CustomUserDetails customUserDetails,
            @Valid @RequestBody LocationRequest locationRequest
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locationServiceImpl.save(userId,locationRequest));
    }
}
