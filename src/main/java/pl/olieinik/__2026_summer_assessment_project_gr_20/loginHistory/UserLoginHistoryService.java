package pl.olieinik.__2026_summer_assessment_project_gr_20.loginHistory;

import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;

import java.time.LocalDateTime;

@Service
public class UserLoginHistoryService {

    private final UserLoginHistoryRepository repository;

    public UserLoginHistoryService(UserLoginHistoryRepository repository) {
        this.repository = repository;
    }

    public UserLoginHistory startSession(User user) {
        UserLoginHistory history = new UserLoginHistory();
        history.setUser(user);
        history.setLoginAt(LocalDateTime.now());

        return repository.save(history);
    }

    public UserLoginHistory getCurrentSession(Long userId) {
        return repository
                .findFirstByUser_IdAndLogoutAtIsNullOrderByLoginAtDesc(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Użytkownik nie ma aktywnej sesji."
                ));
    }

    public UserLoginHistory closeCurrentSession(User user) {
        LocalDateTime now = LocalDateTime.now();

        UserLoginHistory history = repository
                .findFirstByUser_IdAndLogoutAtIsNullOrderByLoginAtDesc(user.getId())
                .orElseGet(() -> {
                    UserLoginHistory missingHistory = new UserLoginHistory();
                    missingHistory.setUser(user);
                    missingHistory.setLoginAt(now);
                    return missingHistory;
                });

        history.setLogoutAt(now);
        return repository.save(history);
    }
}
