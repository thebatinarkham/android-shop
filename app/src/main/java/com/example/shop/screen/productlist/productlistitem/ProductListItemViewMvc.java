package com.example.shop.screen.productlist.productlistitem;

import com.example.shop.screen.common.views.ObservableViewMvc;
import com.example.shop.screen.model.Product;

public interface ProductListItemViewMvc extends ObservableViewMvc<ProductListItemViewMvc.Listener> {
    interface Listener{
        void onProductClicked(Product product);
    }
    //Adapter bindProduct
    void bindProduct(Product product);
}
