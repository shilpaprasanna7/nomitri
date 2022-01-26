package com.nomitri.cart.mapper;

import com.nomitri.cart.datatransferobject.ProductDTO;
import com.nomitri.cart.domainobject.ProductDO;

public class ProductMapper {

    public static ProductDTO makeProductDTO(ProductDO productDO) {
        return new ProductDTO(productDO.getId(), productDO.getProduct_name(), productDO.getPrice());
    }
}
