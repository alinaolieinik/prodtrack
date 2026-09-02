package pl.olieinik.__2026_summer_assessment_project_gr_20.auth;

import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRepository;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Nieprawidłowy użytkownik lub PIN"));

        if (!user.getPin().equals(request.getPin())) {
            throw new RuntimeException("Nieprawidłowe imię lub PIN ehh");
        }

        return new LoginResponse(user.getId(), user.getName(), user.getRole());
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