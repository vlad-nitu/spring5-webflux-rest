package vlad.springframework.spring5webfluxrest.controller;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vlad.springframework.spring5webfluxrest.domain.Vendor;
import vlad.springframework.spring5webfluxrest.repositories.VendorRepository;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {
    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    Flux<Vendor> getAll() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor patchedVendor = Vendor.builder()
                .id(id)
                .firstName(vendor.getFirstName())
                .lastName(vendor.getLastName())
                .build();
        return vendorRepository.save(patchedVendor);
    }
}
