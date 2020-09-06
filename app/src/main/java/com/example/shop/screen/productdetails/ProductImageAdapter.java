package com.example.shop.screen.productdetails;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.shop.R;

import java.util.ArrayList;

public class ProductImageAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<Uri> imagesUri;
    ArrayList<Integer> images;

    public ProductImageAdapter(Context context, ArrayList<Uri> imagesUri) {
        this.context = context;
        this.imagesUri = imagesUri;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.slidingimages_layout,container,false);
        ImageView imageView = view.findViewById(R.id.slider_images);
        Glide.with(context).load(imagesUri.get(position)).into(imageView);
        container.addView(view,0);
        return view;
    }

    @Override
    public int getCount() {
        return imagesUri.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
