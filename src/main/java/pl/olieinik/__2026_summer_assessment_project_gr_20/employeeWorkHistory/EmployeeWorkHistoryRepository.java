package pl.olieinik.__2026_summer_assessment_project_gr_20.employeeWorkHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeWorkHistoryRepository extends JpaRepository<EmployeeWorkHistory, Long> {

    boolean existsByProduct_Id(Long productId);
}
