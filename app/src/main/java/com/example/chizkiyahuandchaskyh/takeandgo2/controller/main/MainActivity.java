package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.example.chizkiyahuandchaskyh.takeandgo2.R;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.Login.BasicLoginActivity;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.BranchesFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.ContactUsFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.MyOrdersFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ResponseReceiver receiver;

    public void onClickBack(View view) {
    }


    public class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int test = 5;
        }
    }


    BranchesFragment branchesFragment = null;
    ContactUsFragment contactUsFragment = null;
    MyOrdersFragment myOrdersFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLogin();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        final Button logOutButton = header.findViewById(R.id.log_out_button);
        if (logOutButton != null) {
            logOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickLoguot(null);
                }
            });
        }

        IntentFilter filter = new IntentFilter("NEW_CAR_IS_FREE");
        receiver = new ResponseReceiver();
        registerReceiver(receiver, filter);


    }

    void checkLogin(){

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        if(! prefs.getBoolean("isLogon", false)){
            startActivity(new Intent( MainActivity.this, BasicLoginActivity.class));

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.contact_us) {
            if (contactUsFragment == null){
                contactUsFragment = new ContactUsFragment();
            }
            changeFragement(contactUsFragment);

        } else if (id == R.id.branches) {
            if (branchesFragment == null){
                branchesFragment = new BranchesFragment();
            }
            changeFragement(branchesFragment);

        } else if (id == R.id.available_cars) {

        }
        else if (id == R.id.my_orders) {
            if (myOrdersFragment == null){
                myOrdersFragment = new MyOrdersFragment();
            }
            changeFragement(myOrdersFragment);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickLoguot(MenuItem item) {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isLogon", false);
        editor.putInt("failedLogin", 0);
        editor.putInt("customerId", 0);
        editor.commit();
        checkLogin();
    }

    public  void changeFragement(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frgament_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    public void onClickOrder(View view) {
        //The implementation of the code is in MyOrdersFragment
    }
}