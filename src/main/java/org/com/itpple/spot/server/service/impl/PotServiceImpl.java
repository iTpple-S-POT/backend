package org.com.itpple.spot.server.service.impl;

import static org.com.itpple.spot.server.model.constant.FileConstant.IMAGE_NAME_REGEX;
import static org.com.itpple.spot.server.model.constant.FileConstant.POT_IMAGE_PATH;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.pot.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.UploadImageResponse;
import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;
import org.com.itpple.spot.server.repository.CategoryRepository;
import org.com.itpple.spot.server.service.FileService;
import org.com.itpple.spot.server.service.PotService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PotServiceImpl implements PotService {

    private final FileService fileService;
    private final CategoryRepository categoryRepository;

    @Override
    public GetCategoryResponse getCategory() {
        return GetCategoryResponse.of(categoryRepository.findAll());
    }

    @Override
    public UploadImageResponse uploadImage(String fileName) {
        var uniqueFileName = POT_IMAGE_PATH + this.generateUniqueFileName(fileName);
        return UploadImageResponse.of(fileService.getPreSignedUrl(uniqueFileName), uniqueFileName);
    }

    private String generateUniqueFileName(String fileName) {
        if (!fileName.matches(IMAGE_NAME_REGEX)) {
            throw new CustomException(ErrorCode.FILE_NAME_ILLEGAL);
        }

        var fileKey = fileName.substring(0, fileName.lastIndexOf("."));
        ;
        var fileExtension = fileName.substring(fileName.lastIndexOf("."));

        return fileKey + "_" + UUID.randomUUID() + fileExtension;
    }
}
