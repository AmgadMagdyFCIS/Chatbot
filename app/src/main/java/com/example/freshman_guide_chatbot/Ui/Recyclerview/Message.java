package com.example.freshman_guide_chatbot.Ui.Recyclerview;

public class Message {
    private String messageText;
    private String sender;
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
}
