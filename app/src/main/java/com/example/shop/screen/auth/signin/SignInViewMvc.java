package com.example.shop.screen.auth.signin;

import com.example.shop.screen.common.views.ObservableViewMvc;

public interface SignInViewMvc extends ObservableViewMvc<SignInViewMvc.Listener> {
    interface Listener {
        void SignInWithEmailAndPass(String email,String pass);
    }
}
