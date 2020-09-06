package com.example.shop.screen.shoppingcart.shoppingcartitem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.example.shop.screen.model.Cart;
import com.example.shop.screen.shoppingcart.CartListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCartItemViewMvcImpl extends BaseObservableViewMvc<ShoppingCartItemViewMvc.Listener>
    implements ShoppingCartItemViewMvc {

    private ImageView imageView;
    private TextView title,desc,price,productQty;
    private AutoCompleteTextView dropdownText;
    private ImageView addProductQty,removeProductQty,removeAllProduct;



    public ShoppingCartItemViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.shopping_cart,parent,false));
        imageView = findViewById(R.id.productImageForShoppingCart);
        title = findViewById(R.id.productTitleForCart);
        desc = findViewById(R.id.productDetailsForCart);
        price = findViewById(R.id.productPriceForCart);
        productQty = findViewById(R.id.productQtyForCart);
        addProductQty = findViewById(R.id.productAddForCart);
        removeProductQty = findViewById(R.id.productRemoveForCart);
        removeAllProduct = findViewById(R.id.removeProductAll);
    }


    @Override
    public void bindCart(final Cart cart) {
        title.setText(cart.getProductName());
        desc.setText(cart.getProductDes());
        price.setText("\u20B9" + (Integer.parseInt(String.valueOf(cart.getProductPrice()))));
        productQty.setText(String.valueOf(cart.getProductQty()));

        final ArrayList<Bitmap> ProductImageBitmap = new ArrayList<>();
        StorageReference imageRef;
        FirebaseStorage storage = FirebaseStorage.getInstance();

        imageRef = storage.getReference().child("Images").child(cart.getProductImage1());

        imageRef.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                ProductImageBitmap.add(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            }
        }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
            @Override
            public void onComplete(@NonNull Task<byte[]> task) {
                imageView.setImageBitmap(ProductImageBitmap.get(0));
            }
        });







        addProductQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Integer.parseInt(productQty.getText().toString()) < 9){
                    int Qty = Integer.parseInt(productQty.getText().toString()) + 1 ;
                    int priceOfProduct = Integer.parseInt(String.valueOf(cart.getProductPrice())) / cart.getProductQty();
                    Map<String,Object> productInCart = new HashMap<>();

                    productInCart.put("productQty",Integer.parseInt(String.valueOf(Qty)));
                    productInCart.put("productPrice", priceOfProduct * Qty);

                    price.setText("$" + priceOfProduct * Qty);
                    productQty.setText(String.valueOf(Qty));

                    for(Listener listener: getListeners()){
                        listener.onProductQtyIncrease( cart,productInCart);
                    }
                }

            }
        });

        removeProductQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(productQty.getText().toString()) > 1){
                    int Qty = Integer.parseInt(productQty.getText().toString()) - 1 ;
                    int priceOfProduct = Integer.parseInt(String.valueOf(cart.getProductPrice())) / cart.getProductQty();
                    Map<String,Object> productInCart = new HashMap<>();

                    productInCart.put("productQty",Integer.parseInt(String.valueOf(Qty)));
                    productInCart.put("productPrice", priceOfProduct * Qty);

                    price.setText("$" + priceOfProduct * Qty);
                    productQty.setText(String.valueOf(Qty));

                    for(final Listener listener: getListeners()) {
                        listener.onProductQtyDecrease( cart,productInCart);
                    }
                }
            }
        });

        removeAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(final Listener listener: getListeners()) {
                    listener.onProductRemoveFromCart( cart);
                }

            }
        });
}

    @Override
    public void notifyViewAboutProductRemove() {
        Toast.makeText(getContext(), "CartItemViewImpl", Toast.LENGTH_SHORT).show();
    }


}
