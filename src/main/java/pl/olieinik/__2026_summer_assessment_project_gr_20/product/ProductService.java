package pl.olieinik.__2026_summer_assessment_project_gr_20.product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produkt nie istnieje"));
    }

    public Product create(Product product) {
        return repository.save(product);
    }
}