package com.example.shop.screen.productlist;

import android.content.Intent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.model.Product;
import com.example.shop.screen.productdetails.ProductDetailsActivity;
import com.example.shop.screen.productlist.productlistitem.ProductListItemViewMvc;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>
implements ProductListItemViewMvc.Listener{

    public interface Listener {
        void onProductClicked(Product product);
    }
    private List<Product> products;
    private ProductListItemViewMvc viewMvc;
    private final Listener mListener;
    private ViewMvcFactory mViewMvcFactory;

    public ProductListAdapter(List<Product> products, Listener mListener, ViewMvcFactory mViewMvcFactory) {
        this.products = products;
        this.mListener = mListener;
        this.mViewMvcFactory = mViewMvcFactory;
        notifyDataSetChanged();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewMvc = mViewMvcFactory.getProductListItemViewMvc(parent);
        viewMvc.registerListener(this);
        return new ProductViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder viewHolder, int position) {
        viewHolder.mViewMvc.bindProduct(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public void onProductClicked(Product product) {
        mListener.onProductClicked(product);
        Intent intent = new Intent(viewMvc.getRootView().getContext(), ProductDetailsActivity.class);
        intent.putExtra("item", product);
        viewMvc.getRootView().getContext().startActivity(intent);
    }


    static class ProductViewHolder extends RecyclerView.ViewHolder{
        private final ProductListItemViewMvc mViewMvc;

        public ProductViewHolder(ProductListItemViewMvc viewMvc){
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }
    }

}

