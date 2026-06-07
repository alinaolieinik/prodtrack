package pl.olieinik.__2026_summer_assessment_project_gr_20.norm;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductionNormService {

    private final ProductionNormRepository repository;

    public ProductionNormService(ProductionNormRepository repository) {
        this.repository = repository;
    }

    public ProductionNorm getCurrentNorm() {
        return repository.findByValidToIsNull()
                .orElseThrow(() -> new RuntimeException("No active norm"));
    }

    public List<ProductionNorm> getHistory() {
        return repository.findAllByOrderByValidFromDesc();
    }

    public ProductionNorm createNewNorm(ProductionNorm newNorm) {

        repository.findByValidToIsNull().ifPresent(old -> {
            old.setValidTo(LocalDateTime.now());
            repository.save(old);
        });

        newNorm.setValidFrom(LocalDateTime.now());
        newNorm.setValidTo(null);

        return repository.save(newNorm);
    }
}