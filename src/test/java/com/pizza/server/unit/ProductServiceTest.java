package com.pizza.server.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.pizza.server.errors.ElementNotFoundException;
import com.pizza.server.models.Product;
import com.pizza.server.repositories.ProductRepository;
import com.pizza.server.services.ProductService;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProductSuccess() {
        Product product = new Product("Pizza Margherita", "Pizza with tomato sauce, mozzarella and basil",
                "https://www.pizzahut.com.au/images/menu/pizza/margherita.jpg", "10.00,12.00,14.00");
        when(productRepository.save(product)).thenReturn(product);
        Product result = productService.createProduct(product);
        assertEquals("Pizza Margherita", result.getTitle());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testReadProductsSuccess() {
    when(productRepository.findAll()).thenReturn(Arrays.asList(
    new Product("Pizza Margherita", "Pizza with tomato sauce, mozzarella and basil", "https://www.pizzahut.com.au/images/menu/pizza/margherita.jpg", "10.00,12.00,14.00"),
    new Product("Pizza Margherita crispy", "Pizza with tomato sauce, mozzarella, basil and crispy base.", "https://www.pizzahut.com.au/images/menu/pizza/margherita.jpg", "10.00,12.00,14.00")
    ));

    List<Product> result = productService.readProducts();

    assertEquals("Pizza Margherita", result.get(0).getTitle());
    assertEquals("Pizza with tomato sauce, mozzarella, basil and crispy base.",
    result.get(1).getDescription());
    }

    @Test 
    public void testReadProductSuccess(){
    when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(new Product("Pizza Margherita", "Pizza with tomato sauce, mozzarella and basil", "https://www.pizzahut.com.au/images/menu/pizza/margherita.jpg", "10.00,12.00,14.00")));

    Product result = productService.readProduct(1L);

    assertEquals("Pizza Margherita", result.getTitle());
    assertEquals("Pizza with tomato sauce, mozzarella and basil",
    result.getDescription());
    }

    @Test (expected = ElementNotFoundException.class)
    public void testReadProductNotFound(){
    when(productRepository.findById(2L)).thenThrow(new ElementNotFoundException(2L));
    productService.readProduct(2L);
    }

    @Test
    public void testUpdateProductSuccess() {
        Product product = new Product("Pizza Margherita", "Pizza with tomato sauce, mozzarella and basil",
                "https://www.pizzahut.com.au/images/menu/pizza/margherita.jpg", "10.00,12.00,14.00");
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.updateProduct(1L, product);
        assertEquals("Pizza Margherita", result.getTitle());
        verify(productRepository, times(1)).save(product);
    }

    @Test(expected = ElementNotFoundException.class)
    public void testUpdateProductNotFound() {
        Product product = new Product("Pizza Margherita", "Pizza with tomato sauce, mozzarella and basil",
                "https://www.pizzahut.com.au/images/menu/pizza/margherita.jpg", "10.00,12.00,14.00");
        when(productRepository.findById(2L)).thenThrow(new ElementNotFoundException(2L));
        productService.updateProduct(2L, product);
        verify(productRepository, times(0)).save(product);
    }

    @Test
    public void testDeleteProductSuccess() {
        Product product = new Product("Pizza Margherita", "Pizza with tomato sauce, mozzarella and basil",
                "https://www.pizzahut.com.au/images/menu/pizza/margherita.jpg", "10.00,12.00,14.00");
        when(productRepository.findById(1L)).thenReturn(java.util.Optional.of(product));
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test(expected = ElementNotFoundException.class)
    public void testDeleteProductNotFound() {
        when(productRepository.findById(2L)).thenThrow(new ElementNotFoundException(2L));
        productService.deleteProduct(2L);
        verify(productRepository, times(0)).deleteById(2L);
    }
}
