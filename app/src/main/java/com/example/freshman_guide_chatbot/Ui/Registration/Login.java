package com.example.freshman_guide_chatbot.Ui.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freshman_guide_chatbot.R;
import com.example.freshman_guide_chatbot.Ui.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextView createAccount, forgetPassword;
    EditText email, password;
    Button btn_login;
    ImageView SignInWithGoogle;
    ProgressDialog progressDialog;
    FirebaseAuth fbAuth;
    FirebaseUser fbUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        createAccount = findViewById(R.id.createNewAcc);
        forgetPassword = findViewById(R.id.forgotpassword);
        email = findViewById(R.id.InputEmail);


        password = findViewById(R.id.InputPassword);
        btn_login = findViewById(R.id.btn_Login);
        SignInWithGoogle = findViewById(R.id.btn_google);
        progressDialog = new ProgressDialog(this);
        fbAuth = FirebaseAuth.getInstance();
        fbUser = fbAuth.getCurrentUser();
        try {
            email.setText(getIntent().getExtras().getString("email", ""));
            password.setText(getIntent().getExtras().getString("password", ""));
        } catch (Exception e) {
            if (fbUser != null) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        }


        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplication(), ForgetPassword.class);
                startActivity(i);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), Sign_Up.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogIn();
            }
        });


        SignInWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SignInWithGoogle.class);
                startActivity(intent);
            }
        });


    }

    private void LogIn() {

        String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();


        if (!isEmail(strEmail))
            email.setError("Please enter a valid email");
        else if (strPassword.isEmpty() || strPassword.length() < 6)
            password.setError("Enter correct password Please");

        else {
            progressDialog.setMessage("please wait");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            fbAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        fbUser = fbAuth.getCurrentUser();
                        if (fbUser.isEmailVerified()) {
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                            Toast.makeText(getApplication(), "successful Login", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getApplication(), "please verify your email", Toast.LENGTH_LONG).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplication(), "" + task.getException(), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    private boolean isEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}