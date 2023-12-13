@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listActiveProducts() {
        return ResponseEntity.ok(productService.listActiveProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(required = false) String productName,
                                                        @RequestParam(required = false) BigDecimal minPrice,
                                                        @RequestParam(required = false) BigDecimal maxPrice,
                                                        @RequestParam(required = false) LocalDateTime minPostedDate,
                                                        @RequestParam(required = false) LocalDateTime maxPostedDate) {
        List<Product> products = productService.searchProducts(productName, minPrice, maxPrice, minPostedDate, maxPostedDate);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(productId, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/approval-queue")
    public ResponseEntity<List<ApprovalQueue>> getProductsInApprovalQueue() {
        return ResponseEntity.ok(productService.getProductsInApprovalQueue());
    }

    // Add other endpoints for approval and rejection as needed
}