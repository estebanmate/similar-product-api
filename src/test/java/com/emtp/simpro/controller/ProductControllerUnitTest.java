package com.emtp.simpro.controller;

import com.emtp.simpro.model.ProductDetail;
import com.emtp.simpro.service.IProductService;
import com.emtp.simpro.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private IProductService service;

    @InjectMocks
    ProductController controller;

    @Test
    @DisplayName("Ok - getProductSimilar")
    void getProductSimilar() throws Exception {
        when(service.getSimilarProducts(any())).thenReturn(Set.of(MockUtil.PRODUCT_DETAIL_21, MockUtil.PRODUCT_DETAIL_22));

        ResponseEntity<Set<ProductDetail>> response = controller.getSimilarProducts("1");

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(2, response.getBody().size());

        verify(service).getSimilarProducts(any());
    }

    @Test
    @DisplayName("Exception - getProductSimilar")
    void getProductSimilar_ResponseStatusException() throws Exception {
        when(service.getSimilarProducts("1234")).thenThrow(ResponseStatusException.class);
        assertThrows(ResponseStatusException.class, () -> {
            service.getSimilarProducts("1234");
        });

        verify(service).getSimilarProducts("1234");
    }
}