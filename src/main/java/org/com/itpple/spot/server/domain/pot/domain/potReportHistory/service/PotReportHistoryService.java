package org.com.itpple.spot.server.domain.pot.domain.potReportHistory.service;

import org.com.itpple.spot.server.domain.pot.dto.request.CreatePotReportRequest;
import org.com.itpple.spot.server.domain.pot.dto.response.CreatePotReportResponse;

public interface PotReportHistoryService {

    //reportPot
    public CreatePotReportResponse reportPot(Long userId, Long potId, CreatePotReportRequest createPotReportRequest);

}
