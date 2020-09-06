package com.example.shop.screen.addproduct.addproduct;

import android.net.Uri;

import androidx.collection.ArraySet;

import com.example.shop.screen.common.views.ObservableViewMvc;
import com.example.shop.screen.model.Product;

import java.util.ArrayList;

public interface AddProductMvc extends ObservableViewMvc<AddProductMvc.Listener> {
    interface Listener{

    }

    //notifier
    void FetchProductForEditResult(Product product);


    void bindProductsImages(ArrayList<Uri> ProductImageUri);

}
