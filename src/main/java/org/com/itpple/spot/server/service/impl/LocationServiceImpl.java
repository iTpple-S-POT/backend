package org.com.itpple.spot.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.dto.location.request.LocationRequest;
import org.com.itpple.spot.server.dto.location.response.LocationResponse;
import org.com.itpple.spot.server.entity.Location;
import org.com.itpple.spot.server.entity.User;
import org.com.itpple.spot.server.exception.user.UserIdNotFoundException;
import org.com.itpple.spot.server.repository.LocationRepository;
import org.com.itpple.spot.server.repository.UserRepository;
import org.com.itpple.spot.server.service.LocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LocationServiceImpl implements LocationService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Transactional
    @Override
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
}
