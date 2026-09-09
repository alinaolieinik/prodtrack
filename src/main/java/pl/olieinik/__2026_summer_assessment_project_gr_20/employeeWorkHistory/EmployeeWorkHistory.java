package pl.olieinik.__2026_summer_assessment_project_gr_20.employeeWorkHistory;

import jakarta.persistence.*;
import pl.olieinik.__2026_summer_assessment_project_gr_20.machine.Machine;
import pl.olieinik.__2026_summer_assessment_project_gr_20.product.Product;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "employee_work_history")
public class EmployeeWorkHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;
    @ManyToOne(optional = false)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;
    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;
    @Column(name = "packed_count", nullable = false)
    private Integer packedCount;
    @Enumerated(EnumType.STRING)
    @Column(name = "performance_level", nullable = false)
    private WorkPerformanceLevel performanceLevel;

    public Long getId() {
        return id;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public Integer getPackedCount() {
        return packedCount;
    }

    public void setPackedCount(Integer packedCount) {
        this.packedCount = packedCount;
    }

    public WorkPerformanceLevel getPerformanceLevel() {
        return performanceLevel;
    }

    public void setPerformanceLevel(WorkPerformanceLevel performanceLevel) {
        this.performanceLevel = performanceLevel;
    }
}
