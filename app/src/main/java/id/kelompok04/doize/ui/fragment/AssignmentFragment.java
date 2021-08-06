package id.kelompok04.doize.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import id.kelompok04.doize.R;
import id.kelompok04.doize.architecture.viewmodel.AssignmentViewModel;
import id.kelompok04.doize.helper.AlarmReceiver;
import id.kelompok04.doize.helper.CrudType;
import id.kelompok04.doize.helper.DateConverter;
import id.kelompok04.doize.helper.DateType;
import id.kelompok04.doize.helper.DoizeConstants;
import id.kelompok04.doize.model.Assignment;
import id.kelompok04.doize.model.Schedule;
import id.kelompok04.doize.model.response.AssignmentResponse;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentFragment extends Fragment {
    private static final String TAG = "AssignmentFragment";

    // Components
    private RecyclerView rvAssignment;
    private FloatingActionButton fabAddAssignment;
    private TabLayout tlAssignment;

    // Data
    private AssignmentAdapter rvAssignmentAdapter;
    private AssignmentViewModel mAssignmentViewModel;
    private List<Assignment> mAssignmentListFragment;

    // AlarmNotification
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;


    public AssignmentFragment() {
        // Required empty public constructor
    }

    public static AssignmentFragment newInstance() {
        return new AssignmentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAssignmentViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignment, container, false);

        rvAssignment = view.findViewById(R.id.rv_assignment);
        rvAssignment.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAssignment.setAdapter(rvAssignmentAdapter);

        tlAssignment = view.findViewById(R.id.tl_assignment);
        tlAssignment.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        fabAddAssignment = view.findViewById(R.id.fab_add_assignment);
        fabAddAssignment.setOnClickListener(v -> {
            AssignmentDialogFragment.display(CrudType.ADD, null, getActivity().getSupportFragmentManager());
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(rvAssignment);


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Date currentDate = new Date();
        mAssignmentViewModel.getAssignments(1).observe(getViewLifecycleOwner(), assignments -> {
            updateUI(assignments);
            for (Assignment assignment : assignments) {
                Date reminderAt = DateConverter.fromDbToDate(DateType.DATETIME, assignment.getReminderAt());
                if (currentDate.before(reminderAt)) {
                    Log.d(TAG, "onViewCreated: Alarm setted " + assignment.getNameAssignment());
                    setAlarm(1, assignment.getIdAssignment(), reminderAt.getTime(), assignment.getCourse(), assignment.getNameAssignment());
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Assignment> getActiveAssignment(List<Assignment> assignments) {
        return assignments.stream()
                .filter(assignment -> assignment.getWorkingStatus() == 0)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Assignment> getDoneAssignment(List<Assignment> assignments) {
        return assignments.stream()
                .filter(assignment -> assignment.getWorkingStatus() == 1)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Assignment> getPriorityAssignment(List<Assignment> assignments) {
        return assignments.stream()
                .filter(assignment -> assignment.getPriority() == 1)
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setTabValue(int tabPosition) {
        switch (tabPosition) {
            case 0:
                rvAssignmentAdapter = new AssignmentAdapter(getActiveAssignment(mAssignmentListFragment));
                break;
            case 1:
                rvAssignmentAdapter = new AssignmentAdapter(getDoneAssignment(mAssignmentListFragment));
                break;
            case 2:
                rvAssignmentAdapter = new AssignmentAdapter(getPriorityAssignment(mAssignmentListFragment));
                break;
        }
        rvAssignment.setAdapter(rvAssignmentAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateUI(List<Assignment> assignments) {
        mAssignmentListFragment = assignments;
        int tab_position = tlAssignment.getSelectedTabPosition();
        setTabValue(tab_position);
    }

    private void setAlarm(int type, int id, long time, String title, String content) {
        mAlarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);

        String requestCode = type + String.valueOf(id);
        mPendingIntent = PendingIntent.getBroadcast(getActivity(), Integer.parseInt(requestCode), intent, 0);
        mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, time, mPendingIntent);
    }

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Assignment assignment = rvAssignmentAdapter.mAssignments.get(position);

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    mAssignmentViewModel.deleteAssignment(assignment.getIdAssignment()).observe(getViewLifecycleOwner(), assignmentResponse -> {
                        if (assignmentResponse.getStatus() == 200) {
                            rvAssignmentAdapter.notifyItemRemoved(position);
                            Snackbar.make(rvAssignment, assignment.getNameAssignment() + " was deleted.", Snackbar.LENGTH_LONG)
                                    .setAction("Undo", v -> {
                                        Assignment updatedAssignment = assignmentResponse.getAssignment();
                                        updatedAssignment.setStatus(1);
                                        mAssignmentViewModel.addToPosition(position, updatedAssignment);
                                    }).show();
                        } else {
                            FancyToast.makeText(getActivity(), assignmentResponse.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR,false)
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

    private class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentHolder> {

        private List<Assignment> mAssignments;

        public AssignmentAdapter(List<Assignment> assignments) {
            mAssignments = assignments;
        }

        @NonNull
        @Override
        public AssignmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new AssignmentHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull AssignmentHolder holder, int position) {
            Assignment assignment = mAssignments.get(position);
            holder.bind(assignment);
        }

        @Override
        public int getItemCount() {
            return mAssignments.size();
        }

        private class AssignmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView mAssignmentName;
            private TextView mAssignmentCourse;
            private TextView mAssignmentTime;
            private ImageView mStarButton;
            private ImageView mCheckButton;
            private FrameLayout borderLeft;
            private Assignment mAssignment;

            public AssignmentHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_row_assignment, parent, false));
                itemView.setOnClickListener(this);

                mAssignmentName = itemView.findViewById(R.id.tv_assignment_name);
                mAssignmentCourse = itemView.findViewById(R.id.tv_assignment_course);
                mAssignmentTime = itemView.findViewById(R.id.tv_assignment_time);
                mStarButton = itemView.findViewById(R.id.star_icon);
                mCheckButton = itemView.findViewById(R.id.iv_check_assignment);
                borderLeft = itemView.findViewById(R.id.border_left_card_assignment);

                mStarButton.setOnClickListener(v -> {
                    int priority = mAssignment.getPriority() == 0 ? 1 : 0;
                    mAssignment.setPriority(priority);
                    mAssignmentViewModel.updateAssignment(-1,  mAssignment);
                });

                mCheckButton.setOnClickListener(v -> {
                    int workingStatus = mAssignment.getWorkingStatus() == 0 ? 1 : 0;
                    mAssignment.setWorkingStatus(workingStatus);
                    mAssignmentViewModel.updateAssignment(-1,  mAssignment);
                });

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            public void bind(Assignment assignment) {
                mAssignment = assignment;
                mAssignmentName.setText(assignment.getNameAssignment());
                mAssignmentCourse.setText(assignment.getCourse());

                Drawable borderLeftSrc = getResources()
                        .getDrawable((mAssignment.getWorkingStatus() == 0) ? R.drawable.border_left_purple : R.drawable.border_left_green);
                Drawable checkSrc = getResources()
                        .getDrawable((mAssignment.getWorkingStatus() == 0) ? R.drawable.ic_checked_false : R.drawable.ic_checked_true);

                mAssignmentName.setPaintFlags(mAssignment.getWorkingStatus() == 0 ? 0 : (mAssignmentName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG));

                borderLeft.setBackground(borderLeftSrc);
                mCheckButton.setImageDrawable(checkSrc);

                String dueDate = DateConverter.fromDbDateTimeTo(DoizeConstants.FULL_FORMAT, assignment.getDuedateAssignment());
                mAssignmentTime.setText(dueDate);

                Drawable star = getResources()
                        .getDrawable((mAssignment.getPriority() == 0) ? R.drawable.ic_star_bordered : R.drawable.ic_star_filled);

                mStarButton.setImageDrawable(star);
            }

            @Override
            public void onClick(View v) {
                AssignmentDialogFragment.display(CrudType.EDIT, mAssignment, getActivity().getSupportFragmentManager());
            }
        }
    }
}