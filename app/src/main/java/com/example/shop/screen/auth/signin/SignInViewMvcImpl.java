package com.example.shop.screen.auth.signin;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import com.example.shop.R;
import com.example.shop.screen.common.ViewMvcFactory;
import com.example.shop.screen.common.views.BaseObservableViewMvc;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import javax.annotation.Nullable;

public class SignInViewMvcImpl extends BaseObservableViewMvc<SignInViewMvc.Listener> implements SignInViewMvc {

    private TextInputEditText userName,passWord,confirmPassword;
    private TextInputLayout userNameLayout,passwordLayout,confirmPasswordLayout;
    private Button signUpBtn;
    private final ViewMvcFactory mViewMvcFactory;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SignInViewMvcImpl(LayoutInflater inflater, @Nullable final ViewGroup parent, ViewMvcFactory viewMvcFactory)
    {

        this.mViewMvcFactory  = viewMvcFactory;
        setRootView(inflater.inflate(R.layout.fragment_sign_in,parent,false));

        userName = findViewById(R.id.EmailFieldForSignup);
        passWord = findViewById(R.id.PasswordFieldForSignup);
        confirmPassword = findViewById(R.id.ConfirmPasswordFieldForSignup);
        userNameLayout = findViewById(R.id.EmailLayoutForSignup);
        passwordLayout = findViewById(R.id.PasswordLayoutForSignup);
        confirmPasswordLayout = findViewById(R.id.ConfirmPasswordLayoutForSignup);
        signUpBtn = findViewById(R.id.SignUpBtn);


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = userName.getText().toString();
                String password = passWord.getText().toString();

                if(validate()){
                   for(Listener listener :getListeners()){
                        listener.SignInWithEmailAndPass(username,password);
                    }
                }
            }
        });
    }


    private boolean validate() {
        if(userNameLayout.getEditText().getText().toString().trim().isEmpty()){
            userNameLayout.setError("Email Field Cannot Be Empty.");
            return false;
        }else{
            userNameLayout.setError(null);
            userNameLayout.setErrorEnabled(false);
        }
        if(passwordLayout.getEditText().getText().toString().trim().isEmpty()){
            passwordLayout.setError("Password Field Cannot Be Empty.");
            return false;
        }else{
            passwordLayout.setError(null);
            passwordLayout.setErrorEnabled(false);
        }
        if(confirmPasswordLayout.getEditText().getText().toString().trim().isEmpty()){
            confirmPasswordLayout.setError("Confirm Password Field Cannot Be Empty.");
            return false;
        }else{
            confirmPasswordLayout.setError(null);
            confirmPasswordLayout.setErrorEnabled(false);
        }
        if(!passwordLayout.getEditText().getText().toString()
                .equals(confirmPasswordLayout.getEditText().getText().toString())){
            passwordLayout.setError("Password and Confirm Password not Matched.");
            return false;
        }else{
            passwordLayout.setError(null);
            passwordLayout.setErrorEnabled(false);
        }

        return true;
    }


}
