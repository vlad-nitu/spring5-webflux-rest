package vlad.springframework.spring5webfluxrest.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vlad.springframework.spring5webfluxrest.domain.Category;
import vlad.springframework.spring5webfluxrest.domain.Vendor;
import vlad.springframework.spring5webfluxrest.repositories.CategoryRepository;
import vlad.springframework.spring5webfluxrest.repositories.VendorRepository;

@Component
public class Bootstrap implements CommandLineRunner {
    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0L) {
            System.out.println("### Loading Data on Bootstrap");
            loadCategories();
            loadVendors();
        }

    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().description("Fruits").build()).block();
        categoryRepository.save(Category.builder().description("Nuts").build()).block();
        categoryRepository.save(Category.builder().description("Breads").build()).block();
        categoryRepository.save(Category.builder().description("Meats").build()).block();
        categoryRepository.save(Category.builder().description("Eggs").build()).block();
        System.out.println("Load Categories = " + categoryRepository.count().block());
    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder().firstName("Vlad").lastName("Nitu").build()).block();
        vendorRepository.save((Vendor.builder().firstName("Rares").lastName("Nitu").build())).block();
        vendorRepository.save((Vendor.builder().firstName("Mara").lastName("Popescu").build())).block();
        System.out.println("Load Vendors = " + vendorRepository.count().block());

    }
}
