package org.com.itpple.spot.server.dto.pot.request;

import static org.com.itpple.spot.server.model.constant.FileConstant.IMAGE_NAME_REGEX;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record UploadImageRequest(
        @NotNull(message = "File name must not be null")
        @Pattern(regexp = IMAGE_NAME_REGEX, message = "Invalid image file extension")
        String fileName
) {

}
