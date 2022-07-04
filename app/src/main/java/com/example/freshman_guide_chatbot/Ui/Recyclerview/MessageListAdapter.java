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

public class MessageListAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Message> messages;

    private SAClickListener sAClickListener;

    public MessageListAdapter(Context context, List<Message> messages ,SAClickListener sAClickListener) {
        this.context = context;
        this.messages = messages;
        /*if(messages.size()>1)
            index = messages.size() - 2;*/


        this.sAClickListener=sAClickListener;


    }

    @Override
    public int getItemViewType(int position) {
        return position%2 ;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0)
            return new BotViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_query, parent, false));
        else
            return new UserViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_query, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Message message = messages.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                BotViewHolder0 botViewHolder0 = (BotViewHolder0)holder;
                botViewHolder0.messageText.setText(message.getMessageText());
                break;

            case 1:
                UserViewHolder1 userViewHolder1 = (UserViewHolder1)holder;
                userViewHolder1.messageText.setText(message.getMessageText());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class BotViewHolder0 extends RecyclerView.ViewHolder {
        TextView messageText;
        public BotViewHolder0(@NonNull View itemView){
            super(itemView);
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
    }

    class UserViewHolder1 extends RecyclerView.ViewHolder {
        TextView messageText;

        public UserViewHolder1(@NonNull View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.my_message);
        }
    }



}
