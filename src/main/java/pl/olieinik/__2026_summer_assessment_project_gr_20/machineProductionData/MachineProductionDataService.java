package pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData;

import org.springframework.stereotype.Service;

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
}