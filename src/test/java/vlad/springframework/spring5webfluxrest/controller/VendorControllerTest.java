package vlad.springframework.spring5webfluxrest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vlad.springframework.spring5webfluxrest.domain.Vendor;
import vlad.springframework.spring5webfluxrest.repositories.VendorRepository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VendorControllerTest {

    private VendorRepository repository;
    private VendorController controller;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        repository = mock(VendorRepository.class);
        controller = new VendorController(repository);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void getAll() {
        when(repository.findAll()).thenReturn(Flux.just(
                Vendor.builder().firstName("Vlad").lastName("Nitu").build(),
                Vendor.builder().firstName("Mara").lastName("Popescu").build()
        ));
        webTestClient.get().uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        Vendor vendor = Vendor.builder().firstName("Vlad").lastName("Nitu").build();
        when(repository.findById(anyString()))
                .thenReturn(Mono.just(vendor));
        webTestClient.get().uri("/api/v1/vendors/stringID")
                .exchange()
                .expectBody(Vendor.class)
                .isEqualTo(vendor);
    }

    @Test
    void create() {
        when(repository.saveAll(any(Publisher.class)))
                .thenReturn(Flux.just(Vendor.builder().firstName("Vlad").build()));
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Mara").build());
        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void update() {
        when(repository.save(any(Vendor.class)))
                .thenReturn(Mono.just(Vendor.builder().firstName("Vlad").build()));
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Mara").build());
        webTestClient.put()
                .uri("/api/v1/vendors/stringID")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .equals(vendorMono.block());

    }

    @Test
    void patchWithChanges() {
        when(repository.save(any(Vendor.class)))
                .thenReturn(Mono.just(Vendor.builder().firstName("Vlad").build()));
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Mara").build());
        webTestClient.patch()
                .uri("/api/v1/vendors/stringID")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .equals(vendorMono.block());
        ArgumentCaptor<Vendor> captor = ArgumentCaptor.forClass(Vendor.class);
        verify(repository)
                .save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo("stringID");
        assertThat(captor.getValue().getFirstName()).isEqualTo("Mara");
    }

    @Test
    void patchNoChanges() {
        Mono<Vendor> vendorMono = Mono.just(Vendor.builder().firstName("Mara").build());
        when(repository.save(any(Vendor.class)))
                .thenReturn(vendorMono); // Patch same Vendor
        webTestClient.patch()
                .uri("/api/v1/vendors/stringID")
                .body(vendorMono, Vendor.class)
                .exchange()
                .expectStatus().isOk()
                .equals(vendorMono.block());
        ArgumentCaptor<Vendor> captor = ArgumentCaptor.forClass(Vendor.class);
        verify(repository)
                .save(captor.capture());
        assertThat(captor.getValue().getFirstName()).isEqualTo("Mara");
    }
}