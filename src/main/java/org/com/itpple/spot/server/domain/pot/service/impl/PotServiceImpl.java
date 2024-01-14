package org.com.itpple.spot.server.domain.pot.service.impl;

import static org.com.itpple.spot.server.global.common.constant.Constant.POT_IMAGE_PATH;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.pot.category.repository.CategoryRepository;
import org.com.itpple.spot.server.domain.pot.dto.PotDTO;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition.SearchRange;
import org.com.itpple.spot.server.domain.pot.dto.request.CreatePotRequest;
import org.com.itpple.spot.server.domain.pot.dto.response.CreatePotResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.GetCategoryResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.UploadImageResponse;
import org.com.itpple.spot.server.domain.pot.repository.PotRepository;
import org.com.itpple.spot.server.domain.pot.service.PotService;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.global.exception.CustomException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;
import org.com.itpple.spot.server.global.s3.service.FileService;
import org.com.itpple.spot.server.global.util.FileUtil;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PotServiceImpl implements PotService {

    private final FileService fileService;
    private final PotRepository potRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

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

        var category = categoryRepository.findById(createPotRequest.categoryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_CATEGORY));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return CreatePotResponse.from(potRepository.save(CreatePotRequest.toPot(createPotRequest, user, category)));
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
    public List<PotDTO> getPotListForMy(Long userId) {
        return potRepository.findByUserId(userId).stream()
                .map(PotDTO::from)
                .sorted((pot1, pot2) -> pot2.getExpiredAt().compareTo(pot1.getExpiredAt()))
                .toList();
    }

    @Override
    public PotDTO getPot(Long potId, Long userId) {
        return PotDTO.from(potRepository.findById(potId)
                .filter(pot -> pot.getUser().getId().equals(userId) || pot.getExpiredAt().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_POT)));
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
