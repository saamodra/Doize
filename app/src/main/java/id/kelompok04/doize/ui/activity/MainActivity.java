package id.kelompok04.doize.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.gson.Gson;

import id.kelompok04.doize.R;
import id.kelompok04.doize.model.User;
import id.kelompok04.doize.ui.fragment.DashboardFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get userlogin extra from login activity
        String userObject = getIntent().getStringExtra("userLogin");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            // Instantiate Dashboard Fragment
            fragment = DashboardFragment.newInstance(userObject);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}