package com.emtp.simpro.service.impl;

import com.emtp.simpro.client.impl.ProductClientImpl;
import com.emtp.simpro.model.ProductDetail;
import com.emtp.simpro.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductClientImpl client;

    @InjectMocks
    ProductServiceImpl service;

    @Test
    @DisplayName("Success - getProductSimilar")
    void getProductSimilar() {
        when(client.getSimilarProductsIds("1")).thenReturn((List.of("21")));
        when(client.getProductById("21")).thenReturn(MockUtil.PRODUCT_DETAIL_21);

        Set<ProductDetail> responseDto = service.getSimilarProducts("1");

        assertNotNull(responseDto);
        assertEquals(1, responseDto.size());
        assertEquals("21", responseDto.stream().findFirst().get().getId());

        verify(client).getSimilarProductsIds(any());
        verify(client).getProductById(any());
    }

    @Test
    @DisplayName("Exception - client.getProductSimilarIds")
    void getProductSimilar_ResponseStatusException() throws Exception {
        when(client.getSimilarProductsIds("1234")).thenThrow(ResponseStatusException.class);
        assertThrows(ResponseStatusException.class, () -> {
            client.getSimilarProductsIds("1234");
        });
        verify(client).getSimilarProductsIds("1234");
        verify(client, never()).getProductById(any());
    }

    @Test
    @DisplayName("Exception - client.getProductById")
    void getProductById_ResponseStatusException() throws Exception {
        when(client.getProductById("1234")).thenThrow(ResponseStatusException.class);
        assertThrows(ResponseStatusException.class, () -> {
            client.getProductById("1234");
        });
    }
}