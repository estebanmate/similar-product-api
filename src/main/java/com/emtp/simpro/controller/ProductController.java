package com.emtp.simpro.controller;

import com.emtp.simpro.api.ProductApi;
import com.emtp.simpro.model.ProductDetail;
import com.emtp.simpro.service.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import java.util.Set;

@Tag(name = "Product")
@RestController
public class ProductController implements ProductApi {
    @Autowired
    private IProductService service;
    @Override
    public ResponseEntity<Set<ProductDetail>> getSimilarProducts(String productId) {

        Set<ProductDetail> response = service.getSimilarProducts(productId);

        return ResponseEntity.ok(response);
    }
}
