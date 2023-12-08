package org.com.itpple.spot.server.service.impl;

import static org.com.itpple.spot.server.model.constant.FileConstant.IMAGE_EXTENSION_REGEX;
import static org.com.itpple.spot.server.model.constant.FileConstant.POT_IMAGE_PATH;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.pot.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.UploadImageResponse;
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
        return UploadImageResponse.of(fileService.getPreSignedUrl(uniqueFileName));
    }

    private String generateUniqueFileName(String fileName) {
        String[] fileParts = fileName.split(IMAGE_EXTENSION_REGEX);

        var fileKey = fileParts[0];
        var fileExtension = fileParts[1];

        return fileKey + "_" + UUID.randomUUID() + fileExtension;
    }
}
