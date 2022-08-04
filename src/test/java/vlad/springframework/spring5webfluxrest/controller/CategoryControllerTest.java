package vlad.springframework.spring5webfluxrest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vlad.springframework.spring5webfluxrest.domain.Category;
import vlad.springframework.spring5webfluxrest.repositories.CategoryRepository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CategoryControllerTest {
    private CategoryRepository repository;
    private CategoryController controller;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        repository = mock(CategoryRepository.class);
        controller = new CategoryController(repository);
        webTestClient = WebTestClient.bindToController(controller).build();

    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(Flux.just(
                Category.builder().description("cat").build(),
                Category.builder().description("dog").build()
        ));
        webTestClient.get().uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        Category cat = Category.builder().description("cat").build();
        when(repository.findById(anyString())).thenReturn(Mono.just(cat));
        webTestClient.get().uri("/api/v1/categories/1")
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(cat);
    }
}