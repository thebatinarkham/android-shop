package com.example.shop.screen.common;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.asksira.bsimagepicker.BSImagePicker;
import com.example.shop.R;
import com.example.shop.screen.common.views.BaseObservableViewMvc;

import java.util.List;

import javax.annotation.Nullable;

public class Example<ListenerType> extends BaseObservableViewMvc<ListenerType>
        implements BSImagePicker.ImageLoaderDelegate , BSImagePicker.OnMultiImageSelectedListener,
        BSImagePicker.OnSelectImageCancelledListener{
    private final FrameLayout mFrameLayout;

    public Example(LayoutInflater inflater, @Nullable ViewGroup parent) {
        super.setRootView(inflater.inflate(R.layout.product_list,parent,false));
        mFrameLayout = findViewById(R.id.frame_content);
    }

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {

    }

    @Override
    protected void setRootView(View mRootView) {
        mFrameLayout.addView(mRootView);
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {

    }

    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {

    }
}
