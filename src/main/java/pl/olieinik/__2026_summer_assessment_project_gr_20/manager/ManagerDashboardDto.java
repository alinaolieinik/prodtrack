package pl.olieinik.__2026_summer_assessment_project_gr_20.manager;

import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.MachineState;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRole;

import java.time.LocalDateTime;
import java.util.List;

public record ManagerDashboardDto(
        ManagerDto manager,
        SummaryDto summary,
        List<UserDto> users,
        List<MachineDto> machines,
        List<AnnouncementDto> announcements
) {

    public record ManagerDto(
            Long id,
            String name,
            String surname
    ) {}

    public record SummaryDto(
            long operatorsCount,
            long machinesCount,
            long workingMachinesCount,
            long waitingMachinesCount,
            long failedMachinesCount,
            long activeAnnouncementsCount
    ) {}

    public record UserDto(
            Long id,
            String name,
            String surname,
            UserRole role,
            Long machineId,
            String machineName,
            Long productId,
            String productName,
            String productCode
    ) {}

    public record MachineDto(
            Long id,
            String name,
            MachineState state,
            LocalDateTime lastUpdate
    ) {}

    public record NormDto(
            Long id,
            Integer targetPerShift,
            Integer exceedTargetPerShift,
            Integer minimumPerShift,
            Double secondsPerPackage,
            LocalDateTime validFrom
    ) {}

    public record AnnouncementDto(
            Long id,
            String title,
            String message,
            String createdBy,
            LocalDateTime createdAt,
            LocalDateTime validFrom,
            LocalDateTime validTo
    ) {}
}