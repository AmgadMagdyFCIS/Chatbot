package com.example.freshman_guide_chatbot.Ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.freshman_guide_chatbot.R;

import java.util.List;

public class IntroViewPageAdapter extends PagerAdapter
{
    Context mcontext ;
    List<ScreenItem> mlistSreen;

    public IntroViewPageAdapter(Context mcontext, List<ScreenItem> mlistSreen) {
        this.mcontext = mcontext;
        this.mlistSreen = mlistSreen;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutscreen = inflater.inflate(R.layout.layout, null);

        ImageView imgslide = layoutscreen.findViewById(R.id.intro_image);
        TextView title = layoutscreen.findViewById(R.id.into_title);
        TextView descreption = layoutscreen.findViewById(R.id.intro_description);
        title.setText(mlistSreen.get(position).getTitle());
        descreption.setText(mlistSreen.get(position).getDescreption());
        imgslide.setImageResource(mlistSreen.get(position).getScreenImg());
        container.addView(layoutscreen);
        return layoutscreen;

    }

    @Override
    public int getCount()
    {
        return mlistSreen.size();

    }

    @Override
    public boolean isViewFromObject (@NonNull View view, @NonNull Object o)
    {
        return view==o;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
