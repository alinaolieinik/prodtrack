package pl.olieinik.__2026_summer_assessment_project_gr_20.norm;
import jakarta.persistence.*;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;

import java.time.LocalDateTime;


@Entity
public class ProductionNorm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer targetPerShift;

    private Integer exceedTargetPerShift;

    private Integer minimumPerShift;

    private Double secondsPerPackage;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    public Integer getExceedTargetPerShift() {
        return exceedTargetPerShift;
    }

    public void setExceedTargetPerShift(Integer exceedTargetPerShift) {
        this.exceedTargetPerShift = exceedTargetPerShift;
    }

    public Integer getMinimumPerShift() {
        return minimumPerShift;
    }

    public void setMinimumPerShift(Integer minimumPerShift) {
        this.minimumPerShift = minimumPerShift;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public Double getSecondsPerPackage() {
        return secondsPerPackage;
    }

    public void setSecondsPerPackage(Double secondsPerPackage) {
        this.secondsPerPackage = secondsPerPackage;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public Integer getTargetPerShift() {
        return targetPerShift;
    }

    public void setTargetPerShift(Integer targetPerShift) {
        this.targetPerShift = targetPerShift;
    }
}