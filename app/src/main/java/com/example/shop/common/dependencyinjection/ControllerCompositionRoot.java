package com.example.shop.common.dependencyinjection;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.shop.networking.auth.FetchCredentialUseCase;
import com.example.shop.networking.cart.FetchShoppingCartDetailsCase;
import com.example.shop.networking.product.FetchProduct;
import com.example.shop.networking.product.productdetails.FetchProductDetailsToEditOrAddUseCase;
import com.example.shop.networking.product.productdetails.FetchProductDetailsUseCase;
import com.example.shop.networking.product.StoreProductToDatabaseUseCase;
import com.example.shop.screen.common.screennavigator.ScreenNavigator;
import com.example.shop.screen.common.ViewMvcFactory;
import com.google.firebase.auth.FirebaseUser;

public class ControllerCompositionRoot {
    private final CompositionRoot mCompositionRoot;
    private Activity mActivity;

    //activity require
    public ControllerCompositionRoot(CompositionRoot compositionRoot, Activity activity) {
        this.mCompositionRoot = compositionRoot;
        this.mActivity = activity;
    }

    public FirebaseUser getUserDetails() {
        return mCompositionRoot.getUserDetails();
    }

    private LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(mActivity);
    }

    public ViewMvcFactory getViewMvcFactory(){
        return new ViewMvcFactory(getLayoutInflater(),mActivity);
    }

    public FetchProductDetailsUseCase getFetchProductDetailsUseCase() {
        return new FetchProductDetailsUseCase();
    }

    public FetchProduct getFetchProduct(){
        return  new FetchProduct();
    }

    public Context getContext(){
        return mActivity;
    }
    public ScreenNavigator getScreenNavigator(){
        return  new ScreenNavigator(getContext());
    }

    public FetchShoppingCartDetailsCase getFetchShoppingCartDetailsUseCase() {
        return new FetchShoppingCartDetailsCase();
    }

    public FetchCredentialUseCase getFetchCredentialUseCase(){
        return new FetchCredentialUseCase();
    }

    public FetchProductDetailsToEditOrAddUseCase getFetchProductDetailsToEditOrAddUseCase(){
        return new FetchProductDetailsToEditOrAddUseCase();
    }

    public StoreProductToDatabaseUseCase getStoreProductToDatabaseUseCase(){
        return  new StoreProductToDatabaseUseCase();
    }
}
