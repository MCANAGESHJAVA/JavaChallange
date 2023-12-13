package com.taodigitalsolutions.retail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByStatusOrderByPostedDateDesc(ProductStatus status);

    @Query("SELECT p FROM Product p WHERE " +
            "(:productName IS NULL OR p.name LIKE %:productName%) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.price <= :maxPrice) " +
            "AND (:minPostedDate IS NULL OR p.postedDate >= :minPostedDate) " +
            "AND (:maxPostedDate IS NULL OR p.postedDate <= :maxPostedDate)")
    List<Product> searchProducts(
            @Param("productName") String productName,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minPostedDate") LocalDateTime minPostedDate,
            @Param("maxPostedDate") LocalDateTime maxPostedDate);

    @Query("SELECT p FROM Product p WHERE p.price > :priceThreshold")
    List<Product> findHighPricedProducts(@Param("priceThreshold") BigDecimal priceThreshold);
}