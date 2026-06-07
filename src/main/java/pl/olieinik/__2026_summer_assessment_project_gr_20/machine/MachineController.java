package pl.olieinik.__2026_summer_assessment_project_gr_20.machine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService service;

    public MachineController(MachineService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Machine get(@PathVariable Long id) {
        return service.getMachine(id);
    }

    @GetMapping
    public List<Machine> getAll() {
        return service.getAllMachines();
    }
}
