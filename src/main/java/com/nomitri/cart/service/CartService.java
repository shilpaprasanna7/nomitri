package com.nomitri.cart.service;

import com.nomitri.cart.domainobject.CartDO;
import com.nomitri.cart.exception.ConstraintsViolationException;
import com.nomitri.cart.exception.EntityNotFoundException;

public interface CartService {

    CartDO createCart(CartDO cartDO) throws ConstraintsViolationException;

    CartDO updateCart(long cartId, long productId) throws EntityNotFoundException;

    void delete(Long cartId) throws EntityNotFoundException;

    CartDO getCart(Long cartId) throws EntityNotFoundException;

    CartDO getCartByUserName(String userName) throws EntityNotFoundException;
}
