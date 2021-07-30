package id.kelompok04.doize.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Collections;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.ScheduleViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyActivityFragment extends Fragment {

    private static final String TAG = "DailyActivityFragment";

    private ScheduleViewModel mScheduleViewModel;
    private RecyclerView mDailyActivityRecyclerView;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private FloatingActionButton mFloatingAddDailyActivityButton;
    private View mLayoutEmpty;
    private View customAlertDialogView;

    // Schedule Dialog Form
    private TextInputLayout mScheduleNameLayout;
    private TextInputLayout mScheduleDescriptionLayout;
    private TextInputLayout mScheduleSearchLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DailyActivityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyActivityFragment newInstance() {
        return new DailyActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mDaViewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_activity, container, false);
    }
}