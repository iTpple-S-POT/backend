package org.com.itpple.spot.server.domain.pot.service.impl;

import static org.com.itpple.spot.server.global.common.constant.Constant.POT_IMAGE_PATH;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.pot.domain.category.repository.CategoryRepository;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.entity.Hashtag;
import org.com.itpple.spot.server.domain.pot.domain.hashtag.repository.HashtagRepository;
import org.com.itpple.spot.server.domain.pot.domain.potReportHistory.entity.PotReportHistory;
import org.com.itpple.spot.server.domain.pot.domain.potReportHistory.repository.PotReportHistoryRepository;
import org.com.itpple.spot.server.domain.pot.domain.viewHistory.entity.ViewHistory;
import org.com.itpple.spot.server.domain.pot.domain.viewHistory.repository.ViewHistoryRepository;
import org.com.itpple.spot.server.domain.pot.dto.PotDTO;
import org.com.itpple.spot.server.domain.pot.dto.SearchCondition.SearchRange;
import org.com.itpple.spot.server.domain.pot.dto.request.CreatePotRequest;
import org.com.itpple.spot.server.domain.pot.dto.response.CreatePotResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.GetCategoryResponse;
import org.com.itpple.spot.server.domain.pot.dto.response.UploadImageResponse;
import org.com.itpple.spot.server.domain.pot.entity.Pot;
import org.com.itpple.spot.server.domain.pot.repository.PotRepository;
import org.com.itpple.spot.server.domain.pot.service.PotService;
import org.com.itpple.spot.server.domain.reaction.repository.ReactionRepository;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;
import org.com.itpple.spot.server.global.s3.service.FileService;
import org.com.itpple.spot.server.global.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class PotServiceImpl implements PotService {

    private final FileService fileService;
    private final PotRepository potRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    private final ViewHistoryRepository viewHistoryRepository;
    private final ReactionRepository reactionRepository;
    private final PotReportHistoryRepository potReportHistoryRepository;

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
            throw new BusinessException(ErrorCode.INVALID_FILE_KEY);
        }

        var category = categoryRepository.findById(createPotRequest.categoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        var hashtagList = CollectionUtils.isEmpty(createPotRequest.hashtagIdList()) ?
                new ArrayList<Hashtag>()
                : hashtagRepository.findAllById(createPotRequest.hashtagIdList());

        return CreatePotResponse.from(potRepository.save(
                CreatePotRequest.toPot(createPotRequest, user, category, hashtagList)));
    }

    @Override
    public List<PotDTO> getPotList(SearchRange searchRange, Long categoryId, Long hashtagId,
            Long userId) {

        var potList = potRepository.findBySearchCondition(searchRange.polygon(), categoryId,
                        hashtagId)
                .stream()
                .map(PotDTO::from)
                .sorted((pot1, pot2) -> pot2.getExpiredAt().compareTo(pot1.getExpiredAt()))
                .toList();

        // 내가 신고한 팟 제외
        var reportedPotIdList = userId != null ? potReportHistoryRepository.findAllByReporterId(userId).stream()
                .map(PotReportHistory::getPotId)
                .toList() : new ArrayList<>();

        return potList.stream()
                .filter(pot -> !reportedPotIdList.contains(pot.getId()))
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
    @Transactional(readOnly = true)
    public List<PotDTO> getPotListByRecentlyViewed(Long userId) {
        var viewHistoryList = viewHistoryRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        var potIdList = new ArrayList<>(viewHistoryList.stream()
                .map(viewHistory -> viewHistory.getPot().getId())
                .toList());

        var reportedPotIdList = potReportHistoryRepository.findAllByReporterId(userId).stream()
                .map(PotReportHistory::getPotId)
                .toList();

        potIdList.removeAll(reportedPotIdList);

        return potRepository.findByIdAndNotExpired(potIdList).stream()
                .map(PotDTO::from)
                .sorted((pot1, pot2) -> potIdList.indexOf(pot2.getId()) - potIdList.indexOf(
                        pot1.getId()))
                .toList();
    }


    @Override
    @Transactional
    public PotDTO getPot(Long potId, Long userId) {
        return potRepository.findById(potId)
                .filter(pot -> pot.getUser().getId().equals(userId) || pot.getExpiredAt()
                        .isAfter(LocalDateTime.now()))
                .map(pot -> {
                    if (!pot.getUser().getId().equals(userId)
                            && !viewHistoryRepository.existsByPotIdAndUserId(potId, userId)) {
                        pot.addViewCount();
                        this.createViewHistory(userId, pot);
                    }
                    return PotDTO.from(pot, reactionRepository.countEachReactionType(potId));
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POT));
    }


    @Override
    public List<PotDTO> getPotListForAdmin(SearchRange searchRange, Long categoryId,
            Long hashtagId) {
        return potRepository.findBySearchConditionForAdmin(searchRange.polygon(), categoryId,
                        hashtagId).stream()
                .map(PotDTO::from)
                .sorted((pot1, pot2) -> pot2.getExpiredAt().compareTo(pot1.getExpiredAt()))
                .toList();
    }

    private void createViewHistory(Long userId, Pot pot) {
        if (pot.getUser().getId().equals(userId)) {
            return;
        }

        var viewedUser = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        viewHistoryRepository.save(ViewHistory.of(viewedUser, pot));

    }
}
