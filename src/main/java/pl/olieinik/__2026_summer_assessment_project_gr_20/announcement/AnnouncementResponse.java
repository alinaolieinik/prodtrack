package pl.olieinik.__2026_summer_assessment_project_gr_20.announcement;

import java.time.LocalDateTime;

public record AnnouncementResponse(
        Long id,
        String title,
        String message,
        String createdBy,
        LocalDateTime createdAt,
        LocalDateTime validFrom,
        LocalDateTime validTo
) {
}
