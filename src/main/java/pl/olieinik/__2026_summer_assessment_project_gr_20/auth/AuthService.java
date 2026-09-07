package pl.olieinik.__2026_summer_assessment_project_gr_20.auth;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.olieinik.__2026_summer_assessment_project_gr_20.employeeWorkHistory.EmployeeWorkHistoryService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.loginHistory.UserLoginHistory;
import pl.olieinik.__2026_summer_assessment_project_gr_20.loginHistory.UserLoginHistoryService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRepository;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRole;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserLoginHistoryService loginHistoryService;
    private final EmployeeWorkHistoryService workHistoryService;

    public AuthService(
            UserRepository userRepository,
            UserLoginHistoryService loginHistoryService,
            EmployeeWorkHistoryService workHistoryService
    ) {
        this.userRepository = userRepository;
        this.loginHistoryService = loginHistoryService;
        this.workHistoryService = workHistoryService;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Nieprawidłowy użytkownik lub PIN"));

        if (!user.getPin().equals(request.getPin())) {
            throw new RuntimeException("Nieprawidłowe imię lub PIN ehh");
        }

        loginHistoryService.startSession(user);

        return new LoginResponse(user.getId(), user.getName(), user.getRole());
    }

    @Transactional
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono użytkownika."));

        UserLoginHistory loginHistory = loginHistoryService.closeCurrentSession(user);

        if (user.getRole() == UserRole.OPERATOR) {
            workHistoryService.saveCompletedWork(user, loginHistory.getLoginAt());
        }
    }

    public List<LoginUserOption> getLoginUsers() {
        return userRepository.findAll().stream()
                .map(user -> new LoginUserOption(
                        user.getId(),
                        user.getName() + " " + user.getSurname()
                ))
                .toList();
    }
}
