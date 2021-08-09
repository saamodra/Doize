package id.kelompok04.doize.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import id.kelompok04.doize.R;

public class SlideActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    SlideViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        viewPager=findViewById(R.id.viewpager);
        adapter=new SlideViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        if (isOpenAlread())
        {
            Intent intent=new Intent(SlideActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            SharedPreferences.Editor editor=getSharedPreferences("slide",MODE_PRIVATE).edit();
            editor.putBoolean("slide", true);
            editor.commit();
        }

    }

    // Get SharedPreference if open alread
    private boolean isOpenAlread() {

        SharedPreferences sharedPreferences=getSharedPreferences("slide",MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("slide",false);
        return result;

    }

    private class SlideViewPagerAdapter extends PagerAdapter {

        Context mContext;

        public SlideViewPagerAdapter(Context context) {
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slide_screen,container,false);

            ImageView logo = view.findViewById(R.id.logo);

            ImageView ind1 = view.findViewById(R.id.ind1);
            ImageView ind2 = view.findViewById(R.id.ind2);
            ImageView ind3 = view.findViewById(R.id.ind3);


            TextView title = view.findViewById(R.id.title);
            TextView desc = view.findViewById(R.id.desc);

            ImageView next = view.findViewById(R.id.iv_next);
            ImageView back = view.findViewById(R.id.iv_back);

            Button btnGetStarted = view.findViewById(R.id.btnGetStarted);
            btnGetStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlideActivity.viewPager.setCurrentItem(position+1);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SlideActivity.viewPager.setCurrentItem(position-1);
                }
            });

            switch (position)
            {
                case 0:
                    logo.setImageResource(R.drawable.logo_slide1);
                    ind1.setImageResource(R.drawable.ic_selected);
                    ind2.setImageResource(R.drawable.ic_unselected);
                    ind3.setImageResource(R.drawable.ic_unselected);

                    title.setText(R.string.title1);
                    desc.setText(R.string.desc_title1);

                    back.setVisibility(View.GONE);
                    next.setVisibility(View.VISIBLE);
                    break;

                case 1:
                    logo.setImageResource(R.drawable.logo_slide2);
                    ind1.setImageResource(R.drawable.ic_unselected);
                    ind2.setImageResource(R.drawable.ic_selected);
                    ind3.setImageResource(R.drawable.ic_unselected);

                    title.setText(R.string.title2);
                    desc.setText(R.string.desc_title2);

                    back.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    break;

                case 2:
                    logo.setImageResource(R.drawable.logo_slide3);
                    ind1.setImageResource(R.drawable.ic_unselected);
                    ind2.setImageResource(R.drawable.ic_unselected);
                    ind3.setImageResource(R.drawable.ic_selected);

                    title.setText(R.string.title3);
                    desc.setText(R.string.desc_title3);

                    back.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    break;

            }

            container.addView(view);
            return view;

        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
