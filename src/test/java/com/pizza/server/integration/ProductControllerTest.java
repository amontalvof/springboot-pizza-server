package com.pizza.server.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.pizza.server.models.Product;
import com.pizza.server.repositories.ProductRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ProductRepository productRepository;

    private Product[] products = new Product[] {
            new Product("Pizza Margherita", "Tomato sauce, mozzarella, basil", "http://localhost/img.png", "[8,9,19]"),
            new Product("Pizza Marinara", "Tomato sauce, garlic, oregano", "http://localhost/img.png", "[7,9,19]"),
            new Product("Pizza Capricciosa", "Tomato sauce, mozzarella, mushrooms, ham, artichokes",
                    "http://localhost/img.png", "[9,19,29]"),
    };

    @BeforeAll
    void setup() {
        for (int i = 0; i < products.length; i++) {
            productRepository.save(products[i]);
        }
    }

    @AfterAll
    void clear() {
        productRepository.deleteAll();
    }

    @Test
    void testReadProductsSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/products");
        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(products.length))
                .andExpect(
                        jsonPath(
                                "$.[?(@.id == \"2\" && @.title == \"Pizza Marinara\" && @.description ==\"Tomato sauce, garlic, oregano\")]")
                                .exists());
    }

    @Test
    void testReadProductSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/products/1");
        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(products[0].getTitle()))
                .andExpect(jsonPath("$.description").value(products[0].getDescription()));
    }

    @Test
    void testReadProductNotFound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/products/100");
        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    void testCreateProductSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic QWRtaW5pc3RyYXRvcjpQQHNzd29yZDEyMw==")
                .content(
                        "{\"title\":\"Pizza Funghi\",\"description\":\"Tomato sauce, mozzarella, mushrooms\",\"img\":\"http://localhost/img.png\",\"prices\":\"[9,19,29]\"}");
        mockMvc.perform(request).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Pizza Funghi"))
                .andExpect(jsonPath("$.description").value("Tomato sauce, mozzarella, mushrooms"));
    }

    @Test
    void testCreateProductBadRequest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic QWRtaW5pc3RyYXRvcjpQQHNzd29yZDEyMw==")
                .content("{\"title\":\"Pizza Funghi\"}");
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateProductSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.put("/api/products/3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic QWRtaW5pc3RyYXRvcjpQQHNzd29yZDEyMw==")
                .content(
                        "{\"title\":\"Pizza Funghi\",\"description\":\"Tomato sauce, mozzarella, mushrooms\",\"img\":\"http://localhost/img.png\",\"prices\":\"[9,19,29]\"}");
        mockMvc.perform(request).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("Pizza Funghi"))
                .andExpect(jsonPath("$.description").value("Tomato sauce, mozzarella, mushrooms"));
    }

    @Test
    void testUpdateProductNotFound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.put("/api/products/100")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic QWRtaW5pc3RyYXRvcjpQQHNzd29yZDEyMw==")
                .content(
                        "{\"title\":\"Pizza Funghi\",\"description\":\"Tomato sauce, mozzarella, mushrooms\",\"img\":\"http://localhost/img.png\",\"prices\":\"[9,19,29]\"}");
        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    void testUpdateProductBadRequest() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.put("/api/products/3")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Basic QWRtaW5pc3RyYXRvcjpQQHNzd29yZDEyMw==")
                .content("{\"title\":\"Pizza Funghi\"}");
        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteProductSuccess() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/products/2").header("Authorization",
                "Basic QWRtaW5pc3RyYXRvcjpQQHNzd29yZDEyMw==");
        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    void testDeleteProductNotFound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/products/100").header("Authorization",
                "Basic QWRtaW5pc3RyYXRvcjpQQHNzd29yZDEyMw==");
        mockMvc.perform(request).andExpect(status().isNotFound());
    }
}
