package pl.olieinik.__2026_summer_assessment_project_gr_20.announcement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService service;

    public AnnouncementController(AnnouncementService service) {
        this.service = service;
    }

    @GetMapping
    public List<AnnouncementResponse> getActiveAnnouncements() {
        return service.getActiveAnnouncements().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/manageable")
    public List<AnnouncementResponse> getActiveOrFutureAnnouncements(
            @RequestParam Long managerId
    ) {
        return service.getActiveOrFutureAnnouncements(managerId).stream()
                .map(this::toResponse)
                .toList();
    }


    @PostMapping
    public AnnouncementResponse createAnnouncement(
            @RequestBody AnnouncementCreateRequest request) {
        return toResponse(service.createAnnouncement(request));
    }

    @PatchMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelAnnouncement(
            @PathVariable Long id,
            @RequestBody AnnouncementCancelRequest request
    ) {
        service.cancelAnnouncement(id, request.managerId());
    }

    private AnnouncementResponse toResponse(Announcement announcement) {
        User author = announcement.getCreatedBy();

        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getMessage(),
                author.getName() + " " + author.getSurname(),
                announcement.getCreatedAt(),
                announcement.getValidFrom(),
                announcement.getValidTo()
        );
    }

}
