package com.example.shop.screen.common;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;

import com.example.shop.screen.addproduct.addproduct.AddProductMvc;
import com.example.shop.screen.addproduct.addproduct.AddProductViewMvcImpl;
import com.example.shop.screen.addproduct.addproduct1.AddProductMvc1;
import com.example.shop.screen.addproduct.addproduct1.AddProductViewMvcImpl1;
import com.example.shop.screen.auth.AuthViewMvc;
import com.example.shop.screen.auth.AuthViewMvcImpl;
import com.example.shop.screen.auth.login.LoginViewMvc;
import com.example.shop.screen.auth.login.LoginViewMvcImpl;
import com.example.shop.screen.auth.signin.SignInViewMvc;
import com.example.shop.screen.auth.signin.SignInViewMvcImpl;
import com.example.shop.screen.productdetails.ProductDetailsViewMvc;
import com.example.shop.screen.productdetails.ProductDetailsViewMvcImpl;
import com.example.shop.screen.productlist.productlistitem.ProductListItemViewMvc;
import com.example.shop.screen.productlist.productlistitem.ProductListItemViewMvcImpl;
import com.example.shop.screen.productlist.ProductlistViewMvc;
import com.example.shop.screen.productlist.ProductlistViewMvcImpl;
import com.example.shop.screen.shoppingcart.ShoppingCartViewMvc;
import com.example.shop.screen.shoppingcart.ShoppingCartViewMvcImpl;
import com.example.shop.screen.shoppingcart.shoppingcartitem.ShoppingCartItemViewMvc;
import com.example.shop.screen.shoppingcart.shoppingcartitem.ShoppingCartItemViewMvcImpl;

import javax.annotation.Nullable;

public class ViewMvcFactory {
    private final LayoutInflater mLayoutInflater;
    private final Activity mActivity;

    public ViewMvcFactory(LayoutInflater mLayoutInflater, Activity activity) {
        this.mLayoutInflater = mLayoutInflater;
        this.mActivity = activity;
    }

    public ToolbarViewMvc getToolbarViewMvc(@Nullable ViewGroup parent) {
        return new ToolbarViewMvc(mLayoutInflater, parent);
    }

    public ProductDetailsViewMvc getProductDetailsViewMvc(@Nullable ViewGroup parent){
        return new ProductDetailsViewMvcImpl(mLayoutInflater,parent,this);
    }

    public AddProductMvc getProductAddOrEditViewMvc(@Nullable ViewGroup parent,FragmentManager fragmentManager){
        return new AddProductViewMvcImpl(mLayoutInflater,parent,this,fragmentManager);
    }

    public AddProductMvc1 getStoreProductToDatabaseViewMvc(@Nullable ViewGroup parent){
        return new AddProductViewMvcImpl1(mLayoutInflater,parent,this);
    }

    public ProductListItemViewMvc getProductListItemViewMvc(@Nullable ViewGroup parent){
        return new ProductListItemViewMvcImpl(mLayoutInflater,parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProductlistViewMvc getProductListViewMvc(@Nullable ViewGroup parent){
        return new ProductlistViewMvcImpl(mLayoutInflater,parent,this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShoppingCartViewMvc getShoppingCartDetailsViewMvc(@Nullable ViewGroup parent) {
        return new ShoppingCartViewMvcImpl(mLayoutInflater,parent, this);
    }

    public ShoppingCartItemViewMvc getShoppingCartItemViewMvc(@Nullable  ViewGroup parent) {
        return new ShoppingCartItemViewMvcImpl(mLayoutInflater,parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AuthViewMvc getAuthViewMvc(@Nullable ViewGroup parent, FragmentManager fragmentManager){
        return  new AuthViewMvcImpl(mLayoutInflater,fragmentManager,parent, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoginViewMvc getLoginViewMvc(@Nullable ViewGroup parent) {
        return new LoginViewMvcImpl(mLayoutInflater,parent,this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SignInViewMvc getSignInViewMvc(@Nullable ViewGroup parent){
        return new SignInViewMvcImpl(mLayoutInflater,parent,this);
    }

    public AddProductMvc1 getAddProductViewMvc(@Nullable ViewGroup parent){
        return new AddProductViewMvcImpl1(mLayoutInflater,parent,this);
    }

}
