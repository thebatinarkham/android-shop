package com.example.shop.screen.productlist;

import com.example.shop.screen.common.nav_drawer.DrawerItems;
import com.example.shop.screen.common.views.ObservableViewMvc;
import com.example.shop.screen.model.Product;

import java.util.List;

public interface ProductlistViewMvc extends ObservableViewMvc<ProductlistViewMvc.Listener> {
    public interface Listener{
        void onProductClicked(Product product);

        void onDrawerItemClicked(DrawerItems item);
    }
    void bindProducts(List<Product> products);
}
