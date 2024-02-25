package org.com.itpple.spot.server.global.util;

import lombok.AllArgsConstructor;
import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;

import java.util.UUID;

import static org.com.itpple.spot.server.global.common.constant.Constant.IMAGE_NAME_REGEX;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FileUtil {

    public static String generateUniqueNameForImage(String imageName) {
        if (!imageName.matches(IMAGE_NAME_REGEX)) {
            throw new BusinessException(ErrorCode.ILLEGAL_FILE_NAME);
        }

        var fileKey = imageName.substring(0, imageName.lastIndexOf("."));
        var fileExtension = imageName.substring(imageName.lastIndexOf("."));

        return fileKey + "_" + UUID.randomUUID() + fileExtension;
    }
}
