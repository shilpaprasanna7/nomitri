package com.nomitri.cart.controller;

import com.nomitri.cart.domainobject.CartDO;
import com.nomitri.cart.domainobject.ProductDO;
import com.nomitri.cart.exception.ConstraintsViolationException;
import com.nomitri.cart.exception.EntityNotFoundException;
import com.nomitri.cart.service.CartService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Test
    public void getCartSuccess() throws Exception {

        CartDO cartDO = new CartDO();
        cartDO.setId(1L);
        Mockito.when(cartService.getCart(Mockito.anyLong())).thenReturn(cartDO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/v1/carts/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println("resp: "+result.getResponse());
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void getCartFailure() throws Exception {

        Mockito.when(cartService.getCart(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/v1/carts/1").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertNotNull(result);
    }

    @Test
    public void addCartSuccess() throws Exception {

        CartDO cartDO = new CartDO();
        cartDO.setUserName("test123");
        Mockito.when(cartService.createCart(cartDO)).thenReturn(cartDO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/carts")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        System.out.println("resp: "+result.getResponse());
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void addCartFailure() throws Exception {

        CartDO cartDO = new CartDO();
        Mockito.when(cartService.createCart(cartDO)).thenThrow(ConstraintsViolationException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/v1/carts")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void updateCartSuccess() throws Exception {

        CartDO cartDO = new CartDO();
        Set<ProductDO> products = new HashSet<>();
        ProductDO productDO = new ProductDO();
        productDO.setProduct_name("mango");
        productDO.setPrice(12.3);
        productDO.setId(6L);
        products.add(productDO);
        cartDO.setProducts(products);


        Mockito.when(cartService.updateCart(1, 6)).thenReturn(cartDO);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/carts/1/6")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void updateCartFailure() throws Exception {

        CartDO cartDO = new CartDO();
        Mockito.when(cartService.updateCart(Mockito.anyLong(), Mockito.anyLong())).thenThrow(EntityNotFoundException.class);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/carts/1/6")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void deleteCartSuccess() throws Exception {

        Mockito.doNothing().when(cartService).delete(Mockito.anyLong());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/carts/1")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertNotNull(result.getResponse());
    }

    @Test
    public void getCartByUserName() throws Exception {

        CartDO cartDO = new CartDO();
        cartDO.setUserName("test123");
        cartDO.setCart_value(0.0);
        Mockito.when(cartService.getCartByUserName("test123")).thenReturn(cartDO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/getCartByUserName/test123")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertNotNull(result.getResponse());
    }

}
