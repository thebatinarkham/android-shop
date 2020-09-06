package com.example.shop.screen.shoppingcart;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.shop.networking.cart.FetchShoppingCartDetailsCase;
import com.example.shop.screen.auth.AuthActivity;
import com.example.shop.screen.common.BaseActivity;
import com.example.shop.screen.productlist.ProductListActivity;
import com.example.shop.screen.model.Cart;

import java.util.List;
import java.util.Map;


public class ShoppingCartActivity extends BaseActivity implements
        ShoppingCartViewMvc.Listener, FetchShoppingCartDetailsCase.Listener {
    private FetchShoppingCartDetailsCase mFetchShoppingCartDetailsCase;
    private ShoppingCartViewMvc mViewMvc;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getShoppingCartDetailsViewMvc(null);
        mFetchShoppingCartDetailsCase = getCompositionRoot().getFetchShoppingCartDetailsUseCase();

        mViewMvc.registerListener(this);
        setContentView(mViewMvc.getRootView());

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getCompositionRoot().getUserDetails() == null){
            Intent intent = new Intent(this, AuthActivity.class);
            startActivity(intent);
            Toast.makeText(this,"Auth Require.",Toast.LENGTH_LONG).show();
            return;
        }
        mViewMvc.registerListener(this);
        mFetchShoppingCartDetailsCase.FetchCartAndNotify(this);
        mFetchShoppingCartDetailsCase.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCartFetch(List<Cart> cart) {
        mViewMvc.bindCarts(cart);
    }

    @Override
    public void onProductRemoveFromCart() {
        mViewMvc.notifyViewAboutProductRemove();
    }


    @Override
    public void onProductQtyIncreaseSuccess() {
        mViewMvc.notifyProductQtyIncrease();
    }

    @Override
    public void onProductQtyIncreaseFailed(Exception e) {
        Toast.makeText(this, "ProductQty Decrease Failed" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductQtyDecreaseSuccess() {
        mViewMvc.notifyProductQtyDecrease();

    }

    @Override
    public void onProductQtyDecreaseFailed(Exception e) {
        Toast.makeText(this, "ProductQty Decrease Failed" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void removeProductFromCart(Cart cart) {
        mFetchShoppingCartDetailsCase.RemoveProductFromCart(cart,this);
    }

    @Override
    public void increaseProductInCart(Cart cart, Map<String, Object> productInCart) {
        mFetchShoppingCartDetailsCase.IncreaseProductQtyInCart(cart,productInCart);
    }

    @Override
    public void decreaseProductInCart(Cart cart, Map<String, Object> productInCart) {
        mFetchShoppingCartDetailsCase.DecreaseProductQtyInCart(cart,productInCart);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



