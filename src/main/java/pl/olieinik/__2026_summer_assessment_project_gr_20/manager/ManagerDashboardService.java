package pl.olieinik.__2026_summer_assessment_project_gr_20.manager;

import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.announcement.Announcement;
import pl.olieinik.__2026_summer_assessment_project_gr_20.announcement.AnnouncementService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.MachineService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.MachineState;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNorm;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNormService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRole;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserService;

import java.util.List;

@Service
public class ManagerDashboardService {

    private final UserService userService;
    private final MachineService machineService;
    private final AnnouncementService announcementService;

    public ManagerDashboardService(
            UserService userService,
            MachineService machineService,
            AnnouncementService announcementService
    ) {
        this.userService = userService;
        this.machineService = machineService;
        this.announcementService = announcementService;
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

        ManagerDashboardDto.SummaryDto summary =
                new ManagerDashboardDto.SummaryDto(
                        users.stream()
                                .filter(user -> user.getRole() == UserRole.OPERATOR)
                                .count(),
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
                        announcements.size()
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
                announcement.getCreatedBy(),
                announcement.getCreatedAt(),
                announcement.getValidFrom(),
                announcement.getValidTo()
        );
    }
}