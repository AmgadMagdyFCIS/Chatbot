package com.example.freshman_guide_chatbot.Ui.Recyclerview;

public class Message {
    private String messageText;
    private String sender;
    private String uri="";
    public Message(String messageText,String sender,String uri)
    {
        this.messageText=messageText;
        this .sender=sender;
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
