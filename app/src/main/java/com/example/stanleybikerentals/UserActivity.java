package com.example.stanleybikerentals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {
    
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    
        UserActivityViewModel userActivityViewModel = new ViewModelProvider(this).get(UserActivityViewModel.class);
        
        auth = FirebaseAuth.getInstance();
        Button signOutButton = findViewById(R.id.sign_out);
        TextView userGreetingText = findViewById(R.id.text_user);
        
        authListener = firebaseAuth -> {

            FirebaseUser user = firebaseAuth.getCurrentUser();
            
            if(user == null) {
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
//                }
            }
        };
        
        signOutButton.setOnClickListener(v -> signOut());
    }
    
        public void signOut() {
            auth.signOut();
        }
    
    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        auth.addAuthStateListener(authListener);
    }
}