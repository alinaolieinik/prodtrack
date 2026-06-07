package pl.olieinik.__2026_summer_assessment_project_gr_20.norm;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/norms")
public class ProductionNormController {

    private final ProductionNormService service;

    public ProductionNormController(ProductionNormService service) {
        this.service = service;
    }

    @GetMapping("/current")
    public ProductionNorm getCurrent() {
        return service.getCurrentNorm();
    }

    @GetMapping("/history")
    public List<ProductionNorm> getHistory() {
        return service.getHistory();
    }

    @PostMapping
    public ProductionNorm create(@RequestBody ProductionNorm norm) {
        return service.createNewNorm(norm);
    }
}