package com.example.freshman_guide_chatbot.Ui.Recyclerview;

import com.example.freshman_guide_chatbot.R;

public class Message {
    private String messageText;
    private String sender;
    private String uri="";
    private int layout;
    public Message(String messageText,String sender,String uri )
    {
        this.messageText=messageText;
        this .sender=sender;
        if(sender.equals("user"))
            layout= R.id.my_message;
        else
            layout=R.id.bot_message;
        this.uri=uri;
    }
    public Message(String messageText,String sender)
    {
        this.messageText=messageText;
        this .sender=sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getSender() {
        return sender;
    }

    public String getUri() {
        return uri;
    }
}
