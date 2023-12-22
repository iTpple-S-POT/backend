package org.com.itpple.spot.server.dto;

import javax.validation.constraints.NotNull;

public record PointDTO(
        @NotNull Double lat,
        @NotNull Double lon) {

}
