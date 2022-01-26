package com.nomitri.cart.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private Long id;
    private String productName;
    private Double price;
    private CartDTO cartDTO;

    public ProductDTO(Long id, String productName,  Double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }
}
