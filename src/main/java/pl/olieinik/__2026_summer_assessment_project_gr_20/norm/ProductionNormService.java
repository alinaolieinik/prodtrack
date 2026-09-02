package pl.olieinik.__2026_summer_assessment_project_gr_20.norm;

import org.springframework.stereotype.Service;
import pl.olieinik.__2026_summer_assessment_project_gr_20.product.ProductService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductionNormService {

    private final ProductionNormRepository repository;
    private final ProductService productService;

    public ProductionNormService(ProductionNormRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }
//    public ProductionNormService(ProductionNormRepository repository) {
//        this.repository = repository;
//        this.productService = productService;
//    }


//    public ProductionNorm getCurrentNorm() {
//        return repository.findByValidToIsNull()
//                .orElseThrow(() -> new RuntimeException("No active norm"));
//    }
//
//    public List<ProductionNorm> getHistory() {
//        return repository.findAllByOrderByValidFromDesc();
//    }
//
//    public ProductionNorm createNewNorm(ProductionNorm newNorm) {
//
//        repository.findByValidToIsNull().ifPresent(old -> {
//            old.setValidTo(LocalDateTime.now());
//            repository.save(old);
//        });
//
//        newNorm.setValidFrom(LocalDateTime.now());
//        newNorm.setValidTo(null);
//
//        return repository.save(newNorm);
//    }


    public ProductionNorm getCurrentNorm(Long productId) {
        return repository.findByProductIdAndValidToIsNull(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Brak aktywnej normy dla wybranego produktu"
                ));
    }

    public List<ProductionNorm> getHistory(Long productId) {
        return repository.findAllByProductIdOrderByValidFromDesc(productId);
    }

    public ProductionNorm createNewNorm(
            Long productId,
            ProductionNorm newNorm
    ) {
        repository.findByProductIdAndValidToIsNull(productId)
                .ifPresent(oldNorm -> {
                    oldNorm.setValidTo(LocalDateTime.now());
                    repository.save(oldNorm);
                });

        newNorm.setProduct(productService.getById(productId));
        newNorm.setValidFrom(LocalDateTime.now());
        newNorm.setValidTo(null);

        return repository.save(newNorm);
    }
}