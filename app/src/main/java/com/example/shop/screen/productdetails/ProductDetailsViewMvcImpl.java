package com.example.shop.screen.productdetails;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.shop.R;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.example.shop.screen.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsViewMvcImpl extends BaseObservableViewMvc<ProductDetailsViewMvc.Listener>
implements ProductDetailsViewMvc {
    private ChipGroup chipGroup;

    private FloatingActionButton AddToCart;
    private Toolbar mToolbar;

    private TextView ProductTitle,ProductDesc,ProductPrice,ProductLocation;
    ArrayList<Uri> ProductImageUri = new ArrayList<>();
    private ViewPager viewPager;
    private ProductDetailsAdapter productDetailsAdapter;


    final Map<String,Object> products = new HashMap<>();
    private Product product;


    public ProductDetailsViewMvcImpl(LayoutInflater inflater, final ViewGroup parent, final ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.activity_product_details,parent,false));
        mToolbar = findViewById(R.id.productDetailsMenu_toolbar);


        ProductTitle = findViewById(R.id.productTitleForCard);
        ProductPrice = findViewById(R.id.productPriceForCard);
        ProductDesc = findViewById(R.id.productDetailsForCard);
        AddToCart = findViewById(R.id.addToCart);
    }


    @Override
    public void bindProduct(final Product product) {
        this.product = product;
        ProductTitle.setText(product.getProductName());
        ProductPrice.setText("\u20B9" +  product.getProductPrice());
        ProductDesc.setText(product.getProductDes());
//        ProductLocation.setText(product.getProductLocation());
//        ProductCategory.setText(product.getProductCategory());

        final ArrayList<String> ProductImages = new ArrayList<>();
        ProductImages.add(product.getProductImage1());
        ProductImages.add(product.getProductImage2());
        ProductImages.add(product.getProductImage3());

        for(int i = 0 ; i < ProductImages.size(); i++){
            StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("Images")
                    .child(ProductImages.get(i));
            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ProductImageUri.add(uri);
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(ProductImageUri.size() >= 3){
                        ViewPager viewPager1 = findViewById(R.id.imageViewPager);
                        ProductImageAdapter productImageAdapter = new ProductImageAdapter(getContext(),ProductImageUri);
                        viewPager1.setAdapter(productImageAdapter);
                    }
                }
            });
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            AddToCart.setVisibility(View.VISIBLE);
            AddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    products.put("productId", product.getProductId());
                    products.put("productName", product.getProductName());
                    products.put("productPrice", product.getProductPrice());
                    products.put("productDes", product.getProductDes());
                    products.put("productImage1", product.getProductImage1());
                    products.put("productQty",1);

                    for (Listener listener :getListeners()) {
                        listener.onAddProductToCart(products,product);
                    }
                }
            });

        }




    }

    @Override
    public void showProgressIndication() {

    }

    @Override
    public void hideProgressIndication() {

    }


    @Override
    public void bindProductCardAdapter(List<Product> products) {
        viewPager = findViewById(R.id.viewpagerForProductDetails);
        productDetailsAdapter = new ProductDetailsAdapter(products,getContext());
        viewPager.setAdapter(productDetailsAdapter);
        viewPager.setPadding(0,0,0,0);

    }




}
