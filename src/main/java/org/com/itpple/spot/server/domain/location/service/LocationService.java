package org.com.itpple.spot.server.domain.location.service;

import org.com.itpple.spot.server.domain.location.dto.request.LocationRequest;
import org.com.itpple.spot.server.domain.location.dto.response.LocationResponse;

public interface LocationService {
    LocationResponse save(Long userId, LocationRequest locationRequest);

    LocationResponse getLocation(Long userId);
}
