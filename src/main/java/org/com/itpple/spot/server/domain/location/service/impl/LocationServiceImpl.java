package org.com.itpple.spot.server.domain.location.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.location.dto.request.LocationRequest;
import org.com.itpple.spot.server.domain.location.dto.response.LocationResponse;
import org.com.itpple.spot.server.domain.location.entity.Location;
import org.com.itpple.spot.server.domain.location.exception.LocationIdNotFoundException;
import org.com.itpple.spot.server.domain.user.entity.User;
import org.com.itpple.spot.server.domain.user.exception.UserIdNotFoundException;
import org.com.itpple.spot.server.domain.location.repository.LocationRepository;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.domain.location.service.LocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LocationServiceImpl implements LocationService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public LocationResponse save(Long userId, LocationRequest locationRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException("PK = " + userId));
        Location location = Location.builder()
                .user(user)
                .location(locationRequest.location())
                .build();

        Location savedLocation = locationRepository.save(location);

        return LocationResponse.fromLocation(savedLocation);
    }

    public LocationResponse getLocation(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserIdNotFoundException("PK = " + userId));
            List<Location> locations = locationRepository.findByUserId(userId)
                    .orElseThrow(() -> new LocationIdNotFoundException("userId = " + userId));
            Location latestLocation = locations.get(0);
            return LocationResponse.fromLocation(latestLocation);
        } catch (IndexOutOfBoundsException e) {
            throw new LocationIdNotFoundException("userId = " + userId);
        }
    }
}