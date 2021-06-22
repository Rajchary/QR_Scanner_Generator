package com.curiosity.qrgeneratorandscanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context)
    {
        this.context = context;
    }

    public int[] slider_images = {
            R.drawable.curiosity,R.drawable.chary_dp,R.drawable.venu_dp,R.drawable.hitha_dp
            ,R.drawable.shravs_dp
    };

    public String[] slide_headings = {
            "Curiosity","Rajabrahmam (Chary)","Venu","Srihitha","Shravani"
    };
    public String[] slide_description = {
    "Wait wait I am not the one who explored mars that was another" +
     " Curiosity. We are more alike. But i explore the IT world\uD83D\uDDA5️ instead. This Corp was founded by Chary."+
    "I was named curiosity because it is the reason for our present and the source of our future❤️.",
        "Chary is an enthusiast and a passionate learner. "+
        "Who is very interested in developing things like the one you are looking at."+
        " And he always wanted to contribute his part in this evolving technology✨",
    "Venu is an avid learner who has interest in developing user friendly applications.He is pursuing Graduation from Vardhaman."+
    "He turned down Mining to purse his passion for developing.",
        "Iam Srihitha, pursuing graduation at Vardhaman in Computer Science and  Engineering."+
        " Iam passion at my career and Very enthusiastic in creative works.",
    "Iam shravani ,Pursuing graduation at vardhaman college of engineering. "+
    "I am very  interactive towards creative and innovative things,and iam passionate about my dream."
    };

    @Override
    public int getCount() {
        return slider_images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull @org.jetbrains.annotations.NotNull View view, @NonNull @org.jetbrains.annotations.NotNull Object object) {
        return view == (ConstraintLayout)object;
    }
    public @NotNull Object instantiateItem(ViewGroup container, int position)
    {
        layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);
        TextView name=  view.findViewById(R.id.name);
        TextView description = view.findViewById(R.id.description);
        ImageView image = view.findViewById(R.id.user_logo);
        image.setImageResource(slider_images[position]);
        name.setText(slide_headings[position]);
        description.setText(slide_description[position]);
//        description.setPadding();
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((ConstraintLayout)object);
    }
}
