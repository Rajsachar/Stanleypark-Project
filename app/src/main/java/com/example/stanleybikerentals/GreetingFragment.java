package com.example.stanleybikerentals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class GreetingFragment extends Fragment {
    
    GreetingFragment(){}
    
    private FirebaseAuth firebaseAuth;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_greeting, container, false);
        
        FirebaseAuth auth = FirebaseAuth.getInstance();
    
        FirebaseUser user = firebaseAuth.getCurrentUser();
        
        TextView userGreetingText = rootView.findViewById(R.id.text_user);
        userGreetingText.setText("Hello " + Objects.requireNonNull(user).getEmail());
        return rootView;
    
    }}
