package com.example.shop.screen.auth;

import android.app.Activity;
import android.content.Context;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.shop.R;
import com.example.shop.screen.auth.login.LoginFragment;
import com.example.shop.screen.auth.signin.SignInFragment;
import com.example.shop.screen.common.ViewMvcFactory;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class PagerAdapter extends FragmentPagerAdapter {




    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.login, R.string.sign_In};
    private final Context mContext;

    private ViewMvcFactory mViewMvcFactory;


    public PagerAdapter(@NonNull FragmentManager fm, int behavior, Context mContext) {
        super(fm, behavior);
        this.mContext = mContext;


    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a defined as a static inner class below).
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new LoginFragment();
                break;
            case 1:
                fragment = new SignInFragment();
                break;

        }

        return  fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }



}
