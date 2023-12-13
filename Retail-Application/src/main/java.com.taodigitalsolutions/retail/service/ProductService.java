package com.taodigitalsolutions.retail.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApprovalQueueRepository approvalQueueRepository;

    /**
     * Retrieve a list of active products, sorted by the latest first.
     *
     * @return List of active products
     */
    public List<Product> listActiveProducts() {
        return productRepository.findByStatusOrderByPostedDateDesc(ProductStatus.ACTIVE);
    }

    /**
     * Search for products based on the given criteria.
     *
     * @param productName    Product name (optional)
     * @param minPrice       Minimum price (optional)
     * @param maxPrice       Maximum price (optional)
     * @param minPostedDate  Minimum posted date (optional)
     * @param maxPostedDate  Maximum posted date (optional)
     * @return List of products matching the search criteria
     */
    public List<Product> searchProducts(String productName, BigDecimal minPrice, BigDecimal maxPrice, LocalDateTime minPostedDate, LocalDateTime maxPostedDate) {
        // Your implementation for searchProducts method
        // ...
        return null;
    }

    /**
     * Create a new product.
     *
     * @param product Product to be created
     * @return Created product
     */
    public Product createProduct(Product product) {
        validateProductForCreation(product);


        return productRepository.save(product);
    }

    /**
     * Validate a product for creation and add it to the approval queue if necessary.
     *
     * @param product Product to be validated
     */
    private void validateProductForCreation(Product product) {
        if (product.getPrice().compareTo(new BigDecimal("5000.00")) > 0) {

            ApprovalQueue approvalQueue = new ApprovalQueue();
            approvalQueue.setProduct(product);
            approvalQueue.setRequestDate(LocalDateTime.now());
            approvalQueueRepository.save(approvalQueue);


            product.setStatus(ProductStatus.IN_APPROVAL_QUEUE);
        }
    }

    /**
     * Update an existing product.
     *
     * @param productId ID of the product to be updated
     * @param product   Updated product data
     * @return Updated product
     */
    public Product updateProduct(Long productId, Product product) {
        validateProductForUpdate(product);


        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));


        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStatus(product.getStatus());

        return productRepository.save(existingProduct);
    }

    /**
     * Validate a product for update and add it to the approval queue if necessary.
     *
     * @param product Updated product data
     */
    private void validateProductForUpdate(Product product) {

        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        BigDecimal previousPrice = existingProduct.getPrice();
        BigDecimal newPrice = product.getPrice();

        if (newPrice.compareTo(previousPrice.multiply(new BigDecimal("1.5"))) > 0) {

            ApprovalQueue approvalQueue = new ApprovalQueue();
            approvalQueue.setProduct(product);
            approvalQueue.setRequestDate(LocalDateTime.now());
            approvalQueueRepository.save(approvalQueue);


            product.setStatus(ProductStatus.IN_APPROVAL_QUEUE);
        }
    }

    /**
     * Delete a product by ID.
     *
     * @param productId ID of the product to be deleted
     */
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));


        productRepository.deleteById(productId);
    }

    /**
     * Get all products in the approval queue, sorted by request date (earliest first).
     *
     * @return List of products in the approval queue
     */
    public List<ApprovalQueue> getProductsInApprovalQueue() {
        return approvalQueueRepository.findAllByOrderByRequestDateAsc();
    }

    /**
     * Approve a product from the approval queue.
     *
     * @param approvalId ID of the approval record
     */
    public void approveProduct(Long approvalId) {
        // Your logic for approving a product
        // ...

        // Delete the approval record
        approvalQueueRepository.deleteById(approvalId);
    }

    /**
     * Reject a product from the approval queue.
     *
     * @param approvalId ID of the approval record
     */
    public void rejectProduct(Long approvalId) {

        approvalQueueRepository.deleteById(approvalId);
    }
}
