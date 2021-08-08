package id.kelompok04.doize.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
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
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.AssignmentViewModel;
import id.kelompok04.doize.architecture.viewmodel.DailyActivityViewModel;
import id.kelompok04.doize.helper.AlarmReceiver;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.NotificationHelper;
import id.kelompok04.doize.helper.NotificationType;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.DailyActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private NavigationView nvDrawer;
    private NavController mNavController;
    private BottomNavigationView mBottomNavigationView;
    private AssignmentViewModel mAssignmentViewModel;
    private DailyActivityViewModel mDailyActivityViewModel;

    // Components
    private TextView mTvHeaderTitle;
    private TextView mTvHeaderSubtitle;
    private SharedPreferences userPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userPreferences = getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        setupNavigation();
        mAssignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
        mDailyActivityViewModel = new ViewModelProvider(this).get(DailyActivityViewModel.class);
        setAlarm();
    }

    @SuppressLint("NonConstantResourceId")
    public void setupNavigation() {
        mToolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Dashboard");
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        View headerView = LayoutInflater.from(this).inflate(R.layout.header_navigation_drawer, nvDrawer, false);
        nvDrawer.addHeaderView(headerView);
        mTvHeaderTitle = headerView.findViewById(R.id.navigation_drawer_header_title);
        mTvHeaderSubtitle = headerView.findViewById(R.id.navigation_drawer_header_subtitle);

        mTvHeaderTitle.setText(userPreferences.getString("name", ""));
        mTvHeaderSubtitle.setText(userPreferences.getString("email", ""));

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
                SharedPreferences.Editor editor = userPreferences.edit();
                editor.putString("oldId", userPreferences.getString("id", ""));
                editor.apply();

                editor.remove("email");
                editor.remove("password");
                editor.remove("birth_date");
                editor.remove("phone");
                editor.remove("name");
                editor.remove("id");
                editor.apply(); //remove all

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                break;

        }

        return true;
    }

    private void setAlarm() {
        int idUser = Integer.parseInt(userPreferences.getString("id", "0"));
        int oldUser = Integer.parseInt(userPreferences.getString("oldId", "0"));

        // if user sign in with a new account, cancel all alarm of previous user
        if (idUser != oldUser && oldUser != 0) {
            mAssignmentViewModel.getAssignmentsOnce(oldUser).observe(this, assignments -> {
                for (Assignment assignment : assignments) {
                    Log.d(TAG, "setAlarm: Cancel Assignment " + assignment);
                    NotificationHelper.cancelAlarm(this, 1, assignment.getIdAssignment());
                }
            });

            mDailyActivityViewModel.getDailyActivitiesOnce(oldUser).observe(this, dailyActivities -> {;
                for (DailyActivity dailyActivity : dailyActivities) {
                    Log.d(TAG, "setAlarm: Cancel Daily " + dailyActivity);
                    NotificationHelper.cancelAlarm(this, 2, dailyActivity.getIdDailyActivity());
                }
            });
        }

        // Set new alarm
        mAssignmentViewModel.getAssignments(idUser).observe(this, assignments -> {
            Date currentDate = new Date();
            for (Assignment assignment : assignments) {
                int requestCode = Integer.parseInt("1" + assignment.getIdAssignment());
                boolean alarmUp = (PendingIntent.getBroadcast(this, requestCode , new Intent(MainActivity.this, AlarmReceiver.class), PendingIntent.FLAG_NO_CREATE) != null);
                if (alarmUp) {
                    NotificationHelper.cancelAlarm(this, 1, assignment.getIdAssignment());
                }

                Date reminderAt = DateConverter.fromDbToDate(DateType.DATETIME, assignment.getReminderAt());
                if (currentDate.before(reminderAt)) {
                    NotificationHelper.setAlarm(this, 1, assignment.getIdAssignment(), reminderAt.getTime(), assignment.getCourse(),
                            DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, assignment.getDuedateAssignment()) + " : " + assignment.getNameAssignment());
                }
            }
        });

        mDailyActivityViewModel.getDailyActivities(idUser).observe(this, dailyActivities -> {
            Date currentDate = new Date();
            for (DailyActivity dailyActivity : dailyActivities) {
                int requestCode = Integer.parseInt("2" + dailyActivity.getIdDailyActivity());
                boolean alarmUp = (PendingIntent.getBroadcast(this, requestCode , new Intent(MainActivity.this, AlarmReceiver.class), PendingIntent.FLAG_NO_CREATE) != null);
                if (alarmUp) {
                    NotificationHelper.cancelAlarm(this, 2, dailyActivity.getIdDailyActivity());
                }

                Date reminderAt = DateConverter.fromDbToDate(DateType.DATETIME, dailyActivity.getReminderAt());
                if (currentDate.before(reminderAt)) {
                    NotificationHelper.setAlarm(this, 2, dailyActivity.getIdDailyActivity(), reminderAt.getTime(),
                            dailyActivity.getNameDailyActivity(), DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, dailyActivity.getDuedateDailyActivity()) +
                                    " : " + dailyActivity.getDescriptionDailyActivity());
                }
            }
        });
    }
}