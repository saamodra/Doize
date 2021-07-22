package id.kelompok04.doize.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import id.kelompok04.doize.R;
import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.ui.fragment.DailyActivityFragment;
import id.kelompok04.doize.ui.fragment.DashboardFragment;
import id.kelompok04.doize.ui.fragment.ScheduleFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private MaterialToolbar mToolbar;
    private NavigationView nvDrawer;
    private NavController mNavController;
    private BottomNavigationView mBottomNavigationView;
    private AppBarConfiguration mAppBarConfiguration;


    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();

    }

    public void setupNavigation() {
        mToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        mNavController = Navigation.findNavController(this, R.id.fragment_container);

        NavigationUI.setupActionBarWithNavController(this, mNavController, mDrawerLayout);
        NavigationUI.setupWithNavController(nvDrawer, mNavController);
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);

        nvDrawer.setNavigationItemSelectedListener(this);
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

                    case R.id.scheduleFragment:
                        mNavController.navigate(R.id.scheduleFragment);
                        break;

                    case R.id.exit_drawer:
                        finishAffinity();
                        break;

                }

                return true;
            }
        });
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
            case R.id.home_drawer:
                mNavController.navigate(R.id.dashboardFragment);
                break;

            case R.id.to_do_drawer:
                mNavController.navigate(R.id.action_dashboardFragment_to_dailyActivityFragment);
                break;

            case R.id.schedule_drawer:
                mNavController.navigate(R.id.action_dashboardFragment_to_scheduleFragment);
                break;

            case R.id.exit_drawer:
                finishAffinity();
                break;

        }

        return true;
    }
}