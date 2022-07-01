package com.example.freshman_guide_chatbot.Ui.Recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshman_guide_chatbot.R;
import com.google.firebase.installations.Utils;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {

    private Context context;
    private List<Message> messages;
    private View view;
    public int index;
    private SAClickListener sAClickListener;

    public MessageListAdapter(Context context, List<Message> messages ,SAClickListener sAClickListener) {
        this.context = context;
        this.messages = messages;
        /*if(messages.size()>1)
            index = messages.size() - 2;*/

        index=0;
        this.sAClickListener=sAClickListener;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (messages.get(index).getSender().equals("user"))
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_query, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_query, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageText.setText(message.getMessageText());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            if (messages.get(index).getSender().equals("user"))
                messageText = (TextView) itemView.findViewById(R.id.my_message);
            else {
                messageText = (TextView) itemView.findViewById(R.id.bot_message);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getAdapterPosition() != -1) {
                            sAClickListener.onRecyclerViewClick(getAdapterPosition());
                        }
                    }
                });
            }
            index++;

        }
    }


}
