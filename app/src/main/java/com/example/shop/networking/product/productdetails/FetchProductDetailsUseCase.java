package com.example.shop.networking.product.productdetails;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop.common.BaseObservable;
import com.example.shop.screen.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FetchProductDetailsUseCase extends BaseObservable<FetchProductDetailsUseCase.Listener> {

    //return
    public interface Listener {
        void onProductDetailsFetched(Product product);
        void oProductDetailsFetchedFailed();
        void onProductCardAdapterFetched(List<Product> products);
    }


    public void FetchProductDetailsAndNotify(Activity activity){
        Product product = activity.getIntent().getParcelableExtra("item");
        notifySuccess(product);
    }

    public void FetchProductDetailsImageAndNotify(){

    }

    public void notifySuccess(Product product){
        for (Listener listener :getListeners()){
            //product details activity
            listener.onProductDetailsFetched(product);
        }
    }

    public void FetchProductCardAdapterAndNotify(Activity mActivity){

        //Init cloud firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference =  db.collection("products");
        //Populating Product
        final List<Product> products = new ArrayList<>();
        collectionReference.addSnapshotListener(mActivity, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Product product = documentSnapshot.toObject(Product.class);
                    String ProductName = product.getProductName();
                    String ProductDesc = product.getProductDes();
                    String ProductImage1 = product.getProductImage1();

//                    models.add(new Model(ProductId,ProductName, ProductDesc,ProductPrice,ProductCategory,ProductTag
//                            ,ProductLocation,ProductImage1,ProductImage2,ProductImage3));
                    //Populating Product
                    products.add(new Product(ProductName,ProductDesc,ProductImage1));

                }
                notifyProductAdapterFetchSuccess(products);
            }
        });


    }

    private void notifyProductAdapterFetchSuccess(List<Product> products) {
        for (Listener listener :getListeners()){
//            product details activity
            listener.onProductCardAdapterFetched(products);
        }
    }

    public void checkForProductInCartThenAddAndNotify(final Map<String, Object> products, Product product, final Context mContext){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection("shopping-cart").
                document(FirebaseAuth.getInstance().getUid()).collection("products");

        Query query = collectionReference.whereEqualTo("productId", product.getProductId());

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    boolean isEmpty = task.getResult().isEmpty();
                    List<DocumentSnapshot> document = task.getResult().getDocuments();
                    List<DocumentSnapshot> doc = task.getResult().getDocuments();
                    if(task.getResult().getDocuments().size() > 0 ){
                        notifyAlreadyAddedToCart(mContext);
                    }else{
                        db.collection("shopping-cart").document(FirebaseAuth.getInstance().getUid()).collection("products").add(products)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        notifyProductAddedToCart(mContext);
                                    }
                                });

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                notifyProductAddedToCartFailure(mContext ,e);
            }
        });

    }

    public void notifyProductAddedToCart(Context mContext){
        Toast.makeText(mContext,"Product Added in Cart.",Toast.LENGTH_LONG).show();
    }
    public void notifyAlreadyAddedToCart(Context mContext){
        Toast.makeText(mContext, "Product already Exists In Cart.",Toast.LENGTH_LONG).show();
    }

    public void notifyProductAddedToCartFailure(Context mContext,Exception e){
        Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
    }


}
