package org.com.itpple.spot.server.service.impl;

import static org.com.itpple.spot.server.constant.Constant.POT_IMAGE_PATH;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.PotDto;
import org.com.itpple.spot.server.dto.SearchCondition.SearchRange;
import org.com.itpple.spot.server.dto.pot.request.CreatePotRequest;
import org.com.itpple.spot.server.dto.pot.response.CreatePotResponse;
import org.com.itpple.spot.server.dto.pot.response.GetCategoryResponse;
import org.com.itpple.spot.server.dto.pot.response.UploadImageResponse;
import org.com.itpple.spot.server.exception.CustomException;
import org.com.itpple.spot.server.exception.code.ErrorCode;
import org.com.itpple.spot.server.repository.CategoryRepository;
import org.com.itpple.spot.server.repository.PotRepository;
import org.com.itpple.spot.server.service.FileService;
import org.com.itpple.spot.server.service.PotService;
import org.com.itpple.spot.server.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PotServiceImpl implements PotService {

    private final FileService fileService;
    private final PotRepository potRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public GetCategoryResponse getCategory() {
        return GetCategoryResponse.of(categoryRepository.findAll());
    }

    @Override
    public UploadImageResponse uploadImage(Long userId, String fileName) {
        var uniqueFileName = POT_IMAGE_PATH + FileUtil.generateUniqueNameForImage(fileName);
        return UploadImageResponse.of(fileService.getPreSignedUrl(uniqueFileName), uniqueFileName);
    }

    @Transactional
    @Override
    public CreatePotResponse createPot(Long userId, CreatePotRequest createPotRequest) {

        var isUploadedImage = fileService.isUploaded(createPotRequest.imageKey());
        if (!isUploadedImage) {
            throw new CustomException(ErrorCode.INVALID_FILE_KEY);
        }

        return CreatePotResponse.from(potRepository.save(CreatePotRequest.toPot(createPotRequest)));
    }

    @Override
    public List<PotDto> getPotListWithoutExpired(SearchRange searchRange, Long categoryId) {
        return potRepository.findByLocationAndCategory(searchRange.polygon(),
                categoryId).stream().map(PotDto::from).toList();
    }


    @Override
    public List<PotDto> getPotList(SearchRange searchRange, Long categoryId) {
        return potRepository.findByLocationAndCategory(searchRange.polygon(), categoryId)
                .stream()
                .map(PotDto::from).toList();
    }
}
