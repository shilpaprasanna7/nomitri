package com.nomitri.cart.domainobject;

import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cart",
uniqueConstraints = @UniqueConstraint(name = "uc_userName", columnNames = {"userName"}))
public class CartDO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "username can not be null!")
    private String userName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    Set<ProductDO> products;

    @Column(nullable = false)
    private Boolean deleted;

    @Column
    private Integer total_no_of_items;

    @Column
    private Double cart_value;

    public CartDO(Long id, Boolean deleted, String userName) {
        this.id = id;
        this.deleted = deleted;
        this.total_no_of_items = 0;
        this.cart_value = (double) 0;
        this.userName = userName;
    }
}
