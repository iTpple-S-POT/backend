package org.com.itpple.spot.server.service.impl;

import static org.com.itpple.spot.server.model.constant.FileConstant.POT_IMAGE_PATH;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;
import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;
import org.com.itpple.spot.server.repository.CategoryRepository;
import org.com.itpple.spot.server.service.FileService;
import org.com.itpple.spot.server.service.PotService;
import org.com.itpple.spot.server.util.FileUtil;
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
        var uniqueFileName = POT_IMAGE_PATH + FileUtil.generateUniqueNameForImage(fileName);
        return UploadImageResponse.of(fileService.getPreSignedUrl(uniqueFileName), uniqueFileName);
    }

    @Override
    public CreatePotResponse createPot(CreatePotRequest createPotRequest) {
        return null;
    }
}
