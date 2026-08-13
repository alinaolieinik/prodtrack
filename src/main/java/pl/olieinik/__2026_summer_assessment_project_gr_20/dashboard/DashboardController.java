package pl.olieinik.__2026_summer_assessment_project_gr_20.dashboard;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/operator/{operatorId}")
    public OperatorDashboardDto getDashboard(
            @PathVariable Long operatorId
    ) {
        return service.getDashboard(operatorId);
    }
}