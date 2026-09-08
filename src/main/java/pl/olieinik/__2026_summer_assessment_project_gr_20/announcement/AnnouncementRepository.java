package pl.olieinik.__2026_summer_assessment_project_gr_20.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("""
        SELECT a FROM Announcement a
        JOIN FETCH a.createdBy
        WHERE a.validFrom <= CURRENT_TIMESTAMP
        AND (a.validTo IS NULL OR a.validTo > CURRENT_TIMESTAMP)
        ORDER BY a.createdAt DESC
    """)
    List<Announcement> findActiveAnnouncements();

    @Query("""
        SELECT a FROM Announcement a
        JOIN FETCH a.createdBy
        WHERE a.validTo IS NULL OR a.validTo > CURRENT_TIMESTAMP
        ORDER BY a.validFrom DESC, a.createdAt DESC
    """)
    List<Announcement> findActiveOrFutureAnnouncements();
}
