package com.example.shop.networking.product.productdetails;

import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.example.shop.screen.model.Product;

public class FetchProductDetailsToEditOrAddUseCase extends
        BaseObservableViewMvc<FetchProductDetailsToEditOrAddUseCase.Listener> {
    public interface Listener {
        void notifyProductEditFetchSuccess(Product product);
    }

    public void FetchProductToEdit(Product product){
        notifyFetchProductToEditSuccess(product);
    }

    private void notifyFetchProductToEditSuccess(Product product) {
        for(Listener listener:getListeners()){
            listener.notifyProductEditFetchSuccess(product);
        }
    }
}
