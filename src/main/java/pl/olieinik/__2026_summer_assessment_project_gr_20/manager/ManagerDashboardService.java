package pl.olieinik.__2026_summer_assessment_project_gr_20.manager;

import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.announcement.Announcement;
import pl.olieinik.__2026_summer_assessment_project_gr_20.announcement.AnnouncementService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.MachineService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.MachineState;
import pl.olieinik.__2026_summer_assessment_project_gr_20.loginHistory.UserLoginHistoryRepository;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNorm;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNormService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRole;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserService;

import java.util.List;
import java.util.Objects;

@Service
public class ManagerDashboardService {

    private final UserService userService;
    private final MachineService machineService;
    private final AnnouncementService announcementService;
    private final UserLoginHistoryRepository loginHistoryRepository;

    public ManagerDashboardService(
            UserService userService,
            MachineService machineService,
            AnnouncementService announcementService,
            UserLoginHistoryRepository loginHistoryRepository
    ) {
        this.userService = userService;
        this.machineService = machineService;
        this.announcementService = announcementService;
        this.loginHistoryRepository = loginHistoryRepository;
    }

    public pl.olieinik.__2026_summer_assessment_project_gr_20.manager.ManagerDashboardDto getDashboard(Long managerId) {
        User manager = userService.getUser(managerId);

        if (manager.getRole() != UserRole.MANAGER) {
            throw new IllegalStateException(
                    "Only MANAGER can access manager dashboard"
            );
        }

        List<User> users = userService.getAllUsers();
        List<Machine> machines = machineService.getAllMachines();
        List<Announcement> announcements =
                announcementService.getActiveAnnouncements();

        List<ManagerDashboardDto.UnattendedMachineDto> unattendedMachines =
                machines.stream()
                        .filter(machine -> machine.getState() == MachineState.WORKING)
                        .map(machine -> mapUnattendedMachine(machine, users))
                        .filter(Objects::nonNull)
                        .toList();

        ManagerDashboardDto.SummaryDto summary =
                new ManagerDashboardDto.SummaryDto(
                        users.stream()
                                .filter(user -> user.getRole() == UserRole.OPERATOR)
                                .count(),
                        loginHistoryRepository.countActiveUsersByRole(UserRole.OPERATOR),
                        machines.size(),
                        machines.stream()
                                .filter(machine ->
                                        machine.getState() == MachineState.WORKING)
                                .count(),
                        machines.stream()
                                .filter(machine ->
                                        machine.getState() == MachineState.WAITING)
                                .count(),
                        machines.stream()
                                .filter(machine ->
                                        machine.getState() == MachineState.FAILURE)
                                .count(),
                        announcements.size(),
                        unattendedMachines
                );

        return new ManagerDashboardDto(
                new ManagerDashboardDto.ManagerDto(
                        manager.getId(),
                        manager.getName(),
                        manager.getSurname()
                ),
                summary,
                users.stream()
                        .map(this::mapUser)
                        .toList(),
                machines.stream()
                        .map(this::mapMachine)
                        .toList(),
                announcements.stream()
                        .map(this::mapAnnouncement)
                        .toList()
        );
    }

    private ManagerDashboardDto.UnattendedMachineDto mapUnattendedMachine(
            Machine machine,
            List<User> users
    ) {
        List<User> assignedOperators = users.stream()
                .filter(user -> user.getRole() == UserRole.OPERATOR)
                .filter(user -> user.getMachine() != null)
                .filter(user -> Objects.equals(user.getMachine().getId(), machine.getId()))
                .filter(user -> user.getProduct() != null)
                .toList();

        if (assignedOperators.isEmpty()) {
            return null;
        }

        boolean hasLoggedInOperator = assignedOperators.stream()
                .anyMatch(user -> loginHistoryRepository
                        .existsByUser_IdAndLogoutAtIsNull(user.getId()));

        if (hasLoggedInOperator) {
            return null;
        }

        List<String> productDescriptions = assignedOperators.stream()
                .map(user -> user.getProduct().getCode()
                        + " — " + user.getProduct().getName())
                .distinct()
                .toList();

        return new ManagerDashboardDto.UnattendedMachineDto(
                machine.getId(),
                machine.getName(),
                productDescriptions
        );
    }

//    private ManagerDashboardDto.UserDto mapUser(User user) {
//        Long machineId = null;
//        String machineName = null;
//
//        if (user.getMachine() != null) {
//            machineId = user.getMachine().getId();
//            machineName = user.getMachine().getName();
//        }
//
//
//        Long productId = null;
//        String productCode = null;
//        String productName = null;
//
//        if (user.getProduct() != null) {
//            productId = user.getProduct().getId();
//            productCode = user.getProduct().getCode();
//            productName = user.getProduct().getName();
//        }
//
//        return new ManagerDashboardDto.UserDto(
//                user.getId(),
//                user.getName(),
//                user.getSurname(),
//                user.getRole(),
//                machineId,
//                machineName,
//                productId,
//                productCode,
//                productName
//        );
//    }
private ManagerDashboardDto.UserDto mapUser(User user) {
    Long machineId = null;
    String machineName = null;

    if (user.getMachine() != null) {
        machineId = user.getMachine().getId();
        machineName = user.getMachine().getName();
    }

    Long productId = null;
    String productCode = null;
    String productName = null;

    if (user.getProduct() != null) {
        productId = user.getProduct().getId();
        productCode = user.getProduct().getCode();
        productName = user.getProduct().getName();
    }

    return new ManagerDashboardDto.UserDto(
            user.getId(),
            user.getName(),
            user.getSurname(),
            user.getRole(),
            machineId,
            machineName,
            productId,
            productCode,
            productName
    );
}

    private ManagerDashboardDto.MachineDto mapMachine(Machine machine) {
        return new ManagerDashboardDto.MachineDto(
                machine.getId(),
                machine.getName(),
                machine.getState(),
                machine.getLastUpdate()
        );
    }

    private ManagerDashboardDto.NormDto mapNorm(ProductionNorm norm) {
        return new ManagerDashboardDto.NormDto(
                norm.getId(),
                norm.getTargetPerShift(),
                norm.getExceedTargetPerShift(),
                norm.getMinimumPerShift(),
                norm.getSecondsPerPackage(),
                norm.getValidFrom()
        );
    }

    private ManagerDashboardDto.AnnouncementDto mapAnnouncement(
            Announcement announcement
    ) {
        return new ManagerDashboardDto.AnnouncementDto(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getMessage(),
                announcement.getCreatedBy().getName()
                        + " " + announcement.getCreatedBy().getSurname(),
                announcement.getCreatedAt(),
                announcement.getValidFrom(),
                announcement.getValidTo()
        );
    }
}
