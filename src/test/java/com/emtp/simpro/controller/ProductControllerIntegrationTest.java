package com.emtp.simpro.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "spring.main.banner-mode=OFF")
@AutoConfigureWebTestClient(timeout = "36000")
class ProductControllerIntegrationTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebTestClient client;

    @ParameterizedTest(name = "Test {index} running with productId value: {0}")
    @ValueSource(strings = {"1", "2", "4"})
    @DisplayName("Ok - getProductSimilar")
    void getProductSimilar(String productId) throws JsonProcessingException {

        client.get().uri("product/{productId}/similar", productId)
                .exchange()
                .expectStatus().isOk().expectBody();
    }

    @Test
    @DisplayName("Not Found - getProductSimilar")
    public void getProductSimilar_NotFound() throws Exception {
        client.get().uri("product/{productId}/similar", "6")
                .exchange()
                .expectStatus().isNotFound().expectBody()
                .jsonPath("$.error").isEqualTo("Not Found");
    }

    @Test
    @DisplayName("Server Error - getProductSimilar")
    public void getProductSimilar_ServerError() throws Exception {
        client.get().uri("product/{productId}/similar", "5")
                .exchange()
                .expectStatus().is5xxServerError().expectBody()
                .jsonPath("$.error").isEqualTo("Internal Server Error");
    }

}