package com.example.freshman_guide_chatbot.Ui.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.freshman_guide_chatbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPassword extends AppCompatActivity {

    EditText email;
    Button resetPassword;
    ProgressDialog progressDialog;
    FirebaseAuth fbAuth;
    FirebaseUser fbUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        getSupportActionBar().hide();
        progressDialog=new ProgressDialog(this);
        fbAuth=FirebaseAuth.getInstance();
        fbUser=fbAuth.getCurrentUser();

        email=findViewById(R.id.email);
        resetPassword=findViewById(R.id.btn_reset_password);
        resetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(email.getText().toString().equals(""))
                    Toast.makeText(getApplication(),"Please enter ur email",Toast.LENGTH_LONG).show();
                else
                {
                    progressDialog.setMessage("please wait");
                    progressDialog.setTitle("Reset Password");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    fbAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                                Toast.makeText(getApplication(), "check ur email", Toast.LENGTH_LONG).show();

                            else
                                Toast.makeText(getApplication(),"An unexpected error occurred. please enter a valid email",Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
                }

            }
        });
    }
}