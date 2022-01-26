package com.nomitri.cart.service;

import com.nomitri.cart.domainobject.CartDO;
import com.nomitri.cart.domainobject.ProductDO;
import com.nomitri.cart.exception.ConstraintsViolationException;
import com.nomitri.cart.exception.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CartService.class)
public class CartServiceTest {

    @MockBean
    CartService cartService;

    @Test
    public void getCartSuccess() throws EntityNotFoundException {
        CartDO cartDO = new CartDO();
        cartDO.setId(1L);
        Mockito.when(cartService.getCart(1L)).thenReturn(cartDO);
        CartDO newCartDO = cartService.getCart(1L);
        Assert.assertEquals(cartDO, newCartDO);
    }

    @Test
    public void getCartFailure() throws EntityNotFoundException {
        Mockito.when(cartService.getCart(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void createCartSuccess() throws ConstraintsViolationException {
        CartDO cartDO = new CartDO();
        cartDO.setUserName("test123");
        Mockito.when(cartService.createCart(cartDO)).thenReturn(cartDO);
        CartDO cart = cartService.createCart(cartDO);
        Assert.assertEquals(cartDO, cart);
    }

    @Test
    public void createCartFailure() throws ConstraintsViolationException {
        Mockito.when(cartService.createCart(new CartDO())).thenThrow(ConstraintsViolationException.class);
    }

    @Test
    public void updateCartSuccess() throws EntityNotFoundException {

        CartDO cartDO = new CartDO();
        Set<ProductDO> products = new HashSet<>();
        ProductDO productDO = new ProductDO();
        productDO.setProduct_name("mango");
        productDO.setPrice(12.3);
        productDO.setId(6L);
        products.add(productDO);
        cartDO.setProducts(products);

        Mockito.when(cartService.updateCart(1, 6)).thenReturn(cartDO);
        CartDO cart = cartService.updateCart(1L, 6);
        Assert.assertEquals(cartDO, cart);
    }

    @Test
    public void updateCartFailure() throws EntityNotFoundException {
        Mockito.when(cartService.updateCart(Mockito.anyLong(), Mockito.anyLong())).thenThrow(EntityNotFoundException.class);
    }

    @Test
    public void deleteCartSuccess() throws  EntityNotFoundException {
        Mockito.doNothing().when(cartService).delete(1L);
    }

}
