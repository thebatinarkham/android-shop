package com.example.shop.screen.common;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shop.common.CustomApplication;
import com.example.shop.common.dependencyinjection.ActivityCompositionRoot;
import com.example.shop.common.dependencyinjection.ControllerCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private ActivityCompositionRoot mActivityCompositionRoot;
    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot(){
        if(mControllerCompositionRoot == null){
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this
            );
        }

        return mControllerCompositionRoot;
    }

    public ActivityCompositionRoot getActivityCompositionRoot() {
        if (mActivityCompositionRoot == null) {
            mActivityCompositionRoot = new ActivityCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return mActivityCompositionRoot;
    }
}
