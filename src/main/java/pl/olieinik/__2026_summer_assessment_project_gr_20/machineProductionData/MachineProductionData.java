package pl.olieinik.__2026_summer_assessment_project_gr_20.machineProductionData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;

import java.time.LocalDateTime;

@Entity
@Table(name = "machine_production_data")
public class MachineProductionData {

    @Id
    @Column(name = "machine_id")
    private Long machineId;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;
    @Column(name = "packed_count")
    private Integer packedCount;
    @Column(name = "last_package_time")
    private LocalDateTime lastPackageTime;

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Integer getPackedCount() {
        return packedCount;
    }

    public void setPackedCount(Integer packedCount) {
        this.packedCount = packedCount;
    }

    public LocalDateTime getLastPackageTime() {
        return lastPackageTime;
    }

    public void setLastPackageTime(LocalDateTime lastPackageTime) {
        this.lastPackageTime = lastPackageTime;
    }
}
