package com.nomitri.cart.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDTO {

    private Long id;
    private Set<ProductDTO> productList;
    private Integer totalNoOfItems;
    private Double cartValue;
    @NotNull(message = "Username can not be null!")
    private String userName;
}
