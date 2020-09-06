package com.example.shop.screen.auth.signin;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.shop.R;
import com.example.shop.networking.auth.FetchCredentialUseCase;
import com.example.shop.screen.common.BaseFragment;
import com.example.shop.screen.productlist.ProductListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignInFragment extends BaseFragment implements SignInViewMvc.Listener , FetchCredentialUseCase.Listener {
    private  SignInViewMvc mViewMvc;
    private FetchCredentialUseCase mFetchCredentialUseCase;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewMvc = getCompositionRoot().getViewMvcFactory().getSignInViewMvc(null);
        mFetchCredentialUseCase = getCompositionRoot().getFetchCredentialUseCase();

        mViewMvc.registerListener(this);

        return mViewMvc.getRootView();
    }


    @Override
    public void onStop() {
        mViewMvc.unregisterListener(this);
        mFetchCredentialUseCase.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onStart() {
        mViewMvc.registerListener(this);
        mFetchCredentialUseCase.registerListener(this);
        super.onStart();
    }

    @Override
    public void SignInWithEmailAndPass(String email, String pass) {
        mFetchCredentialUseCase.SignInWithEmailAndPassWord(email, pass);
    }

    @Override
    public void notifyUserCredential(FirebaseUser user) {

    }

    @Override
    public void notifyUserCredentialFailed(Exception exception) {

    }

    @Override
    public void notifyLoginWithEmailSuccess(AuthResult result) {

    }

    @Override
    public void notifyLoginWithEmailFailed(Exception exception) {

    }

    @Override
    public void notifySignInWithEmailSuccess(AuthResult result) {
        Toast.makeText(getContext(),"User Successfully Created.",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity().getApplicationContext(), ProductListActivity.class);
        startActivity(intent);
    }

    @Override
    public void notifySignInWithEmailFailed(Exception exception) {
        Toast.makeText(getContext(),"Error Happened: " + exception.getLocalizedMessage(),Toast.LENGTH_LONG).show();
    }
}
