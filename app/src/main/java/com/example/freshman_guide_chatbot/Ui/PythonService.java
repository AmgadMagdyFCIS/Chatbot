package com.example.freshman_guide_chatbot.Ui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class PythonService extends Service {
    public static PyObject python_module;
    public PythonService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Python

        // create python if not started
        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        // get instance from python to load python scripts
        Python py = Python.getInstance();

        //load python script called hello.py
        python_module = py.getModule("codeFeature").callAttr("Nlp_plus_ANN");

        // call a function called main from hello.py
        PyObject response = python_module.callAttr("response","سالي");
        Toast.makeText(this,"Python is ready",Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}