package com.example.shop.screen.addproduct.addproduct1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.shop.networking.product.productdetails.FetchProductDetailsToEditOrAddUseCase;
import com.example.shop.networking.product.StoreProductToDatabaseUseCase;
import com.example.shop.screen.common.BaseActivity;
import com.example.shop.screen.model.Product;
import com.example.shop.screen.productlist.ProductListActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class AddProductActivity1 extends BaseActivity implements AddProductMvc1.Listener,
        FetchProductDetailsToEditOrAddUseCase.Listener ,StoreProductToDatabaseUseCase.Listener{

    private AddProductMvc1 mViewMvc;
    private StoreProductToDatabaseUseCase mStoreProductToDatabaseUseCase;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getCompositionRoot().getViewMvcFactory().getStoreProductToDatabaseViewMvc(null);
        mStoreProductToDatabaseUseCase = getCompositionRoot().getStoreProductToDatabaseUseCase();

        mViewMvc.registerListener(this);
        mStoreProductToDatabaseUseCase.registerListener(this);

        setContentView(mViewMvc.getRootView());

        AddProductActivity1.this.setTitle("Add Product");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mStoreProductToDatabaseUseCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mStoreProductToDatabaseUseCase.unregisterListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private byte[] readBytes(InputStream inputStream) throws IOException {

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1){
            byteBuffer.write(buffer,0,buffer.length);
        }
        return byteBuffer.toByteArray();
    }

    private String getFileExtension(Uri uri ){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    public void notifyProductEditFetchSuccess(Product product) {
        Toast.makeText(this,"Success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void FetchProductAndStoreToDatabase(Map<String, Object> product) {
        mStoreProductToDatabaseUseCase.StoreProductToDatabase(product);
    }

    @Override
    public void EditProductAndStoreToDatabase(String productId, Map<String, Object> product) {
        mStoreProductToDatabaseUseCase.EditStoreProductFromDatabase(productId,product);
    }

    @Override
    public void StoreProductImageToDatabase(byte[] readFile, String filename) {
        mStoreProductToDatabaseUseCase.StoreProductImageAndNotify(readFile,filename);
    }

    @Override
    public void DeleteProductFromDatabase(String productId) {
        mStoreProductToDatabaseUseCase.DeleteProductFromDatabase(productId);
    }


    @Override
    public void notifyViewAboutProductStoredSuccessFully(DocumentReference result) {
        mViewMvc.notifyProductStoreToDatabase();
    }

    @Override
    public void notifyViewAboutProductStoredFailedWithException(Exception e) {
        Toast.makeText(this, "Error Occurred During Product Stored :"  + e.getLocalizedMessage()
        ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyViewAboutEditStoredProductSuccessFully(Void result) {
        mViewMvc.notifyProductEditStoreSuccessFull( );
    }

    @Override
    public void notifyViewAboutEditStoredProductFailed(Exception e) {
        Toast.makeText(this, "Error Occurred During Product Edit Store :"  + e.getLocalizedMessage()
                ,Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyViewAboutStoreProductImageProcessResult(double progress) {
        mViewMvc.notifyViewAboutProductImageStoreProcess(progress);
    }

    @Override
    public void notifyViewAboutProductDeleteSuccessfully(Task<Void> task) {

        Toast.makeText(this, "Product Deleted :"  + task
                ,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }
}
