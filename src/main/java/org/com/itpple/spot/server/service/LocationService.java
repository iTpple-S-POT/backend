package org.com.itpple.spot.server.service;

import org.com.itpple.spot.server.dto.location.request.LocationRequest;
import org.com.itpple.spot.server.dto.location.response.LocationResponse;

public interface LocationService {
    LocationResponse save(Long userId, LocationRequest locationRequest);
}
