package com.example.chizkiyahuandchaskyh.takeandgo2.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.utils.Constants;


public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    DataSource dataSource = BackendFactory.getDataSource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

    }

    void onClickSignIn(View view){

            String user = email.getText().toString();
            String pass =  password.getText().toString();

            new TryUserPass().execute(user, pass);

    }

    void onClickRegister(View view){

        String user = email.getText().toString();
        String pass =  password.getText().toString();
        try {
            if (!isEmailValid(user)){
                throw new Exception("Please enter a valid email address");
            }
            if(!isPassStrong(pass)){
                throw new Exception("Password is too short Please enter at least 8 characters");
            }
            new CheckUserIsFree().execute(user, pass);
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
            Log.e(Constants.Log.TAG,e.getMessage());
        }
    }

    private class TryUserPass extends AsyncTask<String,Void,Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                return dataSource.tryUserPass(strings[0], strings[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            if(aBoolean){
                editor.putBoolean("isLogon", true);
                editor.putInt("failedLogin", 0);
                editor.commit();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else {

                Toast.makeText(getApplicationContext(), R.string.error_user_pass , Toast.LENGTH_LONG).show();
            }
            editor.putInt("failedLogin", prefs.getInt("failedLogin",0 ) + 1);
        }
    }

    private class CheckUserIsFree extends AsyncTask<String, String, Void> {


        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                if(dataSource.checkUserIsFree(strings[0])){
                    dataSource.addUserPass(strings[0], strings[1]);
                    new TryUserPass().execute(strings[0], strings[1]);
                }else {
                    publishProgress(getString(R.string.error_the_user_already_exsit));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isPassStrong(String pass) {
        if (pass.length() < 8){
            return  false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }



}
