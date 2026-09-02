package pl.olieinik.__2026_summer_assessment_project_gr_20.norm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductionNormRepository extends JpaRepository<ProductionNorm, Long> {

    Optional<ProductionNorm> findByProductIdAndValidToIsNull(Long productId);

    List<ProductionNorm> findAllByProductIdOrderByValidFromDesc(Long productId);
}