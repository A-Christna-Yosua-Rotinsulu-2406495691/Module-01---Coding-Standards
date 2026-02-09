package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setup() {
        // Setup product untuk test
        product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testUpdateProduct_positiveScenario() {
        // Create product
        Product created = productRepository.create(product);
        String productId = created.getProductId();

        // Update the product
        Product updatedProduct = new Product();
        updatedProduct.setProductId(productId);
        updatedProduct.setProductName("Sampo Cap Bango (Updated)");
        updatedProduct.setProductQuantity(150);

        Product result = productRepository.update(updatedProduct);

        // Verify update successful
        assertNotNull(result);
        assertEquals("Sampo Cap Bango (Updated)", result.getProductName());
        assertEquals(150, result.getProductQuantity());

        // Verify in findAll
        Product found = productRepository.findById(productId);
        assertNotNull(found);
        assertEquals("Sampo Cap Bango (Updated)", found.getProductName());
    }

    @Test
    void testUpdateProduct_negativeScenario_notFound() {
        // Try to update non-existent product
        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Ghost Product");
        nonExistentProduct.setProductQuantity(999);

        // Act
        Product result = productRepository.update(nonExistentProduct);

        // Assert: Should return null
        assertNull(result);
    }

    @Test
    void testUpdateProduct_withNullValues() {
        // Create product
        Product created = productRepository.create(product);
        String productId = created.getProductId();

        // Try to update with null product
        Product result = productRepository.update(null);

        // Should return null
        assertNull(result, "Update with null product should return null");

        // Original product should still exist and unchanged
        Product original = productRepository.findById(productId);
        assertNotNull(original, "Original product should still exist");
        assertEquals("Sampo Cap Bambang", original.getProductName());
        assertEquals(100, original.getProductQuantity());
    }

    @Test
    void testDeleteProduct_positiveScenario() {
        // Create product
        Product created = productRepository.create(product);
        String productId = created.getProductId();

        // Verify exists before delete
        assertNotNull(productRepository.findById(productId));

        // Delete the product
        boolean deleteResult = productRepository.delete(productId);

        // Verify delete successful
        assertTrue(deleteResult);
        assertNull(productRepository.findById(productId));

        // Verify iterator is empty
        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteProduct_negativeScenario_notFound() {
        // Try to delete non-existent product
        String nonExistentId = "non-existent-id";

        // Act
        boolean deleteResult = productRepository.delete(nonExistentId);

        // Should return false
        assertFalse(deleteResult);
    }

    @Test
    void testDeleteProduct_negativeScenario_nullId() {
        // Try to delete with null ID
        String nullId = null;

        // Act
        boolean deleteResult = productRepository.delete(nullId);

        // Should return false
        assertFalse(deleteResult);
    }

    @Test
    void testDeleteProduct_andCreateNewWithSameId() {
        // Create and delete product
        Product created = productRepository.create(product);
        String productId = created.getProductId();
        productRepository.delete(productId);

        // Create new product with same ID (should be allowed)
        Product newProduct = new Product();
        newProduct.setProductId(productId); // Explicitly set same ID
        newProduct.setProductName("New Product Same ID");
        newProduct.setProductQuantity(200);

        Product result = productRepository.create(newProduct);

        // New product should be created
        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals("New Product Same ID", result.getProductName());

        // Verify only one product exists
        int count = 0;
        Iterator<Product> iterator = productRepository.findAll();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        assertEquals(1, count);
    }
}
