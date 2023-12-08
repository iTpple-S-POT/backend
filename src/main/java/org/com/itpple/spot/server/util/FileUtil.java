package org.com.itpple.spot.server.util;

import static org.com.itpple.spot.server.model.constant.FileConstant.IMAGE_NAME_REGEX;

import java.util.UUID;
import lombok.AllArgsConstructor;
import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;

@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FileUtil {
    public static String generateUniqueNameForImage(String imageName) {
        if (!imageName.matches(IMAGE_NAME_REGEX)) {
            throw new CustomException(ErrorCode.FILE_NAME_ILLEGAL);
        }

        var fileKey = imageName.substring(0, imageName.lastIndexOf("."));
        ;
        var fileExtension = imageName.substring(imageName.lastIndexOf("."));

        return fileKey + "_" + UUID.randomUUID() + fileExtension;
    }
}
