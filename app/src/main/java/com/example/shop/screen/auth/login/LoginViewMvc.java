package com.example.shop.screen.auth.login;

import android.app.Activity;

import com.example.shop.screen.common.views.ObservableViewMvc;

public interface LoginViewMvc extends ObservableViewMvc<LoginViewMvc.Listener> {
    interface Listener {
        void onLoginWithGoogle();
        void onLoginInWithCredential(String userName,String pass);
    }

    void notifyLoginSuccess();
}
