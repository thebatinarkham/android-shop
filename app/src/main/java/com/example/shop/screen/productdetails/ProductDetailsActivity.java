package com.example.shop.screen.productdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.shop.R;
import com.example.shop.networking.product.productdetails.FetchProductDetailsUseCase;
import com.example.shop.screen.addproduct.addproduct.AddProductActivity;
import com.example.shop.screen.common.BaseActivity;
import com.example.shop.screen.model.Product;
import com.example.shop.screen.productlist.ProductListActivity;

import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends BaseActivity implements ProductDetailsViewMvc.Listener,
        FetchProductDetailsUseCase.Listener{

    private ProductDetailsViewMvc mViewMvc;
    private FetchProductDetailsUseCase mFetchProductDetailsUseCase;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getProductDetailsViewMvc(null);
        mFetchProductDetailsUseCase = getCompositionRoot().getFetchProductDetailsUseCase();
        setContentView(mViewMvc.getRootView());

        Toolbar toolbar = findViewById(R.id.productDetailsMenu_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Product Details");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.productdetails_menu,menu);
        if (getCompositionRoot().getUserDetails() != null) {
            MenuItem item = menu.findItem(R.id.productEditMenu);
            item.setVisible(true);

        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.productEditMenu){
            Intent intent = new Intent(this, AddProductActivity.class);
            intent.putExtra("item", product);
            startActivity(intent);
        }else if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
        mFetchProductDetailsUseCase.registerListener(this);
        mFetchProductDetailsUseCase.FetchProductDetailsAndNotify(this);
        mFetchProductDetailsUseCase.FetchProductCardAdapterAndNotify(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        //for not causing memory leaks
        mViewMvc.unregisterListener(this);

    }

    @Override
    public void onNavigateUpClicked() {

    }


    @Override
    public void onAddProductToCart(Map<String, Object> products, Product product) {
        mFetchProductDetailsUseCase.checkForProductInCartThenAddAndNotify(products,product,this);
    }



    @Override
    public void onProductCardAdapterFetched(List<Product> products) {
        mViewMvc.bindProductCardAdapter(products);
    }


    @Override
    public void onProductDetailsFetched(Product product) {
        this.product = product;
        mViewMvc.bindProduct(product);
    }

    @Override
    public void oProductDetailsFetchedFailed() {
        Toast.makeText(this, "Product Details Fetch Failed", Toast.LENGTH_SHORT).show();
    }

}
