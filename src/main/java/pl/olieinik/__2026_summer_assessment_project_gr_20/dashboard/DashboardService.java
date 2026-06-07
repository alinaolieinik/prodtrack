package pl.olieinik.__2026_summer_assessment_project_gr_20.dashboard;

import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.announcement.AnnouncementService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData.MachineProductionData;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData.MachineProductionDataService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNorm;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNormService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserService;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class DashboardService {

    private final UserService userService;
    private final ProductionNormService normService;
    private final AnnouncementService announcementService;
    private final MachineProductionDataService productionService;

    public DashboardService(
            UserService userService,
            ProductionNormService normService,
            AnnouncementService announcementService,
            MachineProductionDataService productionService
    ) {
        this.userService = userService;
        this.normService = normService;
        this.announcementService = announcementService;
        this.productionService = productionService;
    }

    public OperatorDashboardDto getDashboard(Long operatorId) {

        User operator = userService.getUser(operatorId);

        MachineProductionData productionData =
                productionService.getByMachineId(
                        operator.getMachine().getId()
                );

        ProductionNorm norm =
                normService.getCurrentNorm();

        long elapsedSeconds =
                Duration.between(
                        productionData.getLastPackageTime(),
                        LocalDateTime.now()
                ).getSeconds();

        long remainingSeconds =
                Math.max(
                        0,
                        norm.getSecondsPerPackage().longValue()
                                - elapsedSeconds
                );

        OperatorDashboardDto dto =
                new OperatorDashboardDto();

        dto.setOperatorId(operator.getId());
        dto.setName(operator.getName());
        dto.setSurname(operator.getSurname());

        dto.setMachineName(
                operator.getMachine().getName()
        );

        dto.setPackedCount(
                productionData.getPackedCount()
        );

        dto.setTargetPerShift(
                norm.getTargetPerShift()
        );

        dto.setExceedTargetPerShift(
                norm.getExceedTargetPerShift()
        );

        dto.setMinimumPerShift(
                norm.getMinimumPerShift()
        );

        dto.setSecondsPerPackage(
                norm.getSecondsPerPackage()
        );

        dto.setRemainingSeconds(
                remainingSeconds
        );

        dto.setAnnouncements(
                announcementService.getActiveAnnouncements()
        );

        return dto;
    }
}