package com.example.stanleybikerentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private EditText signupInputEmail;
    private EditText signupInputPassword;
    private TextInputLayout signupInputLayoutEmail;
    private TextInputLayout signupInputLayoutPassword;
    private static final String TAG = "FIREBASE AUTHENTICATION LOG";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    
        auth = FirebaseAuth.getInstance();
    
        signupInputLayoutEmail = findViewById(R.id.signup_input_layout_email);
        signupInputLayoutPassword = findViewById(R.id.signup_input_layout_password);
        
        progressBar = findViewById(R.id.progressBar);
        
        signupInputEmail = findViewById(R.id.signup_input_email);
        signupInputPassword = findViewById(R.id.signup_input_password);
    
        Button btnSignUp = findViewById(R.id.btn_signup);
        Button btnLinkToLogIn = findViewById(R.id.btn_link_login);
        
        btnLinkToLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
    
        });
        
        btnSignUp.setOnClickListener(v -> submitForm());
    }
    
    public void submitForm() {
        String email = signupInputEmail.getText().toString().trim();
        String password = signupInputPassword.getText().toString().trim();
        
        if(!checkEmail()) {
            return;
        }
        
        if(!checkPassword()) {
            return;
        }
        
        signupInputLayoutEmail.setErrorEnabled(false);
        signupInputLayoutPassword.setErrorEnabled(false);
        
        progressBar.setVisibility(View.VISIBLE);
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(SignupActivity.this,
            task -> {
            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
            
            progressBar.setVisibility(View.GONE);
            
            if(task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();

                if(user == null) {
                    Log.d(TAG, "No current user.");
                } else {
                    auth.getCurrentUser().sendEmailVerification();
                    Toast.makeText(getApplicationContext(),
                    "Verification email sent",
                    Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }

                finish();
            } else {
                Log.d(TAG, "Authentication failed." + task.getException());
            }
            
            });
    
        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_SHORT).show();
    }
    
    private boolean checkEmail() {
        
        String email = signupInputEmail.getText().toString().trim();
        
        if (email.isEmpty() || !isEmailValid(email)) {
            
            signupInputLayoutEmail.setErrorEnabled(true);
            signupInputLayoutEmail.setError(getString(R.string.err_msg_email));
            signupInputEmail.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputEmail);
            
            return false;
            
        }
        
        signupInputLayoutEmail.setErrorEnabled(false);
        
        return true;
        
    }
    
    private boolean checkPassword() {
        
        String password = signupInputPassword.getText().toString().trim();
        
        if (password.isEmpty() || !isPasswordValid(password)) {
            
            signupInputLayoutPassword.setError(getString(R.string.err_msg_password));
            
            signupInputPassword.setError(getString(R.string.err_msg_required));
            
            requestFocus(signupInputPassword);
            
            return false;
            
        }
        
        signupInputLayoutPassword.setErrorEnabled(false);
        
        return true;
        
    }
    
    private static boolean isEmailValid(String email) {
        
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        
    }
    
    private static boolean isPasswordValid(String password) {
        
        return (password.length() >= 6);
        
    }
    
    private void requestFocus(View view) {
        
        if (view.requestFocus()) {
            
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            
        }
        
    }
    
    @Override
    
    protected void onResume() {
        
        super.onResume();
        
        progressBar.setVisibility(View.GONE);
        
    }
}