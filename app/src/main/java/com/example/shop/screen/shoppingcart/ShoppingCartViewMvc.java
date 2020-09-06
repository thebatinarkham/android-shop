package com.example.shop.screen.shoppingcart;

import com.example.shop.screen.common.views.ObservableViewMvc;
import com.example.shop.screen.model.Cart;

import java.util.List;
import java.util.Map;

public interface ShoppingCartViewMvc extends ObservableViewMvc<ShoppingCartViewMvc.Listener> {
    interface Listener{
        void removeProductFromCart(Cart cart);
        void increaseProductInCart(Cart cart, Map<String, Object> productInCart);
        void decreaseProductInCart(Cart cart, Map<String, Object> productInCart);

    }

    //notifier
    void bindCarts(List<Cart> cart);

    void notifyViewAboutProductRemove();

    void notifyProductQtyIncrease();

    void notifyProductQtyDecrease();
}
