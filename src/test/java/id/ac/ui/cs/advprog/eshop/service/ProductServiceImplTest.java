package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");

        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        assertEquals(product, result);
        verify(productRepository).create(product);
    }

    @Test
    void testFindAllProducts() {
        Product p1 = new Product();
        p1.setProductId("1");

        Product p2 = new Product();
        p2.setProductId("2");

        List<Product> productList = Arrays.asList(p1, p2);
        Iterator<Product> iterator = productList.iterator();

        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("1");

        when(productRepository.findById("1")).thenReturn(product);

        Product result = productService.findById("1");

        assertEquals(product, result);
        verify(productRepository).findById("1");
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setProductId("1");

        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        assertEquals(product, result);
        verify(productRepository).update(product);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.delete("1")).thenReturn(true);

        boolean result = productService.delete("1");

        assertTrue(result);
        verify(productRepository).delete("1");
    }
}