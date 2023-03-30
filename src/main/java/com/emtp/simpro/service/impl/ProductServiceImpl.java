package com.emtp.simpro.service.impl;

import com.emtp.simpro.client.impl.ProductClientImpl;
import com.emtp.simpro.model.ProductDetail;
import com.emtp.simpro.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    ProductClientImpl client;

    @Override
    public Set<ProductDetail> getSimilarProducts(String productId) {
        List<String> ids = client.getSimilarProductsIds(productId);

        return ids.parallelStream().map(id -> client.getProductById(id)).filter(p -> Objects.nonNull(p.getId()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
