package com.example.freshman_guide_chatbot.Ui.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.freshman_guide_chatbot.R;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.SAClickListener;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.StudentActivity;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.StudentsActivitiesAdapter;

import java.util.ArrayList;


public class StudentsActivitiesFragment extends Fragment implements SAClickListener {
    private RecyclerView recyclerView;
    private ArrayList<StudentActivity> studentActivities;
    private StudentsActivitiesAdapter studentsActivitiesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_students_activities, container, false);
        recyclerView = view.findViewById(R.id.recycler_sa);

        studentActivities = new ArrayList<>();
        studentActivities.add(new StudentActivity("acm","https://www.facebook.com/acmASCIS"));
        studentActivities.add(new StudentActivity("osc","https://www.facebook.com/oscgeeks"));
        studentActivities.add(new StudentActivity("msc","https://www.facebook.com/ASUTC"));
        studentActivities.add(new StudentActivity("i_club","https://www.facebook.com/iClub-FCIS-ASU-103826611069882"));
        studentActivities.add(new StudentActivity("robotics","https://www.facebook.com/RoboTech.FCIS"));
        studentActivities.add(new StudentActivity("support","https://www.facebook.com/SUPPORT.FCIS"));

        studentsActivitiesAdapter = new StudentsActivitiesAdapter(getActivity(), studentActivities, (SAClickListener) this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(studentsActivitiesAdapter);


        return view;
    }

    @Override
    public void onRecyclerViewClick(int pos) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(studentActivities.get(pos).getLink()));
        startActivity(intent);
    }
}