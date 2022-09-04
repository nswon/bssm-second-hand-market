package usedmarket.usedmarket.domain.products.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitleContaining(String keyword);
}
