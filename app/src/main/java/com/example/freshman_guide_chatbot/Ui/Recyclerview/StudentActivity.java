package com.example.freshman_guide_chatbot.Ui.Recyclerview;

public class StudentActivity {
    String imagesrc;
    String link;
    public StudentActivity(String imagesrc,String link)
    {
        this.imagesrc=imagesrc;
        this.link=link;
    }

    public String getImagesrc() {
        return imagesrc;
    }

    public String getLink() {
        return link;
    }
}
