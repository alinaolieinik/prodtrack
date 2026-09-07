package pl.olieinik.__2026_summer_assessment_project_gr_20.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.olieinik.__2026_summer_assessment_project_gr_20.employeeWorkHistory.EmployeeWorkHistoryRepository;
import pl.olieinik.__2026_summer_assessment_project_gr_20.norm.ProductionNormRepository;
import pl.olieinik.__2026_summer_assessment_project_gr_20.user.UserRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final UserRepository userRepository;
    private final ProductionNormRepository normRepository;
    private final EmployeeWorkHistoryRepository workHistoryRepository;

    public ProductService(
            ProductRepository repository,
            UserRepository userRepository,
            ProductionNormRepository normRepository,
            EmployeeWorkHistoryRepository workHistoryRepository
    ) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.normRepository = normRepository;
        this.workHistoryRepository = workHistoryRepository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produkt nie istnieje"));
    }

    public Product create(Product product) {
        validate(product, null);
        product.setName(product.getName().trim());
        product.setCode(product.getCode().trim());
        return repository.save(product);
    }

    public Product update(Long id, Product updatedProduct) {
        Product existingProduct = getById(id);
        validate(updatedProduct, id);

        existingProduct.setName(updatedProduct.getName().trim());
        existingProduct.setCode(updatedProduct.getCode().trim());

        return repository.save(existingProduct);
    }

    public void delete(Long id) {
        Product product = getById(id);

        if (userRepository.existsByProduct_Id(id)
                || normRepository.existsByProduct_Id(id)
                || workHistoryRepository.existsByProduct_Id(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nie można usunąć produktu, ponieważ jest przypisany do użytkowników lub norm."
            );
        }

        repository.delete(product);
    }

    private void validate(Product product, Long productId) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nazwa produktu jest wymagana."
            );
        }

        if (product.getCode() == null || product.getCode().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Kod produktu jest wymagany."
            );
        }

        String code = product.getCode().trim();
        boolean codeAlreadyUsed = productId == null
                ? repository.existsByCodeIgnoreCase(code)
                : repository.existsByCodeIgnoreCaseAndIdNot(code, productId);

        if (codeAlreadyUsed) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Produkt o podanym kodzie już istnieje."
            );
        }
    }
}
