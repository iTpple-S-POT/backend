package org.com.itpple.spot.server.global.util;

import static org.com.itpple.spot.server.global.common.constant.Constant.IMAGE_NAME_REGEX;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FileUtil {

    public static String generateUniqueNameForImage(String imageName) {
        if (!imageName.matches(IMAGE_NAME_REGEX)) {
            throw new CustomException(ErrorCode.ILLEGAL_FILE_NAME);
        }

        var fileKey = imageName.substring(0, imageName.lastIndexOf("."));
        var fileExtension = imageName.substring(imageName.lastIndexOf("."));

        return fileKey + "_" + UUID.randomUUID() + fileExtension;
    }
}
