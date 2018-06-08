package com.example.chizkiyahuandchaskyh.takeandgo2.controller.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;


public class BasicLoginActivity extends AppCompatActivity {

    LoginFragment loginFragment = null;
    RegistrFragment registrFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (loginFragment == null){
            loginFragment = new LoginFragment();
        }
        this.changeFragement(loginFragment);

    }

    public  void changeFragement(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_login_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


    void onClickSignIn(View view){
        //The implementation of the code is in LoginFragment
    }
    void onClickRegister(View view){
        //The implementation of the code is in LoginFragment
    }

    public void onClickRegisterButton(View view) {
        if(registrFragment == null){
            registrFragment = new RegistrFragment();
        }
        changeFragement(registrFragment);
    }

    public void onClickLogin(View view) {
        if(loginFragment == null){
            loginFragment = new LoginFragment();
        }
        changeFragement(loginFragment);
    }



}
