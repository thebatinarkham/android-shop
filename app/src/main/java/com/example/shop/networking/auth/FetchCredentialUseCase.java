package com.example.shop.networking.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shop.R;
import com.example.shop.common.BaseObservable;
import com.example.shop.screen.productlist.ProductListActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class FetchCredentialUseCase extends BaseObservable<FetchCredentialUseCase.Listener> {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public interface Listener {
        void notifyUserCredential(FirebaseUser user);

        void notifyUserCredentialFailed(Exception exception);

        void notifyLoginWithEmailSuccess(AuthResult result);

        void notifyLoginWithEmailFailed(Exception exception);

        void notifySignInWithEmailSuccess(AuthResult result);

        void notifySignInWithEmailFailed(Exception exception);
    }

    private static final String TAG = "FetchCredentialUseCase";

    public void FetchUserCredential(Activity mActivity, String acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "googleSignIn:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            notifyUserCredentialSuccess(user);
                        } else {
                            notifyUserCredentialFailed(task.getException());
                        }
                                            }
                });
    }

    private void notifyUserCredentialFailed(Exception exception) {
        for(FetchCredentialUseCase.Listener listener:getListeners()){
            listener.notifyUserCredentialFailed(exception);
        }
    }

    private void notifyUserCredentialSuccess(FirebaseUser user) {
        for(FetchCredentialUseCase.Listener listener: getListeners()){
            listener.notifyUserCredential(user);
        }
    }


    public void LoginWithEmailAndPassWord(String email,String pass){
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    notifyLoginWithEmailSuccess(task.getResult());
                }else{
                    notifyLoginWithEmailFailed(task.getException());
                }
            }
        });
    }

    private void notifyLoginWithEmailFailed(Exception exception) {
        for (Listener listener:getListeners()){
            listener.notifyLoginWithEmailFailed(exception);
        }
    }

    private void notifyLoginWithEmailSuccess(AuthResult result) {
        for (Listener listener:getListeners()){
            listener.notifyLoginWithEmailSuccess(result);
        }
    }

    public void SignInWithEmailAndPassWord(String email,String pass){
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            notifySignInWithEmailAndPassWordSuccess(task.getResult());
                            Log.d("User Created", "createUserWithEmail:success");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                notifySignInWithEmailAndPassWordFailed(e);
            }
        });
    }

    private void notifySignInWithEmailAndPassWordFailed(Exception e) {
        for(Listener listener:getListeners()){
            listener.notifySignInWithEmailFailed(e);
        }
    }

    private void notifySignInWithEmailAndPassWordSuccess(AuthResult result) {
        for(Listener listener:getListeners()){
            listener.notifySignInWithEmailSuccess(result);
        }
    }
}
