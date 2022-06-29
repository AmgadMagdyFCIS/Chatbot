package com.example.freshman_guide_chatbot.Ui.Fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.freshman_guide_chatbot.R;
import com.example.freshman_guide_chatbot.Ui.PythonService;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.Message;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.MessageListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class ChatFragment extends Fragment {
    private final int VOICE_REQUEST = 1999;
    private RecyclerView recyclerView;
    private MessageListAdapter userMessageListAdapter;
    private ImageButton send,voice;
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
        voice=view.findViewById(R.id.search_voice_btn_);

        messageList=new ArrayList<>();
        message=new Message("مرحبا كيف يمكنني مساعدتك؟","bot");
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
                messageText.setText("");
                botResponse();
//
            }
        });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);// spilling
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
                startActivityForResult(intent, VOICE_REQUEST);
            }
        });

        return view;
    }
    public void botResponse()
    {
        PythonService pythonService=new PythonService();


        // call a function called main from hello.py
        PyObject response = pythonService.python_module.callAttr("response",message.getMessageText());
        message=new Message(response.toString(),"bot");
        messageList.add(message);
        userMessageListAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(messageList.size()-1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null) {
                    messageText.setText(result.get(0));
                }
            }
        }
    }
}