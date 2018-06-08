package com.example.chizkiyahuandchaskyh.takeandgo2.controller.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.MainActivity;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.BackendFactory;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.beckend.DataSource;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Customer;

public class TryUserPass extends AsyncTask<String,Void,Customer> {
    DataSource dataSource = BackendFactory.getDataSource();
    View view;

    public TryUserPass(View view) {
        this.view = view;
    }

    @Override
    protected Customer doInBackground(String... strings) {
        try {
            return dataSource.tryUserPass(strings[0], strings[1]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Customer customer) {
        SharedPreferences prefs = view.getContext().getSharedPreferences("UserData", view.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if(customer != null){
            editor.putBoolean("isLogon", true);
            editor.putInt("failedLogin", 0);
            editor.putInt("customerId", customer.getId());
            editor.commit();

            view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), MainActivity.class));
            ((Activity) view.getContext()).finish();
        }
        else {

            Toast.makeText(view.getContext().getApplicationContext(), R.string.error_user_pass , Toast.LENGTH_LONG).show();
        }
        editor.putInt("failedLogin", prefs.getInt("failedLogin",0 ) + 1);
        editor.commit();
    }


}