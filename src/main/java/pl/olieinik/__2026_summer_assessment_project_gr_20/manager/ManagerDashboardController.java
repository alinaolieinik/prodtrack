package pl.olieinik.__2026_summer_assessment_project_gr_20.manager;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/manager/dashboard")
public class ManagerDashboardController {

    private final ManagerDashboardService service;

    public ManagerDashboardController(ManagerDashboardService service) {
        this.service = service;
    }

    @GetMapping("/{managerId}")
    public ManagerDashboardDto getDashboard(
            @PathVariable Long managerId
    ) {
        return service.getDashboard(managerId);
    }
}