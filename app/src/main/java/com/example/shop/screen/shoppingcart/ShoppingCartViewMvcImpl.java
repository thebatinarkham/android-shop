package com.example.shop.screen.shoppingcart;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.example.shop.screen.model.Cart;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class ShoppingCartViewMvcImpl extends BaseObservableViewMvc<ShoppingCartViewMvc.Listener>
        implements ShoppingCartViewMvc ,CartListAdapter.Listener{

    private final RecyclerView recyclerView;
    private CartListAdapter cartListAdapter;
    private TextView TotalProductItem,TotalProductPrice;
    private final ViewMvcFactory mViewMvcFactory;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShoppingCartViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent, ViewMvcFactory mViewMvcFactory) {

        setRootView(inflater.inflate(R.layout.activity_shopping_cart,parent,false));
        this.mViewMvcFactory = mViewMvcFactory;
        recyclerView = findViewById(R.id.recyclerViewForShoppingCart);
        recyclerView.setHasFixedSize(true);

        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,0);
        TotalProductItem = findViewById(R.id.totalCartItem);
        TotalProductPrice = findViewById(R.id.totalProductPrice);

    }

    @Override
    public void bindCarts(List<Cart> cart) {
        int totalPrice = 0;
        int totalQty = 0;
        for(Cart cart1 : cart){
            totalPrice +=cart1.getProductPrice();
            totalQty += cart1.getProductQty();

        }
        TotalProductItem.setText("You Have " +  String.valueOf(totalQty) + " in Your Cart");
        TotalProductPrice.setText(String.valueOf("\u20B9" +  totalPrice));

        cartListAdapter = new CartListAdapter(
                cart,ShoppingCartViewMvcImpl.this ,mViewMvcFactory);
        recyclerView.setAdapter(cartListAdapter);
        cartListAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifyViewAboutProductRemove() {
        Toast.makeText(getContext(), "Product Removed from Cart.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyProductQtyIncrease() {
//        Toast.makeText(getContext(), "Product Qty increase.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyProductQtyDecrease() {
//        Toast.makeText(getContext(), "Product Qty decrease.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onProductQtyIncrease(Cart cart, Map<String, Object> productInCart) {
        for(Listener listener :getListeners()){
            listener.increaseProductInCart(cart,productInCart);
        }

    }

    @Override
    public void onProductQtyDecrease(Cart cart, Map<String, Object> productInCart) {
        for(Listener listener :getListeners()){
            listener.decreaseProductInCart(cart,productInCart);
        }


    }



    @Override
    public void onProductRemoveFromCart(Cart cart) {
        for (Listener listener : getListeners()) {
            listener.removeProductFromCart(cart);
        }

    }
}
