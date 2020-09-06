package com.example.shop.screen.shoppingcart.shoppingcartitem;

import com.example.shop.screen.common.views.ObservableViewMvc;
import com.example.shop.screen.model.Cart;

import java.util.Map;

public interface ShoppingCartItemViewMvc extends ObservableViewMvc<ShoppingCartItemViewMvc.Listener> {
     interface Listener {
        void onProductQtyIncrease(Cart cart, Map<String,Object> productInCart);
        void onProductQtyDecrease(Cart cart, Map<String,Object> productInCart);

         //action
         void onProductRemoveFromCart(Cart cart);
     }

    //notifier
    void bindCart(Cart cart);

     void notifyViewAboutProductRemove();
}
