public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByPostedDateDesc(ProductStatus status);

    // Add other custom queries as needed
}