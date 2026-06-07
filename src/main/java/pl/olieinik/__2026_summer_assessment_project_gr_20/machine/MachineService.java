package pl.olieinik.__2026_summer_assessment_project_gr_20.machine;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MachineService {

    private final MachineRepository repository;

    public MachineService(MachineRepository repository) {
        this.repository = repository;
    }

    public Machine getMachine(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Machine not found"));
    }

    public List<Machine> getAllMachines() {
        return repository.findAll();
    }

    // to remove
    public void changeState(Long id, MachineState newState) {

        Machine machine = getMachine(id);
        if (machine.getState() == MachineState.FAILURE && newState == MachineState.WORKING) {
            throw new IllegalStateException("Cannot go directly from FAILURE to WORKING");
        }

        machine.setState(newState);
        machine.setLastUpdate(LocalDateTime.now());
    }
}