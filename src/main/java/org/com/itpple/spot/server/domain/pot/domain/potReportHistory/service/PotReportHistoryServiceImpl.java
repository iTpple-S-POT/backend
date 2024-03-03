package org.com.itpple.spot.server.domain.pot.domain.potReportHistory.service;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.com.itpple.spot.server.domain.pot.domain.potReportHistory.entity.PotReportHistory;
import org.com.itpple.spot.server.domain.pot.domain.potReportHistory.repository.PotReportHistoryRepository;
import org.com.itpple.spot.server.domain.pot.dto.request.CreatePotReportRequest;
import org.com.itpple.spot.server.domain.pot.dto.response.CreatePotReportResponse;
import org.com.itpple.spot.server.domain.pot.repository.PotRepository;
import org.com.itpple.spot.server.domain.user.repository.UserRepository;
import org.com.itpple.spot.server.global.exception.BusinessException;
import org.com.itpple.spot.server.global.exception.code.ErrorCode;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PotReportHistoryServiceImpl implements
        PotReportHistoryService {


    private final PotRepository potRepository;
    private final UserRepository userRepository;
    private final PotReportHistoryRepository potReportHistoryRepository;

    @Override
    @Transactional
    public CreatePotReportResponse reportPot(Long userId, Long potId,
            CreatePotReportRequest createPotReportRequest) {
        var pot = potRepository.findById(potId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POT));
        if (pot.getUser().getId().equals(userId)) {
            throw new BusinessException(ErrorCode.CANNOT_REPORT_MY_POT);
        }

        var isReported = potReportHistoryRepository.existsByPotIdAndReporterId(potId, userId);
        if (isReported) {
            throw new BusinessException(ErrorCode.ALREADY_REPORTED_POT);
        }

        var reporter = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER));

        var potReportHistory = potReportHistoryRepository.save(
                PotReportHistory.builder().reporterId(reporter.getId()).potId(pot.getId())
                        .reportType(createPotReportRequest.reportType()).build());

        if (potReportHistoryRepository.countByPotId(potId) >= 5) {
            potRepository.delete(pot);
        }

        return CreatePotReportResponse.from(potReportHistory);
    }
}
