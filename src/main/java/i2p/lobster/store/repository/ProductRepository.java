package i2p.lobster.store.repository;

import i2p.lobster.store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
