package vlad.springframework.spring5webfluxrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vlad.springframework.spring5webfluxrest.domain.Category;
import vlad.springframework.spring5webfluxrest.repositories.CategoryRepository;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @GetMapping()
    Flux<Category> getAll() {
        return categoryRepository.findAll();
    }
    @GetMapping("{id}")
    Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }
}
