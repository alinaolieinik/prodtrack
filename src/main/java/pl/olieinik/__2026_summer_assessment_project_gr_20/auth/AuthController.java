package pl.olieinik.__2026_summer_assessment_project_gr_20.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }

    @GetMapping("/users")
    public List<LoginUserOption> getLoginUsers() {
        return authService.getLoginUsers();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        try {
            authService.logout(request.userId());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

}
