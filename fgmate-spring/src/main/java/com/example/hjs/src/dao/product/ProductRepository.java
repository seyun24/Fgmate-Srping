package com.example.hjs.src.dao.product;

import com.example.hjs.src.domain.product.Product;
import com.example.hjs.src.domain.product.ProductDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<List<Product>>findByRefrigeratorId(Long id);
    @Query("select new com.example.hjs.src.domain.product.ProductDto(P) " +
            "from Product P where P.productId = :id")
    Optional<ProductDto> findByProductIdDtl(Long id);
    Optional<Product> findByProductId(Long id);
}
