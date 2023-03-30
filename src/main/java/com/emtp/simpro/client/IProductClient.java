package com.emtp.simpro.client;

import java.util.List;

public interface IProductClient<T> {
    List<String> getSimilarProductsIds(String id);
    T getProductById(String id);

}
