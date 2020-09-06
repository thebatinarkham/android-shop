package com.example.shop.screen.auth;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.shop.R;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.google.android.material.tabs.TabLayout;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class AuthViewMvcImpl extends BaseObservableViewMvc<AuthViewMvc.Listener> implements AuthViewMvc{



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AuthViewMvcImpl(LayoutInflater mLayoutInflater, FragmentManager fragmentManager, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
//        super(mLayoutInflater,parent);
        setRootView(mLayoutInflater.inflate(R.layout.activity_auth,parent,false));


        PagerAdapter pagerAdapter = new PagerAdapter(
        fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,getContext());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }




}
