package com.example.shop.screen.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.shop.R;
import com.example.shop.networking.auth.FetchCredentialUseCase;
import com.example.shop.screen.common.BaseActivity;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.productlist.ProductListActivity;
import com.example.shop.screen.shoppingcart.ShoppingCartActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;


public class AuthActivity extends BaseActivity implements AuthViewMvc.Listener {


    private AuthViewMvc mViewMvc;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewMvc = getCompositionRoot().getViewMvcFactory().getAuthViewMvc(null,
                getSupportFragmentManager());

        setContentView(mViewMvc.getRootView());


    }



    @Override
    protected void onStart() {
        mViewMvc.registerListener(this);
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        if(getCompositionRoot().getUserDetails() != null){
            Toast.makeText(this, "Already Login as: "
                            + getCompositionRoot().getUserDetails().getDisplayName(),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ProductListActivity.class);
            startActivity(intent);
        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



}
