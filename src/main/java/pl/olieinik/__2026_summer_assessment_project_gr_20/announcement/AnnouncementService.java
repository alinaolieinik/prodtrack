package pl.olieinik.__2026_summer_assessment_project_gr_20.announcement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRole;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository repository;
    private final UserService userService;

    public AnnouncementService(
            AnnouncementRepository repository,
            UserService userService
    ) {
        this.repository = repository;
        this.userService = userService;
    }

    public List<Announcement> getActiveAnnouncements() {
        return repository.findActiveAnnouncements();
    }

    public List<Announcement> getActiveOrFutureAnnouncements(Long managerId) {
        requireManager(managerId);
        return repository.findActiveOrFutureAnnouncements();
    }

    public Announcement createAnnouncement(AnnouncementCreateRequest request) {
        validateRequest(request);

        User author = userService.getUser(request.authorId());

        if (author.getRole() != UserRole.MANAGER) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Tylko manager może tworzyć ogłoszenia."
            );
        }

        Announcement announcement = new Announcement();
        announcement.setTitle(request.title().trim());
        announcement.setMessage(request.message().trim());
        announcement.setValidFrom(request.validFrom());
        announcement.setValidTo(request.validTo());
        announcement.setCreatedAt(LocalDateTime.now());
        announcement.setCreatedBy(author);

        return repository.save(announcement);
    }

    public void cancelAnnouncement(Long announcementId, Long managerId) {
        requireManager(managerId);

        Announcement announcement = repository.findById(announcementId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Nie znaleziono ogłoszenia."
                ));

        LocalDateTime now = LocalDateTime.now();

        if (announcement.getValidTo() != null
                && !announcement.getValidTo().isAfter(now)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "To ogłoszenie nie jest już aktywne ani przyszłe."
            );
        }

        announcement.setValidTo(now);
        repository.save(announcement);
    }

    private void validateRequest(AnnouncementCreateRequest request) {
        if (request.title() == null || request.title().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tytuł jest wymagany.");
        }

        if (request.message() == null || request.message().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Treść jest wymagana.");
        }

        if (request.authorId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nie podano autora ogłoszenia.");
        }

        if (request.validFrom() == null || request.validTo() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Podaj datę i godzinę rozpoczęcia oraz zakończenia ważności."
            );
        }

        if (!request.validTo().isAfter(request.validFrom())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Data zakończenia ważności musi być późniejsza niż data rozpoczęcia."
            );
        }
    }

    private void requireManager(Long managerId) {
        if (managerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nie podano managera.");
        }

        User manager = userService.getUser(managerId);

        if (manager.getRole() != UserRole.MANAGER) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Tylko manager może zarządzać ogłoszeniami."
            );
        }
    }
}
