package id.kelompok04.doize.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.kelompok04.doize.R;

public class ScheduleDetailFragment extends Fragment {
    private static final String TAG = "ScheduleDetailFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView mTvScheduleName;
    private TextView mTvScheduleDesc;

    public ScheduleDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule_detail, container, false);
        mTvScheduleName = view.findViewById(R.id.tv_schedule_name);
        mTvScheduleDesc = view.findViewById(R.id.tv_schedule_description);

        String name = getArguments().getString("name");
        String desc = getArguments().getString("desc");

        mTvScheduleName.setText(name);
        mTvScheduleDesc.setText(desc);

        return view;
    }
}