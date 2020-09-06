package com.example.shop.screen.common.nav_drawer;

import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.shop.R;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.annotation.Nullable;

public abstract class BaseNavDrawerViewMvc<ListenerType> extends BaseObservableViewMvc<ListenerType> {

    private final DrawerLayout mDrawerLayout;
    private final NavigationView mNavigationView;
    private final FrameLayout mFrameLayout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BaseNavDrawerViewMvc(LayoutInflater inflater, @Nullable ViewGroup parent) {
        super.setRootView(inflater.inflate(R.layout.product_list,parent,false));
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigation_view);
        mFrameLayout = findViewById(R.id.frame_content);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        TextView UserName = header.findViewById(R.id.userName);
        TextView UserEmail = header.findViewById(R.id.userEmail);
        ImageView UserImage = header.findViewById(R.id.userImage);
        //Search
        AppCompatEditText searchResult = findViewById(R.id.app_bar_search);
        TextInputLayout searchLayout = findViewById(R.id.app_bar_search);

        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            UserName.setText(name);
            UserEmail.setText(email);
            Glide.with(getContext()).load(photoUrl).into(UserImage);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.addProduct:
                        onDrawerItemsClicked(DrawerItems.ADD_PRODUCT);
                        break;
                    case  R.id.location:
                        onDrawerItemsClicked(DrawerItems.LOCATION);
                        break;
                }
                mDrawerLayout.closeDrawers();
                return false;

            }
        });
    }

    protected  void SetTitle(String title){
        getActivity().getActionBar().setTitle(title);
    }
    protected abstract void onDrawerItemsClicked(DrawerItems item);

    @Override
    protected void setRootView(View view) {
        mFrameLayout.addView(view);
    }

}
