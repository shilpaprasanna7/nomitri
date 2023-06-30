package com.nomitri.cart.controller;

import com.nomitri.cart.datatransferobject.CartDTO;
import com.nomitri.cart.domainobject.CartDO;
import com.nomitri.cart.exception.ConstraintsViolationException;
import com.nomitri.cart.exception.EntityNotFoundException;
import com.nomitri.cart.mapper.CartMapper;
import com.nomitri.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("v1/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart(@RequestBody CartDTO cartDTO) throws ConstraintsViolationException {

        if (validateCartDTO(cartDTO)) {
            CartDO cart = cartService.createCart(CartMapper.makeCartDO(cartDTO));
            return ResponseEntity.accepted().body(CartMapper.makeCartDTO(cart));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable long cartId) throws EntityNotFoundException {

        CartDO cartDO = cartService.getCart(cartId);
        return Objects.nonNull(cartDO) ? ResponseEntity.ok(CartMapper.makeCartDTO(cartDO))
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{cartId}/{productId}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable long cartId, @PathVariable long productId) throws EntityNotFoundException {

        CartDO cart = cartService.updateCart(cartId, productId);
        return ResponseEntity.ok(CartMapper.makeCartDTO(cart));
    }

    @DeleteMapping("/{cartId}")
    public void deleteCart(@PathVariable long cartId) throws EntityNotFoundException {

        cartService.delete(cartId);
    }

    private boolean validateCartDTO(CartDTO cartDTO){
        if (Objects.nonNull(cartDTO))
            return Objects.nonNull(cartDTO.getUserName());
        return false;
    }

    @GetMapping("/{userName}")
    public ResponseEntity<CartDTO> getCartByUserName(@PathVariable String userName) throws EntityNotFoundException {

        CartDO cartDO = cartService.getCartByUserName(userName);
        return Objects.nonNull(cartDO) ? ResponseEntity.ok(CartMapper.makeCartDTO(cartDO))
                : ResponseEntity.notFound().build();
    }
}
