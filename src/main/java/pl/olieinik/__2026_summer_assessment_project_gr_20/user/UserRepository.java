package pl.olieinik.__2026_summer_assessment_project_gr_20.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNameIgnoreCase(String name);
//    boolean existsByMachine_Id(Long machineId);
//
//    boolean existsByMachine_IdAndIdNot(Long machineId, Long userId);
}