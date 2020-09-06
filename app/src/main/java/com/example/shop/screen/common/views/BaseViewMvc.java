package com.example.shop.screen.common.views;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.StringRes;

public abstract class BaseViewMvc implements ViewMvc {
    private View mRootView;

    @Override
    public View getRootView() {
        return mRootView;
    }

    protected void setRootView(View mRootView) {
        this.mRootView = mRootView;
    }


    protected  <T extends  View> T findViewById(int id) {
        return getRootView().findViewById(id);

    }

    protected Context getContext() {
        return getRootView().getContext();
    }

    protected String getString(@StringRes int id) {
        return getContext().getString(id);
    }

    protected Activity getActivity(){
        return (Activity) getContext();
    }


}
