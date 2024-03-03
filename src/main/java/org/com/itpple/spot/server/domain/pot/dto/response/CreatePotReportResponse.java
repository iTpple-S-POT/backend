package org.com.itpple.spot.server.domain.pot.dto.response;

import org.com.itpple.spot.server.domain.pot.domain.potReportHistory.entity.PotReportHistory;
import org.com.itpple.spot.server.global.common.constant.ReportType;

public record CreatePotReportResponse (
        Long reportId,
        Long potId,
        Long reporterId,
        ReportType reportType,
        String reportContent
){

    public static CreatePotReportResponse from(PotReportHistory potReportHistory) {
        return new CreatePotReportResponse(
                potReportHistory.getId(),
                potReportHistory.getPotId(),
                potReportHistory.getReporterId(),
                potReportHistory.getReportType(),
                potReportHistory.getReportContent()
        );
    }
}
