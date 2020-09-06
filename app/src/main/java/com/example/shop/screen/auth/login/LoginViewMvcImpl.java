package com.example.shop.screen.auth.login;

import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;

import com.example.shop.R;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import javax.annotation.Nullable;

public class LoginViewMvcImpl extends BaseObservableViewMvc<LoginViewMvc.Listener> implements LoginViewMvc
        {
    private final ViewMvcFactory mViewMvcFactory;
    private TextInputEditText userName;
    private TextInputEditText passWord;
    private TextInputLayout userNameLayout;
    private TextInputLayout passWordLayout;
    private LinearLayout LoginPage;
    private FirebaseAuth auth;
    private  FirebaseAuth.AuthStateListener authStateListener;
    private Button LoginBtn;
    private SignInButton LoginWithGoogleBtn;
    private Button LoginWithFbBtn;
    private final int RC_SIGN_IN = 1;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LoginViewMvcImpl(LayoutInflater inflater, @Nullable ViewGroup parent, ViewMvcFactory mViewMvcFactory) {
        this.mViewMvcFactory = mViewMvcFactory;
        setRootView(inflater.inflate(R.layout.fragment_login,parent,false));

        userName = findViewById(R.id.loginWith_Email);
        passWord = findViewById(R.id.loginWith_Password);
        userNameLayout = findViewById(R.id.userName_TextInputLayout);
        passWordLayout = findViewById(R.id.passWord_TextInputLayout);
        LoginBtn = findViewById(R.id.loginBtn);
        LoginWithGoogleBtn = findViewById(R.id.loginWithGoogle);
        LoginPage = findViewById(R.id.loginPageLayout);
        LoginWithFbBtn = findViewById(R.id.loginWithFb);
        auth = FirebaseAuth.getInstance();

        LoginWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Listener listener :getListeners()){
                    listener.onLoginWithGoogle();
                }

            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email =  userName.getText().toString();
                String Pass = passWord.getText().toString();
                if(!(TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass))) {
                    for(Listener listener :getListeners()){
                        listener.onLoginInWithCredential(Email,Pass);
                    }
                }

            }
        });


    }


        @Override
        public void notifyLoginSuccess() {

        }
}
