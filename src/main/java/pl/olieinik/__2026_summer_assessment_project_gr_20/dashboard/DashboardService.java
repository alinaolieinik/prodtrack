package pl.olieinik.__2026_summer_assessment_project_gr_20.dashboard;

import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.announcement.Announcement;
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
        if (operator.getMachine() == null) {
            throw new IllegalStateException(
                    "Operator nie ma przypisanej maszyny."
            );
        }

        if (operator.getProduct() == null) {
            throw new IllegalStateException(
                    "Operator nie ma przypisanego produktu."
            );
        }
        MachineProductionData productionData =
                productionService.getByMachineId(
                        operator.getMachine().getId()
                );

//        if (operator.getProduct() == null) {
//            throw new RuntimeException("Operator nie ma przypisanego produktu");
//        }

        ProductionNorm norm = normService.getCurrentNorm(
                operator.getProduct().getId()
        );

        if (norm == null) {
            throw new RuntimeException("Produkt nie ma przypisanych norm.");
        }

//        long elapsedSeconds =
//                Duration.between(
//                        productionData.getLastPackageTime(),
//                        LocalDateTime.now()
//                ).getSeconds();

//        long remainingSeconds =
//                Math.max(
//                        0,
//                        norm.getSecondsPerPackage().longValue()
//                                - elapsedSeconds
//                );

        long remainingSeconds;

        if (productionData.getLastPackageTime() == null) {
            remainingSeconds = Math.round(norm.getSecondsPerPackage());
        } else {
            long elapsedSeconds = Duration.between(
                    productionData.getLastPackageTime(),
                    LocalDateTime.now()
            ).getSeconds();

            remainingSeconds = Math.max(
                    0,
                    Math.round(norm.getSecondsPerPackage()) - elapsedSeconds
            );
        }

        OperatorDashboardDto dto =
                new OperatorDashboardDto();

        dto.setOperatorId(operator.getId());
        dto.setName(operator.getName());
        dto.setSurname(operator.getSurname());

        dto.setMachineName(
                operator.getMachine().getName()
        );

        dto.setProductName(
                operator.getProduct().getName()
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
                announcementService.getActiveAnnouncements().stream()
                        .map(this::mapOperatorAnnouncement)
                        .toList()
        );

        return dto;
    }

    private OperatorAnnouncementDto mapOperatorAnnouncement(Announcement announcement) {
        return new OperatorAnnouncementDto(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getMessage(),
                announcement.getCreatedBy().getName()
                        + " " + announcement.getCreatedBy().getSurname(),
                announcement.getValidTo()
        );
    }
}
