package com.example.shop.screen.addproduct.addproduct1;

import android.net.Uri;

import com.example.shop.screen.common.views.ObservableViewMvc;

import java.io.File;
import java.util.Map;

public interface AddProductMvc1  extends ObservableViewMvc<AddProductMvc1.Listener> {

    interface Listener {
        void FetchProductAndStoreToDatabase(Map<String, Object> product);

        void EditProductAndStoreToDatabase(String productId,Map<String, Object> product);

        void StoreProductImageToDatabase(byte[] readFile,String filename);

        void DeleteProductFromDatabase(String productId);
    }

    void notifyProductStoreToDatabase();

    void notifyProductEditStoreSuccessFull();

    void notifyViewAboutProductImageStoreProcess(double process);

    void notifyViewAboutProductDeleteFromDatabase();

}
