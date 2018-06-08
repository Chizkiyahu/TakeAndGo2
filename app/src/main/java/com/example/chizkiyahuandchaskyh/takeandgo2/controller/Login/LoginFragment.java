package com.example.chizkiyahuandchaskyh.takeandgo2.controller.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.chizkiyahuandchaskyh.takeandgo2.R;


public class LoginFragment extends Fragment {

    EditText email, password;
    Button onClickSignInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        onClickSignInButton = view.findViewById(R.id.email_sign_in_button);
        onClickSignInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String user = email.getText().toString();
                String pass =  password.getText().toString();
                new TryUserPass(getView()).execute(user, pass);
            }
        });
        return view;

    }













}
