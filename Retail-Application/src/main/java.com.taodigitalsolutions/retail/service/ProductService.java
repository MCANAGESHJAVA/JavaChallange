@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApprovalQueueRepository approvalQueueRepository;

    public List<Product> listActiveProducts() {
        return productRepository.findByStatusOrderByPostedDateDesc(ProductStatus.ACTIVE);
    }

    public List<Product> searchProducts(String productName, BigDecimal minPrice, BigDecimal maxPrice, LocalDateTime minPostedDate, LocalDateTime maxPostedDate) {
        // Implement search logic based on the provided criteria
        // ...

        // For simplicity, returning all active products here
        return listActiveProducts();
    }

    public Product createProduct(Product product) {
        // Implement logic to check price and add to approval queue if needed
        // ...

        return productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) {
        // Implement logic to check price and add to approval queue if needed
        // ...

        // Retrieve the existing product
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update properties
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStatus(product.getStatus());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long productId) {
        // Implement logic to add to approval queue if needed
        // ...

        productRepository.deleteById(productId);
    }

    public List<ApprovalQueue> getProductsInApprovalQueue() {
        return approvalQueueRepository.findAllByOrderByRequestDateAsc();
    }

    // Add methods for approval and rejection as needed
}