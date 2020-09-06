package com.example.shop.common.dependencyinjection;

import androidx.fragment.app.FragmentActivity;

public class ActivityCompositionRoot {
    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;



    public ActivityCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    public FragmentActivity getActivity() {
        return mActivity;
    }


}
