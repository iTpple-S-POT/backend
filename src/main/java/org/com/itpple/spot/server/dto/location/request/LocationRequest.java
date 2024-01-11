package org.com.itpple.spot.server.dto.location.request;

import org.com.itpple.spot.server.dto.PointDTO;

import javax.validation.constraints.NotNull;

public record LocationRequest(
        @NotNull PointDTO location
) {
}
