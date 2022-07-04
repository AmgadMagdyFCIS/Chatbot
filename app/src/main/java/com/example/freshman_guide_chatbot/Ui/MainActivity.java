package com.example.freshman_guide_chatbot.Ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import com.example.freshman_guide_chatbot.R;

import com.example.freshman_guide_chatbot.Ui.Fragments.AboutUsFragment;
import com.example.freshman_guide_chatbot.Ui.Fragments.ChatFragment;
import com.example.freshman_guide_chatbot.Ui.Fragments.InformationFragment;
import com.example.freshman_guide_chatbot.Ui.Fragments.StudentsActivitiesFragment;
import com.example.freshman_guide_chatbot.Ui.Registration.Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    public static PyObject python_module;
    public static String _uri;


    ActionBarDrawerToggle actionBarDrawerToggle;    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    GoogleSignInClient GoogleClient;
    private static final int RC_SIGN_IN = 101;

    String nav="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            nav=getIntent().getExtras().getString("nav","");

        }catch (Exception e){}
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        if(nav.equals("nav"))
        {
            fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.container, new ChatFragment());
            fragmentTransaction.commit();
        }
        else {
            final dialog loadingdialog = new dialog(this);
            loadingdialog.startLoadingdialog();


            // using handler class to set time delay methods
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    // after 4 seconds
                    waiting();
                    loadingdialog.dismissdialog();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.add(R.id.container, new ChatFragment());
                    fragmentTransaction.commit();
                }

            }, 1000); // 4 seconds
        }
    }

    void waiting()
    {
        storageRef.child("images/Timetable.pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                _uri=uri.toString();
            }
        });
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
        Toast.makeText(this,"أنا جاهز لمساعدتك",Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId())
        {
            case R.id.chat_home:
                transaction(new ChatFragment());
                return true;
            case R.id.collage_info:
                transaction(new InformationFragment());
                return true;

            case R.id.guide:
                Intent intent = new Intent(getApplication(), IntroActivity.class);
                intent.putExtra("nav", "nav");
                startActivity(intent);
                return true;

            case R.id.students_activity:
                transaction(new StudentsActivitiesFragment());
                return true;

            case R.id.about_us:
                transaction(new AboutUsFragment());
                return true;
            case R.id.log_out:
                SignOut();
                return true;

        }
        return true;
    }

    public void transaction(Fragment fragment)
    {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);//f
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void SignOut()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleClient = GoogleSignIn.getClient(this,gso);
        GoogleClient.signOut();
        mAuth.signOut();
        Intent intent = new Intent(getApplication(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

}