package pl.olieinik.__2026_summer_assessment_project_gr_20.employeeWorkHistory;

import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData.MachineProductionData;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData.MachineProductionDataService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNorm;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNormService;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;

import java.time.LocalDateTime;

@Service
public class EmployeeWorkHistoryService {

    private final EmployeeWorkHistoryRepository repository;
    private final MachineProductionDataService productionDataService;
    private final ProductionNormService normService;

    public EmployeeWorkHistoryService(
            EmployeeWorkHistoryRepository repository,
            MachineProductionDataService productionDataService,
            ProductionNormService normService
    ) {
        this.repository = repository;
        this.productionDataService = productionDataService;
        this.normService = normService;
    }

    public void saveCompletedWork(User operator, LocalDateTime startedAt) {
        if (operator.getMachine() == null || operator.getProduct() == null) {
            throw new IllegalStateException(
                    "Operator musi mieć przypisaną maszynę i produkt, aby zakończyć pracę."
            );
        }

        MachineProductionData productionData = productionDataService
                .getByMachineId(operator.getMachine().getId());

        int packedCount = productionData.getPackedCount() == null
                ? 0
                : productionData.getPackedCount();

        ProductionNorm norm = normService.findCurrentNorm(
                operator.getProduct().getId()
        ).orElse(null);

        EmployeeWorkHistory history = new EmployeeWorkHistory();
        history.setEmployee(operator);
        history.setMachine(operator.getMachine());
        history.setProduct(operator.getProduct());
        history.setStartedAt(startedAt);
        history.setEndedAt(LocalDateTime.now());
        history.setPackedCount(packedCount);
        history.setPerformanceLevel(calculatePerformanceLevel(packedCount, norm));

        repository.save(history);
        productionDataService.resetPackedCount(operator.getMachine().getId());
    }

    private WorkPerformanceLevel calculatePerformanceLevel(
            int packedCount,
            ProductionNorm norm
    ) {
        if (norm == null) {
            return WorkPerformanceLevel.NO_NORM;
        }

        if (packedCount >= norm.getExceedTargetPerShift()) {
            return WorkPerformanceLevel.EXCELLENT;
        }

        if (packedCount >= norm.getTargetPerShift()) {
            return WorkPerformanceLevel.TARGET;
        }

        if (packedCount >= norm.getMinimumPerShift()) {
            return WorkPerformanceLevel.MINIMUM;
        }

        return WorkPerformanceLevel.BELOW_MINIMUM;
    }
}
