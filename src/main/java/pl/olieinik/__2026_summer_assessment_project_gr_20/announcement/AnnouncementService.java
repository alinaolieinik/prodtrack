package pl.olieinik.__2026_summer_assessment_project_gr_20.announcement;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository repository;

    public AnnouncementService(AnnouncementRepository repository) {
        this.repository = repository;
    }

    public List<Announcement> getActiveAnnouncements() {
        return repository.findActiveAnnouncements();
    }

    public Announcement createAnnouncement(Announcement a) {
        a.setCreatedAt(LocalDateTime.now());

        if (a.getValidFrom() == null) {
            a.setValidFrom(LocalDateTime.now());
        }

        return repository.save(a);
    }


}