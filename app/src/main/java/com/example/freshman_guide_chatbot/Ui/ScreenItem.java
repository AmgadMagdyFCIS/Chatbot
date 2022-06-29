package com.example.freshman_guide_chatbot.Ui;

public class ScreenItem
{
    String Title , Descreption;
    int ScreenImg;
    public ScreenItem(String title,  String descreption,int screenImg)
    {
        Title=title;
        Descreption=descreption;
        ScreenImg= screenImg;
    }
    public void setTitle(String title)
    {
        Title=title;

    }
    public void setDescreption(String descreption)
    {
        Descreption=descreption;

    }
    public String getTitle()
    {
        return Title;
    }
    public String getDescreption()
    {
        return Descreption;
    }
    public int getScreenImg()
    {
        return ScreenImg;
    }




}
