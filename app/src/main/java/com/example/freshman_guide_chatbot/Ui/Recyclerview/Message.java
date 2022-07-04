package com.example.freshman_guide_chatbot.Ui.Recyclerview;

import com.example.freshman_guide_chatbot.R;

public class Message {
    private String messageText;
    private String uri = "";

    public Message(String messageText, String uri)
    {
        this.messageText = messageText;
        this.uri = uri;

    }

    public Message(String messageText)
    {
        this.messageText = messageText;

    }


    public String getMessageText() {
        return messageText;
    }



    public String getUri() {
        return uri;
    }
}
