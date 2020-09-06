package com.example.shop.screen.productdetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.shop.R;
import com.example.shop.screen.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsAdapter extends PagerAdapter {

//    Adapter For Slider
    private List<Product> products;
    private LayoutInflater layoutInflater;
    private Context context;

    public ProductDetailsAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_product ,container,false);

        final ImageView imageView;
        final TextView title,desc;
        final ArrayList<Bitmap> ProductImageBitmap = new ArrayList<>();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imageRef;

        imageView = view.findViewById(R.id.product_image);
        title = view.findViewById(R.id.product_title);
        desc = view.findViewById(R.id.product_desc);
        ArrayList<String> ProductImages = new ArrayList<>();
        ProductImages.add(products.get(position).getProductImage1());

        for(int i = 0; i < ProductImages.size(); i++){
            imageRef = storage.getReference().child("Images")
                    .child(ProductImages.get(i));
            imageRef.getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    ProductImageBitmap.add(BitmapFactory.decodeByteArray(bytes,0,bytes.length));

                }
            }).addOnCompleteListener(new OnCompleteListener<byte[]>() {
                @Override
                public void onComplete(@NonNull Task<byte[]> task) {
                    imageView.setImageBitmap(ProductImageBitmap.get(0));
                    title.setText(products.get(position).getProductName());
                    desc.setText(products.get(position).getProductDes());
                    notifyDataSetChanged();
                }

            });


        }
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public float getPageWidth(int position) {
        return (0.45f);
    }


}
