package com.example.shop.screen.addproduct.addproduct;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.asksira.bsimagepicker.BSImagePicker;
import com.bumptech.glide.Glide;
import com.example.shop.networking.product.productdetails.FetchProductDetailsToEditOrAddUseCase;
import com.example.shop.screen.common.BaseActivity;
import com.example.shop.screen.model.Product;
import com.example.shop.screen.productlist.ProductListActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.FileUtil;


public class AddProductActivity extends BaseActivity implements AddProductMvc.Listener,
        FetchProductDetailsToEditOrAddUseCase.Listener, BSImagePicker.OnMultiImageSelectedListener,BSImagePicker.ImageLoaderDelegate,
        BSImagePicker.OnSelectImageCancelledListener{

    private AddProductMvc mViewMvc;
    private FetchProductDetailsToEditOrAddUseCase mFetchProductDetailsToEditOrAddUseCase;
    private File file;
    private File CompressedImage;
    ArrayList<Uri> ProductImageUri = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().
                    getProductAddOrEditViewMvc(null,getSupportFragmentManager());

        mFetchProductDetailsToEditOrAddUseCase = getCompositionRoot()
                .getFetchProductDetailsToEditOrAddUseCase();

        setContentView(mViewMvc.getRootView());
        AddProductActivity.this.setTitle("Add Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
             if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchProductDetailsToEditOrAddUseCase.registerListener(this);
        Product product = getIntent().getParcelableExtra("item");
        mFetchProductDetailsToEditOrAddUseCase.FetchProductToEdit(product);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchProductDetailsToEditOrAddUseCase.unregisterListener(this);
    }


    @Override
    public void notifyProductEditFetchSuccess(Product product) {
        mViewMvc.FetchProductForEditResult(product);
    }


    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
        Glide.with(this).load(imageUri).into(ivImage);
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        ProductImageUri.clear();
        for(int i = 0 ; i < uriList.size(); i++) {
            try {
                file = new File(String.valueOf(FileUtil.from(this, uriList.get(i))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            File compressedFile = Compressor.getDefault(this).compressToFile(file);
            CompressedImage = new Compressor.Builder(this)
                    .setQuality(75)
                    .setDestinationDirectoryPath(this.getExternalFilesDir(null).getAbsolutePath())
                    .build()
                    .compressToFile(compressedFile);
            ProductImageUri.add(Uri.fromFile(CompressedImage));
        }
        mViewMvc.bindProductsImages(ProductImageUri);
    }

    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {
        Toast.makeText(this, "Selection is cancelled, Multi-selection is " + isMultiSelecting, Toast.LENGTH_SHORT).show();
    }


}
