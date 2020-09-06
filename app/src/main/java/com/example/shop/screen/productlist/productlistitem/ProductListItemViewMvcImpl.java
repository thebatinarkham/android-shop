package com.example.shop.screen.productlist.productlistitem;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.example.shop.screen.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProductListItemViewMvcImpl extends BaseObservableViewMvc<ProductListItemViewMvc.Listener>
        implements ProductListItemViewMvc{


    private final TextView mProductTitle;
    private final TextView mProductDes;
    private final ImageView mProductImage;
    private Product mProduct;

    public ProductListItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.activity_product,parent,false));
        mProductTitle = findViewById(R.id.product_title);
        mProductDes = findViewById(R.id.product_desc);
        mProductImage = findViewById(R.id.product_image);
        getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Listener listener : getListeners()){
                    listener.onProductClicked(mProduct);
                }
            }
        });

    }


    @Override
    public void bindProduct(final Product product) {
        mProduct = product;
        final ArrayList<Uri> ProductImageUri = new ArrayList<>();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef;

        ArrayList<String> ProductImages = new ArrayList<>();
        ProductImages.add(product.getProductImage1());

        for(int i = 0; i < ProductImages.size(); i++) {
            imageRef = storage.getReference().child("Images")
                    .child(ProductImages.get(i));

            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getContext()).load(uri).placeholder(R.drawable.vector)
                            .into(mProductImage);
                    ProductImageUri.add(uri);
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    mProductTitle.setText(product.getProductName());
                    mProductDes.setText(product.getProductDes());
                }
            });
        }
    }
}
