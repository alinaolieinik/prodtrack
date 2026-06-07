package pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineProductionDataRepository
        extends JpaRepository<MachineProductionData, Long> {
}