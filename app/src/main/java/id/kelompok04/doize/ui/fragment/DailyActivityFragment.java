package id.kelompok04.doize.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.DailyActivityViewModel;
import id.kelompok04.doize.model.DailyActivity;
import id.kelompok04.doize.model.Schedule;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyActivityFragment extends Fragment {

    private static final String TAG = "DailyActivityFragment";

    private DailyActivityViewModel mDailyActivityViewModel;
    private RecyclerView mDailyActivityRecyclerView;
    private MaterialAlertDialogBuilder mMaterialAlertDialogBuilder;
    private FloatingActionButton mFloatingAddDailyActivityButton;
    private View mLayoutEmpty;
    private View customAlertDialogView;

    private DailyActivityFragment.DailyActivityAdapter mDailyActivityAdapter = new DailyActivityFragment.DailyActivityAdapter(Collections.emptyList());

    // Daily Activity Dialog Form
    private TextInputLayout mDailyActivityNameLayout;
    private TextInputLayout mDailyActivityDescriptionLayout;
    private TextInputLayout mDailyActivitySearchLayout;


    // Data
    private List<DailyActivity> mDailyActivitiesFragment;

    public DailyActivityFragment() {
        // Required empty public constructor
    }

    public static DailyActivityFragment newInstance() {
        DailyActivityFragment fragment = new DailyActivityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mDailyActivityViewModel = new ViewModelProvider(this).get(DailyActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_activity, container, false);
        mDailyActivityRecyclerView = view.findViewById(R.id.rv_daily_activity);
        mDailyActivityRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDailyActivityRecyclerView.setAdapter(mDailyActivityAdapter);

        mLayoutEmpty = view.findViewById(R.id.layout_empty_data);

//        mMaterialAlertDialogBuilder = new MaterialAlertDialogBuilder(getActivity());
//        mFloatingAddScheduleButton = view.findViewById(R.id.fab_add_schedule);
//        mFloatingAddScheduleButton.setOnClickListener(v -> {
//            customAlertDialogView = inflater.inflate(R.layout.dialog_schedule_form, container, false);
//            launchCustomAlertDialog();
//        });

        mDailyActivitySearchLayout = view.findViewById(R.id.til_daily_activity_search);
        mDailyActivitySearchLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        return view;
    }

    public void filter(String text) {
        List<DailyActivity> filtered = new ArrayList<>();

        for (DailyActivity da : mDailyActivitiesFragment) {
            if (da.getNameDailyActivity().toLowerCase().contains(text.toLowerCase())
                    || da.getDescriptionDailyActivity().toLowerCase().contains(text.toLowerCase())) {
                filtered.add(da);
            }
        }

        mDailyActivityAdapter.filterList(filtered);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDailyActivityViewModel.getDailyActivities().observe(getViewLifecycleOwner(), new Observer<List<DailyActivity>>() {
            @Override
            public void onChanged(List<DailyActivity> dailyActivities) {
                updateUI(dailyActivities);
            }
        });
    }

    private void updateUI(List<DailyActivity> dailyActivities) {
        mDailyActivityAdapter = new DailyActivityFragment.DailyActivityAdapter(dailyActivities);
        mDailyActivityRecyclerView.setAdapter(mDailyActivityAdapter);
        mDailyActivitiesFragment = dailyActivities;
    }

    private class DailyActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<DailyActivity> mDailyActivities;

        public DailyActivityAdapter(List<DailyActivity> dailyActivities) {
            mDailyActivities = dailyActivities;
        }

        public void filterList(List<DailyActivity> filtered) {
            if (filtered.size() == 0) {
                mLayoutEmpty.setVisibility(View.VISIBLE);
                mDailyActivityRecyclerView.setVisibility(View.GONE);
            } else {
                mLayoutEmpty.setVisibility(View.GONE);
                mDailyActivityRecyclerView.setVisibility(View.VISIBLE);
            }
            this.mDailyActivities = filtered;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new DailyActivityFragment.DailyActivityAdapter.DailyActivityHolder(layoutInflater, parent);
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            DailyActivity dailyActivity = mDailyActivities.get(position);
            ((DailyActivityFragment.DailyActivityAdapter.DailyActivityHolder) holder).bind(dailyActivity);
        }

        @Override
        public int getItemCount() {
            return mDailyActivities.size();
        }

        private class DailyActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mDailyActivityName;
            private TextView mDailyActivityDesc;
            private DailyActivity mDailyActivity;

            public DailyActivityHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_daily_activity, parent, false));
                itemView.setOnClickListener(this);

                mDailyActivityName = itemView.findViewById(R.id.tv_daily_activity_name);
                mDailyActivityDesc = itemView.findViewById(R.id.tv_daily_activity_description);
            }

            public void bind(DailyActivity dailyActivity) {
                mDailyActivity = dailyActivity;
                mDailyActivityName.setText(dailyActivity.getNameDailyActivity());
                mDailyActivityDesc.setText(dailyActivity.getDuedateDailyActivity());
            }

            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("dailyActId", Integer.toString(mDailyActivity.getIdDailyActivity()));
                bundle.putString("dailyActName", mDailyActivity.getNameDailyActivity());
                bundle.putString("dailyActDesc", mDailyActivity.getDescriptionDailyActivity());
                Navigation.findNavController(getActivity(), R.id.fragment_container).navigate(R.id.action_dailyActivityFragment_to_detailDailyActivityFragment, bundle);
            }
        }
    }
}