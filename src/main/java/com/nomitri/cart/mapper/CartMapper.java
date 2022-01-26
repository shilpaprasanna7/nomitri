package com.nomitri.cart.mapper;

import com.nomitri.cart.datatransferobject.CartDTO;
import com.nomitri.cart.datatransferobject.ProductDTO;
import com.nomitri.cart.domainobject.CartDO;
import com.nomitri.cart.domainobject.ProductDO;

import java.util.*;

public class CartMapper {
    public static CartDO makeCartDO(CartDTO cartDTO) {

        return new CartDO(cartDTO.getId(), false, cartDTO.getUserName());
    }

    public static CartDTO makeCartDTO(CartDO cartDO) {

        Set<ProductDO> productDOSet = cartDO.getProducts();
        Set<ProductDTO> productDTOSet= new HashSet<>();
        if(Objects.nonNull(productDOSet) && !productDOSet.isEmpty()) {
            for (ProductDO productDO : productDOSet) {
                ProductDTO productDTO = ProductMapper.makeProductDTO(productDO);
                productDTOSet.add(productDTO);
            }
        }
        return new CartDTO(cartDO.getId(), productDTOSet, cartDO.getTotal_no_of_items(), cartDO.getCart_value(), cartDO.getUserName());
    }
}
