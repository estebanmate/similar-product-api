package com.emtp.simpro.util;

import com.emtp.simpro.model.ProductDetail;

import java.math.BigDecimal;

public class MockUtil {
    public final static ProductDetail PRODUCT_DETAIL_21 = getProductDetail("21", "Shirt", "5.99", Boolean.TRUE);

    public final static ProductDetail PRODUCT_DETAIL_22 = getProductDetail("22", "Dress", "29.99", Boolean.TRUE);

    private static ProductDetail getProductDetail(String id, String name, String price, Boolean availability) {
        ProductDetail dto = new ProductDetail();
        dto.setId(id);
        dto.setName(name);
        dto.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
        dto.setAvailability(availability);
        return dto;
    }

}
