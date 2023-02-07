package com.shashankbhat.notesapp;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.shashankbhat.notesapp.room.Note;
import com.shashankbhat.notesapp.receivers.NotifyNotes;
import com.shashankbhat.notesapp.viewmodel.MainActivityViewModel;
import com.shashankbhat.notesapp.viewmodel.MainViewModelFactory;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private MainActivityViewModel viewModel;
    private Context context;
    private static final int NOTIFICATION_ID = 10;
    private DrawerLayout drawerLayout;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        context = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_container);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setUpNavigation(drawerLayout, navController);

//        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        MainViewModelFactory factory = new MainViewModelFactory(getApplication(), 10);
        viewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);


//        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
//            if(destination.getId() == R.id.nav_home) {
//                toolbar.setMenu();
//            } else {
//                toolbar.getMenu().clear();
//            }
//        });


    }

    private void setUpNavigation(DrawerLayout drawerLayout, NavController navController) {

        appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_settings, R.id.nav_about)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "OnStart");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        scheduleNotificationWork();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    private void scheduleNotificationWork() {

        /**
         * Keep eye on pending intent notification id in case of clearing the notification in broadcast
         * or clear it when app start ie. in onStart()
         */

        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotifyNotes.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_ONE_SHOT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);

        if(Calendar.getInstance().getTimeInMillis() > calendar.getTimeInMillis()){
            calendar.add(Calendar.DATE, 1);
        }

        assert alarmMgr != null;
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

    }

    public void deleteAlertDialog(Note note, boolean isDeleteAll) {

        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);

        myAlertBuilder.setTitle("Delete");
        if(isDeleteAll) {
            myAlertBuilder.setMessage("Do you want to delete all note?");
            myAlertBuilder.setPositiveButton(R.string.yes, (dialog, which) -> viewModel.vmDeleteAllNotes());
        } else {
            myAlertBuilder.setMessage("Do you want to delete this note?");
            myAlertBuilder.setPositiveButton(R.string.yes, (dialog, which) -> viewModel.vmDelete(note));
        }

        myAlertBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        myAlertBuilder.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        Log.i("option", "option");

        int id = item.getItemId();
        switch (id) {

            case R.id.menu_delete_all:
                deleteAlertDialog(null, true);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

//    LocalBroadcastManager.getInstance(this)
//            .registerReceiver(mReceiver,
//                        new IntentFilter(ACTION_CUSTOM_BROADCAST));
//        LocalBroadcastManager.getInstance(this)
//                .unregisterReceiver(mReceiver);

}