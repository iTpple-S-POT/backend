package org.com.itpple.spot.server.service.impl;

import static org.com.itpple.spot.server.constant.Constant.POT_IMAGE_PATH;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.dto.pot.PotDTO;
import org.com.itpple.spot.server.dto.pot.SearchCondition.SearchRange;
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

    @Override
    public CreatePotResponse createPot(Long userId, CreatePotRequest createPotRequest) {

        var isUploadedImage = fileService.isUploaded(createPotRequest.imageKey());
        if (!isUploadedImage) {
            throw new CustomException(ErrorCode.INVALID_FILE_KEY);
        }

        return CreatePotResponse.from(potRepository.save(CreatePotRequest.toPot(createPotRequest)));
    }

    @Override
    public List<PotDTO> getPotList(SearchRange searchRange, Long categoryId) {
        return potRepository.findByLocationAndCategoryId(searchRange.polygon(), categoryId)
                .stream()
                .map(PotDTO::from)
                .sorted((pot1, pot2) -> pot2.getExpiredAt().compareTo(pot1.getExpiredAt()))
                .toList();
    }

    @Override
    public List<PotDTO> getPotListForAdmin(SearchRange searchRange, Long categoryId) {
        return potRepository.findByLocationAndCategoryForAdmin(searchRange.polygon(),
                        categoryId).stream()
                .map(PotDTO::from)
                .sorted((pot1, pot2) -> pot2.getExpiredAt().compareTo(pot1.getExpiredAt()))
                .toList();
    }
}
