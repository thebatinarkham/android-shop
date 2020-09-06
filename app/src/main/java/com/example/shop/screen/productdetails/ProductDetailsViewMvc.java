package com.example.shop.screen.productdetails;

import com.example.shop.screen.common.views.ObservableViewMvc;
import com.example.shop.screen.model.Product;

import java.util.List;
import java.util.Map;

//for interactive screen use observable if not use ViewMvc
public interface ProductDetailsViewMvc extends ObservableViewMvc<ProductDetailsViewMvc.Listener> {
    public interface Listener{
        void onNavigateUpClicked();
        //action
        void onAddProductToCart(Map<String, Object> products, Product product);
    }
    //notifier
    void bindProduct(Product product);

    void showProgressIndication();

    void hideProgressIndication();


    void bindProductCardAdapter(List<Product> products);

}
