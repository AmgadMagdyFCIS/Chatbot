package com.example.freshman_guide_chatbot.Ui.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.freshman_guide_chatbot.R;
import com.example.freshman_guide_chatbot.Ui.NavigationActivity;
import com.example.freshman_guide_chatbot.Ui.PythonService;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.Message;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.MessageListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.freshman_guide_chatbot.Ui.Recyclerview.SAClickListener;
/*import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;*/

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.freshman_guide_chatbot.Ui.Registration.Login;
import com.example.freshman_guide_chatbot.Ui.SplashScreen;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnFailureListener;
public class ChatFragment extends Fragment implements SAClickListener {
    private final int VOICE_REQUEST = 1999;
    private RecyclerView recyclerView;
    private MessageListAdapter userMessageListAdapter;
    private ImageButton send,voice;
    private EditText messageText;


    /*OkHttpClient client;
    Request request;*/
    Message message;
    ArrayList<Message> messageList,temp;
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
        temp=new ArrayList<>();
        message=new Message("مرحبا كيف يمكنني مساعدتك؟","bot");
        messageList.add(message);


        userMessageListAdapter = new MessageListAdapter(getActivity(), messageList,(SAClickListener)this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(userMessageListAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message=new Message(messageText.getText().toString(),"user");
                messageList.add(message);

                messageText.setText("");
                recyclerView.scrollToPosition(messageList.size()-1);
                notifyData();
                //post(message.getMessageText());
                botResponse();
                notifyData();
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


   /* public void post(String message)
    {
        client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("question",message).build();
        request = new Request.Builder().url("").post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(getActivity(),"something went wrong",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                botResponse(response.body().toString());
            }
        });

    }*/
    void notifyData()
    {
        userMessageListAdapter.notifyDataSetChanged();
    }
    public void botResponse()
    {
        //PythonService pythonService=new PythonService();
        // call a function called main from hello.py
        PyObject response = NavigationActivity.python_module.callAttr("response",message.getMessageText());


        if(response.equals("جدول"))
        {
            message=new Message("اضغط هنا لتصل الي الجدول","bot",NavigationActivity._uri);
            messageList.add(message);


        }

        else {
            message = new Message(response.toString(), "bot");
            messageList.add(message);
        }

        recyclerView.scrollToPosition(messageList.size()-1);

        /*if(messageList.size()==9)
        {
            messageList.clear();
            message=new Message(message.getMessageText(),"bot");
            messageList.add(message);
        }*/
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

    @Override
    public void onRecyclerViewClick(int pos) {
        if(!messageList.get(pos).getUri().equals("")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(messageList.get(pos).getUri()));
            startActivity(intent);


        }
    }
}