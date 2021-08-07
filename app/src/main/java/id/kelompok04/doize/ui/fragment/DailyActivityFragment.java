package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;
import java.util.stream.Collectors;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.DailyActivityViewModel;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.helper.DoizeHelper;
import id.kelompok04.doize.model.DailyActivity;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class DailyActivityFragment extends Fragment {
    private static final String TAG = "DailyActivityFragment";

    // Components
    private RecyclerView rvDailyActivity;
    private FloatingActionButton fabAddDailyActivity;
    private TabLayout tlDailyActivity;

    // Data
    private DailyActivityFragment.DailyActivityAdapter rvDailyActivityAdapter;
    private DailyActivityViewModel mDailyActivityViewModel;
    private List<DailyActivity> mDailyActivityListFragment;

    public DailyActivityFragment() {
        // Required empty public constructor
    }

    public static DailyActivityFragment newInstance() {
        return new DailyActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDailyActivityViewModel = new ViewModelProvider(this).get(DailyActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_activity, container, false);

        rvDailyActivity = view.findViewById(R.id.rv_daily_activity);
        rvDailyActivity.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDailyActivity.setAdapter(rvDailyActivityAdapter);

        tlDailyActivity = view.findViewById(R.id.tl_daily_activity);
        tlDailyActivity.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                setTabValue(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fabAddDailyActivity = view.findViewById(R.id.fab_add_daily_activity);
        fabAddDailyActivity.setOnClickListener(v -> {
            DailyActivityDialogFragment.display(CrudType.ADD, null, requireActivity().getSupportFragmentManager());
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(rvDailyActivity);

        return view;
    }

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            DailyActivity dailyActivity = rvDailyActivityAdapter.mDailyActivities.get(position);

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    mDailyActivityViewModel.deleteDailyActivity(dailyActivity.getIdDailyActivity()).observe(getViewLifecycleOwner(), dailyActivityResponse -> {
                        if (dailyActivityResponse.getStatus() == 200) {
                            rvDailyActivityAdapter.notifyItemRemoved(position);
                            Snackbar.make(rvDailyActivity, dailyActivity.getNameDailyActivity() + " was deleted.", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", v -> {
                                        DailyActivity updatedDailyActivity = dailyActivityResponse.getData();
                                        updatedDailyActivity.setStatus(1);
                                        mDailyActivityViewModel.addToPosition(position, updatedDailyActivity);
                                    }).show();
                        } else {
                            FancyToast.makeText(getActivity(), dailyActivityResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false)
                                    .show();
                        }
                    });
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.pink))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDailyActivityViewModel.getDailyActivities(DoizeHelper.getIdUserPref(requireActivity())).observe(getViewLifecycleOwner(), this::updateUI);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<DailyActivity> getActiveDailyActivity(List<DailyActivity> dailyActivities) {
        return dailyActivities.stream()
                .filter(dailyActivity -> dailyActivity.getWorkingStatus() == 0)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<DailyActivity> getDoneDailyActivity(List<DailyActivity> dailyActivities) {
        return dailyActivities.stream()
                .filter(dailyActivity -> dailyActivity.getWorkingStatus() == 1)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<DailyActivity> getPriorityDailyActivity(List<DailyActivity> dailyActivities) {
        return dailyActivities.stream()
                .filter(dailyActivity -> dailyActivity.getPriority() == 1)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTabValue(int tabPosition) {
        switch (tabPosition) {
            case 0:
                rvDailyActivityAdapter = new DailyActivityFragment.DailyActivityAdapter(getActiveDailyActivity(mDailyActivityListFragment));
                break;
            case 1:
                rvDailyActivityAdapter = new DailyActivityFragment.DailyActivityAdapter(getDoneDailyActivity(mDailyActivityListFragment));
                break;
            case 2:
                rvDailyActivityAdapter = new DailyActivityFragment.DailyActivityAdapter(getPriorityDailyActivity(mDailyActivityListFragment));
                break;
        }
        rvDailyActivity.setAdapter(rvDailyActivityAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateUI(List<DailyActivity> dailyActivities) {
        mDailyActivityListFragment = dailyActivities;
        int tab_position = tlDailyActivity.getSelectedTabPosition();
        setTabValue(tab_position);
    }

    private class DailyActivityAdapter extends RecyclerView.Adapter<DailyActivityFragment.DailyActivityAdapter.DailyActivityHolder> {

        private List<DailyActivity> mDailyActivities;

        public DailyActivityAdapter(List<DailyActivity> dailyActivities) {
            mDailyActivities = dailyActivities;
        }

        @NonNull
        @Override
        public DailyActivityFragment.DailyActivityAdapter.DailyActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new DailyActivityFragment.DailyActivityAdapter.DailyActivityHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DailyActivityFragment.DailyActivityAdapter.DailyActivityHolder holder, int position) {
            DailyActivity dailyActivity = mDailyActivities.get(position);
            holder.bind(dailyActivity);
        }

        @Override
        public int getItemCount() {
            return mDailyActivities.size();
        }

        private class DailyActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mDailyActivityName;
            private TextView mDailyActivityTime;
            private ImageView mStarButton;
            private ImageView mCheckButton;
            private FrameLayout borderLeft;
            private DailyActivity mDailyActivity;

            public DailyActivityHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_daily_activity, parent, false));
                itemView.setOnClickListener(this);

                mDailyActivityName = itemView.findViewById(R.id.tv_daily_activity_name);
                mDailyActivityTime = itemView.findViewById(R.id.tv_daily_activity_time);
                mStarButton = itemView.findViewById(R.id.star_icon);
                mCheckButton = itemView.findViewById(R.id.iv_check_daily_activity);
                borderLeft = itemView.findViewById(R.id.border_left_card_daily_activity);

                mStarButton.setOnClickListener(v -> {
                    int priority = mDailyActivity.getPriority() == 0 ? 1 : 0;
                    mDailyActivity.setPriority(priority);
                    mDailyActivityViewModel.updateDailyActivity(-1, mDailyActivity);
                });

                mCheckButton.setOnClickListener(v -> {
                    int workingStatus = mDailyActivity.getWorkingStatus() == 0 ? 1 : 0;
                    mDailyActivity.setWorkingStatus(workingStatus);
                    mDailyActivityViewModel.updateDailyActivity(-1, mDailyActivity);
                });

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            public void bind(DailyActivity dailyActivity) {
                mDailyActivity = dailyActivity;
                mDailyActivityName.setText(dailyActivity.getNameDailyActivity());

                Drawable borderLeftSrc = getResources()
                        .getDrawable((mDailyActivity.getWorkingStatus() == 0) ? R.drawable.border_left_purple : R.drawable.border_left_green);
                Drawable checkSrc = getResources()
                        .getDrawable((mDailyActivity.getWorkingStatus() == 0) ? R.drawable.ic_checked_false : R.drawable.ic_checked_true);

                mDailyActivityName.setPaintFlags(mDailyActivity.getWorkingStatus() == 0 ? 0 : (mDailyActivityName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));

                borderLeft.setBackground(borderLeftSrc);
                mCheckButton.setImageDrawable(checkSrc);

                String dueDate = DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, dailyActivity.getDuedateDailyActivity());
                mDailyActivityTime.setText(dueDate);

                Drawable star = getResources()
                        .getDrawable((mDailyActivity.getPriority() == 0) ? R.drawable.ic_star_bordered : R.drawable.ic_star_filled);

                mStarButton.setImageDrawable(star);
            }

            @Override
            public void onClick(View v) {
                DailyActivityDialogFragment.display(CrudType.EDIT, mDailyActivity, getActivity().getSupportFragmentManager());
            }
        }
    }
}