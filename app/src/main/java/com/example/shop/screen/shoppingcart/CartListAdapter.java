package com.example.shop.screen.shoppingcart;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.model.Cart;
import com.example.shop.screen.model.Product;
import com.example.shop.screen.shoppingcart.shoppingcartitem.ShoppingCartItemViewMvc;

import java.util.List;
import java.util.Map;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder>
implements ShoppingCartItemViewMvc.Listener{
    private Context mContext;

    interface Listener {
        void onProductQtyIncrease(Cart cart, Map<String,Object> productInCart);
        void onProductQtyDecrease(Cart cart, Map<String,Object> productInCart);

        //action
        void onProductRemoveFromCart(Cart cart);
    }

    private List<Cart> carts;
    private ShoppingCartItemViewMvc viewMvc;
    private ViewMvcFactory mViewMvcFactory;
    private final Listener mListener;


    public CartListAdapter(List<Cart> cart,Listener listener,
                           ViewMvcFactory viewMvcFactory) {
        this.carts = cart;
        this.mListener = listener;
        this.mViewMvcFactory = viewMvcFactory;
    }


    public void setCart(List<Cart> cart) {
        this.carts = cart;

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewMvc = mViewMvcFactory.getShoppingCartItemViewMvc(parent);
        viewMvc.registerListener(this);
        return new CartViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder viewholder, int position) {
        viewholder.mViewMvc.bindCart(carts.get(position));
    }


    @Override
    public int getItemCount() {
        return carts.size();
    }



    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mContext = recyclerView.getContext();
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onProductQtyIncrease(Cart cart, Map<String, Object> productInCart) {
        mListener.onProductQtyIncrease(cart,productInCart);
    }

    @Override
    public void onProductQtyDecrease(Cart cart, Map<String, Object> productInCart) {
        mListener.onProductQtyDecrease(cart,productInCart);
    }

    @Override
    public void onProductRemoveFromCart(Cart cart) {
        mListener.onProductRemoveFromCart(cart);
    }



    static class CartViewHolder extends RecyclerView.ViewHolder {
        private final ShoppingCartItemViewMvc mViewMvc;
        public CartViewHolder(ShoppingCartItemViewMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }

    }


}
