package org.com.itpple.spot.server.domain.pot.domain.potReportHistory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.com.itpple.spot.server.global.common.constant.ReportType;
import org.com.itpple.spot.server.global.common.entity.BasicDateEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PotReportHistory extends BasicDateEntity {
    @Column(name = "pot_report_history_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "pot_id", nullable = false)
    private Long potId;

    @Column(name = "reporter_id", nullable = false)
    private Long reporterId;

    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "report_content", length = 250)
    private String reportContent;

}
