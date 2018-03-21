package com.teamnotfoundexception.impetus.LogSign;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.activities.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText emailET, passET;
    private Button signIn, signUp;
    private Button oSignIn;
    private FirebaseAuth mAuth;
    private String email, password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        emailET = (EditText) findViewById(R.id.logEmail);
        passET = (EditText) findViewById(R.id.logPass);
        signIn = (Button) findViewById(R.id.logSignIn);
        signUp = (Button) findViewById(R.id.logSignUp);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        oSignIn = (Button) findViewById(R.id.logOrgSignIn);
        oSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,OrganizerLoginActivity.class));
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        signIn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        signIn.setEnabled(false);
        email = emailET.getText().toString();
        password = passET.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        if (email.isEmpty()) {
            emailET.setError("Email can't be empty");
            signIn.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (password.isEmpty()) {
            passET.setError("Password cant be empty");
            signIn.setEnabled(true);
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        if (!isNetworkAvailableAndConnected()) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Network not available", Toast.LENGTH_SHORT).show();
            signIn.setEnabled(true);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Check your creds!",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            signIn.setEnabled(true);
                        } else {
                            FirebaseMessaging.getInstance().subscribeToTopic("ENIGMA");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.i("i", "logging in");
                            progressBar.setVisibility(View.INVISIBLE);
                            finishActivity(900);
                            System.out.println("token is " + FirebaseInstanceId.getInstance().getToken());
                        }
                    }
                });

    }


    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }


}
