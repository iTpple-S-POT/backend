package org.com.itpple.spot.server.domain.pot.dto.request;

import javax.validation.constraints.NotNull;
import org.com.itpple.spot.server.global.common.constant.ReportType;
import org.hibernate.validator.constraints.Length;

public record CreatePotReportRequest(
        @NotNull
        ReportType reportType,
        @Length(max = 250)
        String reportContent

) {

}