package com.emtp.simpro.client.impl;

import com.emtp.simpro.client.IProductClient;
import com.emtp.simpro.model.ProductDetail;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

//@Slf4j
@Service
public class ProductClientImpl implements IProductClient<ProductDetail> {

    @Value("${api.client.product.base-url}")
    private String baseUrl;
    @Value("${api.client.product.getSimilarProductIds}")
    private String productSimilarIdsPath;
    @Value("${api.client.product.getProductById}")
    private String productByIdPath;
    @Autowired
    WebClient productMockclient;

    public ProductClientImpl() {
    }

    @Override
    @Cacheable("similarProductCache")
    public List<String> getSimilarProductsIds(String id) {
        final String url = String.format("%s%s", baseUrl, productSimilarIdsPath);
//        log.debug("Calling URL: ["+url+"]");
        Mono<String[]> response = productMockclient.get().uri(productSimilarIdsPath, id).retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), clientResponse -> {
//                    final String msg = String.format("Executing endpoint %s with productId %s and status code %s", url,
//                            id, clientResponse.statusCode());
//                    log.error(msg);
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase());
                }).onStatus(HttpStatus::is5xxServerError, clientResponse -> {
//                    final String msg = String.format("Executing endpoint %s with productId %s and status code %s", url,
//                            id, clientResponse.statusCode());
//                    log.error(msg);
                    throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
                }).bodyToMono(String[].class);
        return Arrays.asList(response.block());
    }

    @Override
    @Cacheable("productCache")
    public ProductDetail getProductById(String id) {
        final String url = String.format("%s%s", baseUrl, productByIdPath);
//        log.debug("Calling URL: ["+url+"]");
        Mono<ProductDetail> response = productMockclient.get().uri(productByIdPath, id).retrieve()
                .onStatus(status -> status.equals(HttpStatus.NOT_FOUND), clientResponse -> {
//                    final String msg = String.format("Executing endpoint %s with productId %s and status code %s", url,
//                            id, clientResponse.statusCode());
//                    log.error(msg);
                    return Mono.empty();
                }).onStatus(HttpStatus::is5xxServerError, clientResponse -> {
//                    final String msg = String.format("Executing endpoint %s with productId %s and status code %s", url,
//                            id, clientResponse.statusCode());
//                    log.error(msg);
                    return Mono.empty();
                }).bodyToMono(ProductDetail.class);
        return response.block();
    }
}
