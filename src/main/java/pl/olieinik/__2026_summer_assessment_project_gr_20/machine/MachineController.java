package pl.olieinik.__2026_summer_assessment_project_gr_20.machine;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
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

    // to remove
    @PatchMapping("/{id}/state")
    public void changeState(@PathVariable Long id,
                            @RequestParam MachineState state) {
        service.changeState(id, state);
    }
}
