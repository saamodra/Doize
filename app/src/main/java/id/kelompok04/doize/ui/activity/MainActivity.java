package id.kelompok04.doize.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

import id.kelompok04.doize.R;
import id.kelompok04.doize.helper.AlarmReceiver;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.NotificationType;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private MaterialToolbar mToolbar;
    private NavigationView nvDrawer;
    private NavController mNavController;
    private BottomNavigationView mBottomNavigationView;
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupNavigation();
    }

    @SuppressLint("NonConstantResourceId")
    public void setupNavigation() {
        mToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        mNavController = Navigation.findNavController(this, R.id.fragment_container);

        NavigationUI.setupActionBarWithNavController(this, mNavController, mDrawerLayout);
        NavigationUI.setupWithNavController(nvDrawer, mNavController);
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);

        createNotificationChannel();

        nvDrawer.setNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnItemSelectedListener(item -> {
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            int id = item.getItemId();
            switch (id) {
                case R.id.dashboardFragment:
                    Log.d(TAG, "onNavigationItemSelected: " + "DashboardFragment");
                    mNavController.navigate(R.id.dashboardFragment);
                    break;

                case R.id.dailyActivityFragment:
                    Log.d(TAG, "onNavigationItemSelected: " + "DashboardFragment");
                    mNavController.navigate(R.id.dailyActivityFragment);
                    break;

                case R.id.assignmentFragment:
                    mNavController.navigate(R.id.assignmentFragment);
                    break;

                case R.id.scheduleFragment:
                    mNavController.navigate(R.id.scheduleFragment);
                    break;

                case R.id.pomodoroFragment:
                    mNavController.navigate(R.id.pomodoroFragment);
                    break;

                case R.id.exit_drawer:
                    finishAffinity();
                    break;

            }

            return true;
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(DoizeConstants.NOTIFICATION_CHANNEL_ID, DoizeConstants.NOTIFICATION_CHANNEL_ID,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This is notification channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mDrawerLayout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);

        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        mDrawerLayout.closeDrawers();
        int id = item.getItemId();
        switch (id) {
            case R.id.profile_drawer:
                mNavController.navigate(R.id.profileFragment);
                break;

            case R.id.home_drawer:
                mNavController.navigate(R.id.dashboardFragment);
                break;

            case R.id.to_do_drawer:
                mNavController.navigate(R.id.action_dashboardFragment_to_dailyActivityFragment);
                break;

            case R.id.assignment_drawer:
                mNavController.navigate(R.id.action_dashboardFragment_to_assignmentFragment);
                break;

            case R.id.schedule_drawer:
                mNavController.navigate(R.id.action_dashboardFragment_to_scheduleFragment);
                break;

            case R.id.pomodoro_drawer:
                mNavController.navigate(R.id.action_dashboardFragment_to_pomodoroFragment);
                break;

            case R.id.exit_drawer:
                SharedPreferences preferences = getSharedPreferences("user_pref", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.clear().apply(); //remove all

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                break;

        }

        return true;
    }
}