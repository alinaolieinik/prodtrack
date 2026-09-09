package pl.olieinik.__2026_summer_assessment_project_gr_20.announcement;

import java.time.LocalDateTime;

public record AnnouncementCreateRequest(
        String title,
        String message,
        LocalDateTime validFrom,
        LocalDateTime validTo,
        Long authorId
) {
}
