package pl.olieinik.__2026_summer_assessment_project_gr_20.auth;

import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRole;

public record LoginResponse(Long id, String name, UserRole role) {
}