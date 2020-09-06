package com.example.shop.networking.product;

import android.app.Activity;

import androidx.annotation.Nullable;

import com.example.shop.common.BaseObservable;
import com.example.shop.screen.model.Product;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FetchProduct extends BaseObservable<FetchProduct.Listener> {
    public interface Listener {
        //return product
        void onProductFetched(List<Product> products);
        void onProductFetchFailed();
    }


    public void FetchProductAndNotify(final Activity activity){

        //Init cloud firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //Populating Product
        final List<Product> products = new ArrayList<>();
        CollectionReference collectionReference =  db.collection("products");
        collectionReference.addSnapshotListener(activity, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e != null){
                    notifyFailure();
                }
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Product product = documentSnapshot.toObject(Product.class);
                    String ProductId = documentSnapshot.getId();
                    String ProductName = product.getProductName();
                    String ProductDesc = product.getProductDes();
                    int ProductPrice = product.getProductPrice();
                    String ProductCategory = product.getProductCategory();
                    String ProductTag = product.getProductTag();
                    String ProductLocation = product.getProductLocation();
                    String ProductImage1 = product.getProductImage1();
                    String ProductImage2 = product.getProductImage2();
                    String ProductImage3 = product.getProductImage3();

                    products.add(new Product(ProductId,ProductName, ProductDesc,ProductPrice,ProductCategory,ProductTag
                            ,ProductLocation,ProductImage1,ProductImage2,ProductImage3));
                    notifySuccess(products);


                }
            }
        });


    }

    private void notifySuccess(List<Product> products){
        for(Listener listener:getListeners()){
            listener.onProductFetched(products);
        }
    }
    private void notifyFailure() {
        for(Listener listener: getListeners()){
            listener.onProductFetchFailed();
        }
    }


}
