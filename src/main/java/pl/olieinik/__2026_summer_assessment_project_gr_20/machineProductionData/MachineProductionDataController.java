package pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/production")
public class MachineProductionDataController {

    private final MachineProductionDataService productionDataService;

    public MachineProductionDataController(
            MachineProductionDataService productionDataService
    ) {
        this.productionDataService = productionDataService;
    }

    @PostMapping("/machines/{machineId}/packages")
    public MachineProductionData registerPackage(
            @PathVariable Long machineId
    ) {
        return productionDataService.registerPackage(machineId);
    }
}