package vlad.springframework.spring5webfluxrest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vlad.springframework.spring5webfluxrest.domain.Vendor;
import vlad.springframework.spring5webfluxrest.repositories.VendorRepository;

@Component
public class Bootstrap implements CommandLineRunner {
    private VendorRepository vendorRepository;

    public Bootstrap(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadVendors();

    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setFirstName("Vlad");
        vendor1.setLastName("Nitu");
        vendorRepository.save(vendor1).block();

        Vendor vendor2 = new Vendor();
        vendor2.setFirstName("Rares");
        vendor2.setLastName("Nitu");
        vendorRepository.save(vendor2).block();

        System.out.println("Load Vendors = " + vendorRepository.count().block());

    }
}
