package org.com.itpple.spot.server.domain.location.dto.request;

import org.com.itpple.spot.server.domain.location.dto.PointDTO;

import javax.validation.constraints.NotNull;

public record LocationRequest(
        @NotNull PointDTO location
) {
}
