package com.example.shop.common.dependencyinjection;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CompositionRoot {
    public void getStackoverflowApi() {

    }

    private void getRetrofit(){

    }

    public FirebaseUser getUserDetails() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }


}
