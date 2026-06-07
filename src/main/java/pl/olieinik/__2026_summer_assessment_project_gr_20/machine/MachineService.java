package pl.olieinik.__2026_summer_assessment_project_gr_20.machine;


import org.springframework.stereotype.Service;

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
}