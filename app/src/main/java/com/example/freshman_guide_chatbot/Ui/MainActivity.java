package com.example.freshman_guide_chatbot.Ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freshman_guide_chatbot.R;

/*import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;*/

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*TextView hello = findViewById(R.id.hello);

        // create python if not started
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        // get instance from python to load python scripts
        Python py = Python.getInstance();

        //load python script called hello.py
        final PyObject python_module = py.getModule("hello");

        // call a function called main from hello.py
        PyObject obj = python_module.callAttr("main");

        // set text of textview to text returned from main function in the hello script
        hello.setText(obj.toString());*/

    }

}