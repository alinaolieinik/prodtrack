package pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MachineProductionDataService {

    private final MachineProductionDataRepository repository;

    public MachineProductionDataService(
            MachineProductionDataRepository repository) {
        this.repository = repository;
    }

    public MachineProductionData getByMachineId(Long machineId) {
        return repository.findById(machineId)
                .orElseThrow(() ->
                        new RuntimeException("Production data not found"));
    }

    @Transactional
    public MachineProductionData registerPackage(Long machineId) {
        MachineProductionData data = getByMachineId(machineId);

        data.setPackedCount(data.getPackedCount() + 1);
        data.setLastPackageTime(LocalDateTime.now());

        return repository.save(data);
    }
}