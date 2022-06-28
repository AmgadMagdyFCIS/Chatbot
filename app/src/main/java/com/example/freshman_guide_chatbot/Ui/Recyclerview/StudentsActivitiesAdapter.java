package com.example.freshman_guide_chatbot.Ui.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshman_guide_chatbot.R;

import java.util.List;

public class StudentsActivitiesAdapter  extends RecyclerView.Adapter<StudentsActivitiesAdapter.MyViewHolder> {
    private Context context;
    private List<StudentActivity> SAs;
    private View view;
    private SAClickListener sAClickListener;

    public StudentsActivitiesAdapter(Context context, List<StudentActivity> SAs,SAClickListener sAClickListener) {
        this.context = context;
        this.SAs = SAs;
        this.sAClickListener=sAClickListener;

    }

    @NonNull
    @Override
    public StudentsActivitiesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_activity, parent, false);
        return new StudentsActivitiesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsActivitiesAdapter.MyViewHolder holder, int position) {
        StudentActivity SA = SAs.get(position);
        int resourceIdentifier = holder.itemView.getResources().getIdentifier(SA.getImagesrc(), "drawable", holder.itemView.getContext().getPackageName());
        holder.SAImage.setImageResource(resourceIdentifier);

    }

    @Override
    public int getItemCount() {
        return SAs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView SAImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            SAImage=itemView.findViewById(R.id.image_SA);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() != -1) {
                        sAClickListener.onRecyclerViewClick(getAdapterPosition());
                    }
                }
            });
        }
    }


}
