package com.emtp.simpro.service;

import com.emtp.simpro.model.ProductDetail;

import java.util.Set;

public interface IProductService {
    Set<ProductDetail> getSimilarProducts(String productId);
}
