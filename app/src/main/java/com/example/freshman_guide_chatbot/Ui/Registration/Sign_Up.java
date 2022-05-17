package com.example.freshman_guide_chatbot.Ui.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freshman_guide_chatbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Sign_Up extends AppCompatActivity
{

    TextView login;
    EditText email,password,confirmPassword;
    Button register;
    ProgressDialog progressDialog;
    FirebaseAuth fbAuth;
    FirebaseUser fbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        progressDialog=new ProgressDialog(this);
        fbAuth=FirebaseAuth.getInstance();
        fbUser=fbAuth.getCurrentUser();

        login=findViewById(R.id.back_to_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplication(),Login.class);
                startActivity(intent);
            }
        });

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirm_password);

        register=findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authentication();
            }
        });


    }

    private void Authentication() {
        String strEmail=email.getText().toString();
        String strPassword=password.getText().toString();
        String strConPassword=confirmPassword.getText().toString();

        fbAuth.fetchSignInMethodsForEmail(strEmail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                boolean isNewUser = task.getResult().getSignInMethods().isEmpty();

                if (!isNewUser)
                    Toast.makeText(getApplication(),"email already exists",Toast.LENGTH_LONG).show();


            }
        });

        if(!isEmail(strEmail))
            email.setError("Please enter a valid email");
        else if(strPassword.isEmpty()||strPassword.length()<6)
            password.setError("Enter correct password Please");
        else if(!strPassword.equals(strConPassword))
            confirmPassword.setError("Password does not match ");
        else
        {
            progressDialog.setMessage("please wait");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            fbAuth.createUserWithEmailAndPassword(strEmail,strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        if(fbUser!=null) {
                            fbUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplication(), "Verification Email Has been Sent", Toast.LENGTH_LONG).show();
                                }
                            });

                            Intent intent = new Intent(getApplication(), Login.class);
                            intent.putExtra("email", strEmail);
                            intent.putExtra("password", strPassword);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            //Toast.makeText(getApplication(),"successful Registration",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getApplication(),"Please enter a valid email",Toast.LENGTH_LONG).show();

                        }
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplication(),"Please enter a valid email",Toast.LENGTH_LONG).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplication(),"Please enter a valid email",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private boolean isEmail(String email)
    {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}