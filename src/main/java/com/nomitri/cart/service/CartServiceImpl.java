package com.nomitri.cart.service;

import com.nomitri.cart.dataaccessobject.CartRepository;
import com.nomitri.cart.dataaccessobject.ProductRepository;
import com.nomitri.cart.domainobject.CartDO;
import com.nomitri.cart.domainobject.ProductDO;
import com.nomitri.cart.exception.ConstraintsViolationException;
import com.nomitri.cart.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartDO createCart(CartDO cartDO) throws ConstraintsViolationException {
        CartDO cart;
        try {
            cart = cartRepository.save(cartDO);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintsViolationException(e.getMessage());
        }
        return cart;
    }

    @Override
    @Transactional
    public CartDO updateCart(long cartId, long productId) throws EntityNotFoundException {

        if (getCart(cartId) != null) {
            CartDO cartDO = getCart(cartId);
            Optional<ProductDO> productDO = productRepository.findById(productId);
            if (productDO.isPresent()) {
                ProductDO productPresent = productDO.get();
                int count = calculateCount(cartDO);
                double price = calculatePrice(cartDO);
                Set<ProductDO> products = cartDO.getProducts();

                if (products.isEmpty()) {
                    products = new HashSet<>();
                    products.add(productPresent);
                    count++;
                    price += productPresent.getPrice();
                } else {
                    Optional<ProductDO> first = products.stream().filter(p -> p.getId().equals(productPresent.getId())).findFirst();
                    if (first.isPresent()) {
                        count--;
                        price -= productPresent.getPrice();
                        products.remove(productPresent);
                    } else {
                        count++;
                        price += productPresent.getPrice();
                        products.add(productPresent);
                    }
                }
                cartDO.setCart_value(price);
                cartDO.setTotal_no_of_items(count);
                cartDO.setProducts(products);
                return cartRepository.save(cartDO);
            } else {
                throw new EntityNotFoundException("Could not find product with id: " + productId);
            }
        } else {
            throw new EntityNotFoundException("Could not find entity with id: " + cartId);
        }
    }

    @Override
    public void delete(Long cartId) throws EntityNotFoundException {
        if (Objects.nonNull(getCart(cartId))) {
            cartRepository.deleteById(cartId);
        }
    }

    @Override
    public CartDO getCart(Long cartId) throws EntityNotFoundException {
        Optional<CartDO> cartDOOptional = cartRepository.findById(cartId);
        if (cartDOOptional.isPresent())
            if (!cartDOOptional.get().getDeleted())
                return cartDOOptional.get();
            else
                throw new EntityNotFoundException("Could not find entity with id: " + cartId);
        return null;
    }

    @Override
    public CartDO getCartByUserName(String userName) throws EntityNotFoundException {
        CartDO byUserName = cartRepository.findByUserName(userName);
        if(byUserName != null )
            return byUserName;
        else
            throw new EntityNotFoundException("Could not find entity with username: " + userName);
    }

    private int calculateCount(CartDO cartDO) {
        return Objects.nonNull(cartDO.getTotal_no_of_items()) ? cartDO.getTotal_no_of_items() : 0;
    }

    private double calculatePrice(CartDO cartDO) {
        return Objects.nonNull(cartDO.getCart_value()) ? cartDO.getCart_value() : (double) 0;
    }

}
