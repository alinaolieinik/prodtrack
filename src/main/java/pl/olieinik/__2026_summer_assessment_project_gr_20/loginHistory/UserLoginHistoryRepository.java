package pl.olieinik.__2026_summer_assessment_project_gr_20.loginHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {

    Optional<UserLoginHistory> findFirstByUser_IdAndLogoutAtIsNullOrderByLoginAtDesc(Long userId);

    boolean existsByUser_IdAndLogoutAtIsNull(Long userId);

    @Query("""
            select count(distinct history.user.id)
            from UserLoginHistory history
            where history.user.role = :role
              and history.logoutAt is null
            """)
    long countActiveUsersByRole(@Param("role") pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRole role);
}
