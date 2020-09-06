package com.example.shop.screen.productlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.shop.R;
import com.example.shop.networking.product.FetchProduct;
import com.example.shop.screen.addproduct.addproduct.AddProductActivity;
import com.example.shop.screen.auth.AuthActivity;
import com.example.shop.screen.common.BaseActivity;
import com.example.shop.screen.common.nav_drawer.DrawerItems;
import com.example.shop.screen.model.Product;
import com.example.shop.screen.shoppingcart.ShoppingCartActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProductListActivity extends BaseActivity implements
        ProductlistViewMvc.Listener,FetchProduct.Listener {

  private ProductlistViewMvc mViewMvc;
  private FetchProduct mFetchProduct;
  private DrawerLayout drawerLayout;

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mViewMvc = getCompositionRoot().getViewMvcFactory()
            .getProductListViewMvc(null);
    mFetchProduct = getCompositionRoot().getFetchProduct();

    setContentView(mViewMvc.getRootView());
    mViewMvc.registerListener(this);
    mFetchProduct.FetchProductAndNotify(this);
    Toolbar toolbar = findViewById(R.id.main_toolbar);
    setSupportActionBar(toolbar);

    // Side Bar
    drawerLayout = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.navigation_view);

    ActionBarDrawerToggle toggle =
            new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    R.string.open_drawer,R.string.close_drawer);

    navigationView.bringToFront();
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();
  }


  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_menu,menu);


      if (getCompositionRoot().getUserDetails() != null) {
          MenuItem item = menu.findItem(R.id.LogoutUser);
          item.setVisible(true);
          MenuItem item1 = menu.findItem(R.id.RedirectToLogin);
          item1.setVisible(false);
          MenuItem item2 = menu.findItem(R.id.cart);
          item2.setVisible(true);
      }


    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    if(item.getItemId() == R.id.RedirectToLogin){
      Intent intent = new Intent(this, AuthActivity.class);
      startActivity(intent);
    }else if(item.getItemId() == R.id.cart){
      Intent intent = new Intent(this, ShoppingCartActivity.class);
      startActivity(intent);
    }else if(item.getItemId() == android.R.id.home){
      //When back button Hit
    }else if(item.getItemId() == R.id.help){
      Toast.makeText(getApplicationContext(),"Not implemented.",Toast.LENGTH_LONG).show();
    }else if(item.getItemId() == R.id.LogoutUser){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getApplicationContext(),"Log Out.",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ProductListActivity.class);
        startActivity(intent);
    }else if(item.getItemId() == R.id.Exit){
        finish();
    }

    return super.onOptionsItemSelected(item);
  }


  @Override
  public void onBackPressed() {
    //Navigation Drawer
    if(drawerLayout.isDrawerOpen(GravityCompat.START)){
      drawerLayout.closeDrawer(GravityCompat.START);
      return;
    }

    super.onBackPressed();

  }

  @Override
  protected void onResume() {

    super.onResume();
  }

  @Override
  protected void onStart() {
    super.onStart();
    mFetchProduct.registerListener(this);
    mViewMvc.registerListener(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    mFetchProduct.unregisterListener(this);
    mViewMvc.unregisterListener(this);
  }


  @Override
  public void onProductClicked(Product product) {
//    Toast.makeText(this, "product clicked", Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDrawerItemClicked(DrawerItems item) {
    switch (item){
      case  ADD_PRODUCT:
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
        break;
      case  LOCATION:
        Toast.makeText(getApplicationContext(),"Location",Toast.LENGTH_LONG).show();
        break;
    }
  }

  @Override //return method from fetch product class
  public void onProductFetched(List<Product> products) {
    mViewMvc.bindProducts(products);
  }

  @Override
  public void onProductFetchFailed() {
    Toast.makeText(this, "Fetching Product Failed", Toast.LENGTH_SHORT).show();
  }


}

