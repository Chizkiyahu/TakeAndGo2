package com.example.chizkiyahuandchaskyh.takeandgo2.controller.main;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
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
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.AboutUsFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.BranchFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.AvailableCarsFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.BranchesFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.BranchesMapFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.ContactUsFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.controller.main.Fragment.MyOrdersFragment;
import com.example.chizkiyahuandchaskyh.takeandgo2.model.entities.Branch;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ResponseReceiver receiver;
    private MenuItem filterItem;
    public void onClickBack(View view) {
    }



    public class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            createNotification("New Car", "A new car is now available");
        }
    }


    BranchesFragment branchesFragment = null;
    ContactUsFragment contactUsFragment = null;
    MyOrdersFragment myOrdersFragment = null;
    AboutUsFragment aboutUsFragment = null;
    BranchesMapFragment branchesMapFragment = null;
    AvailableCarsFragment availableCarsFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checkLogin();
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

        changeFragement(getMyOrdersFragment());
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

        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        switch (id) {
            case R.id.contact_us:
                fragment = getContactUsFragment();
                break;
            case R.id.branches:
                fragment = getBranchesFragment();
                break;
            case R.id.branches_map:
                fragment = getBranchesMapFragment();
                break;
            case R.id.about_us:
                fragment = getAboutUsFragment();
                break;
            case R.id.my_orders:
                fragment = getMyOrdersFragment();
                break;
                default:
                    break;
        }

        if(fragment != null) {
            changeFragement(fragment);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        setTitle(item.getTitle());
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

    private NotificationManager notifManager;

    public void createNotification(String tital, String message ) {
        final int NOTIFY_ID = 1002;


        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(tital)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(message)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(tital)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);

            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(tital)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(message)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(tital)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }

    private BranchesMapFragment getBranchesMapFragment() {
        if(branchesMapFragment == null) {
            branchesMapFragment = new BranchesMapFragment();
        }
        return branchesMapFragment;
    }

    private BranchesFragment getBranchesFragment() {
        if(branchesFragment == null) {
            branchesFragment = new BranchesFragment();
        }
        return branchesFragment;
    }

    private ContactUsFragment getContactUsFragment() {
        if(contactUsFragment == null) {
            contactUsFragment = new ContactUsFragment();
        }
        return contactUsFragment;
    }

    private MyOrdersFragment getMyOrdersFragment() {
        if(myOrdersFragment == null) {
            myOrdersFragment = new MyOrdersFragment();
        }
        return myOrdersFragment;
    }

    private AboutUsFragment getAboutUsFragment() {
        if(aboutUsFragment == null) {
            aboutUsFragment = new AboutUsFragment();
        }
        return aboutUsFragment;
    }


}
