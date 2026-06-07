package pl.olieinik.__2026_summer_assessment_project_gr_20.announcement;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService service;

    public AnnouncementController(AnnouncementService service) {
        this.service = service;
    }

    @GetMapping
    public List<Announcement> getActiveAnnouncements() {
        return service.getActiveAnnouncements();
    }


    @PostMapping
    public Announcement createAnnouncement(
            @RequestBody Announcement announcement) {
        return service.createAnnouncement(announcement);
    }

}