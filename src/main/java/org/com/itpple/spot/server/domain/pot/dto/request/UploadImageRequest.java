package org.com.itpple.spot.server.domain.pot.dto.request;

import static org.com.itpple.spot.server.global.common.constant.Constant.IMAGE_NAME_REGEX;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record UploadImageRequest(
        @NotNull
        @Pattern(regexp = IMAGE_NAME_REGEX, message = "Invalid image file extension")
        String fileName
) {

}
