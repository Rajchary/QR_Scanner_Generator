package com.curiosity.qrgeneratorandscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {
    private LinearLayoutCompat linearLayoutCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        ViewPager viewPager = findViewById(R.id.pager);
        linearLayoutCompat  = findViewById(R.id.indicator);

        SliderAdapter sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);
        viewPager.addOnPageChangeListener(viewListener);
        addDotsToIndicator(0);
    }
    public void addDotsToIndicator(int position){
        TextView[] dots = new TextView[5];
        linearLayoutCompat.removeAllViews();
        for(int i = 0; i< dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(".");
            dots[i].setTextSize(65);
            dots[i].setTextColor(getResources().getColor(R.color.black));
            linearLayoutCompat.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.white));
        dots[position].setTextSize(75);
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsToIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}