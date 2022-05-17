package com.example.freshman_guide_chatbot.Ui.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.freshman_guide_chatbot.R;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.Message;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.MessageListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {
    private RecyclerView recyclerView;
    private MessageListAdapter userMessageListAdapter;
    private ImageButton send;
    private EditText messageText;
    Message message;
    ArrayList<Message> messageList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recycler_chat);
        messageText=view.findViewById(R.id.edit_message);
        send=view.findViewById(R.id.button_chat_send);

        messageList=new ArrayList<>();
        message=new Message("اهلا","bot");
        messageList.add(message);


        userMessageListAdapter = new MessageListAdapter(getActivity(), messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(userMessageListAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=new Message(messageText.getText().toString(),"user");
                messageList.add(message);
                userMessageListAdapter.notifyDataSetChanged();

            }
        });



        return view;
    }
}