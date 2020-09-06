package com.example.shop.screen.productlist;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.nav_drawer.BaseNavDrawerViewMvc;
import com.example.shop.screen.common.nav_drawer.DrawerItems;
import com.example.shop.screen.model.Product;

import java.util.List;

//ui logic
public class ProductlistViewMvcImpl extends BaseNavDrawerViewMvc<ProductlistViewMvc.Listener>
        implements ProductlistViewMvc,ProductListAdapter.Listener{
    private final ViewMvcFactory mViewMvcFactory;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ProductlistViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        super(inflater,parent);
        setRootView(inflater.inflate(R.layout.product_main,parent,false));
        this.mViewMvcFactory = viewMvcFactory;
        recyclerView = findViewById(R.id.recycleViewForProductView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(
                new GridLayoutManager(getContext(),
                        getContext().getResources().getInteger(R.integer.product_column_2)));
    }

    @Override //onFetchProduct from ProductListActivity
    public void bindProducts(List<Product> products) {
        productListAdapter = new ProductListAdapter(products, this,mViewMvcFactory);
        recyclerView.setAdapter(productListAdapter);

    }

    @Override
    public void onProductClicked(Product product) {
        for (Listener listener : getListeners()) {
            listener.onProductClicked(product);
        }
    }

    @Override
    protected void onDrawerItemsClicked(DrawerItems item) {
        for(Listener listener : getListeners()){
            listener.onDrawerItemClicked(item);
        }
    }
}
