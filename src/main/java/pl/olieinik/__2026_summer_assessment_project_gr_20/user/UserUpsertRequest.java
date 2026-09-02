package pl.olieinik.__2026_summer_assessment_project_gr_20.user;

public record UserUpsertRequest(
        String name,
        String surname,
        UserRole role,
        Long machineId,
        Long productId,
        String pin
) {}