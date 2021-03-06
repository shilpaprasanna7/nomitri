package com.nomitri.cart.domainobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class ProductDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String product_name;

    @Column
    private Double price;

    @JsonIgnore
    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    Set<CartDO> cart;

    public ProductDO(String product_name, Double price) {
        this.product_name = product_name;
        this.price = price;
    }
}
