package com.example.shop.screen.common;

import androidx.fragment.app.Fragment;

import com.example.shop.common.CustomApplication;
import com.example.shop.common.dependencyinjection.ControllerCompositionRoot;

public class BaseFragment extends Fragment {
    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot(){
        if(mControllerCompositionRoot == null){
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) requireActivity().getApplication()).getCompositionRoot(),
                    requireActivity()
            );
        }

        return mControllerCompositionRoot;
    }
}
