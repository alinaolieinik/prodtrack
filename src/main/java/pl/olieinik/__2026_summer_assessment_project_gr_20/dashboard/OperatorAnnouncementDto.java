package pl.olieinik.__2026_summer_assessment_project_gr_20.dashboard;

import java.time.LocalDateTime;

public record OperatorAnnouncementDto(
        Long id,
        String title,
        String message,
        String createdBy,
        LocalDateTime validTo
) {
}
