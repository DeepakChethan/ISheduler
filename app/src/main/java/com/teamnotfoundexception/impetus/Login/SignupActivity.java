package com.teamnotfoundexception.impetus.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.teamnotfoundexception.impetus.activities.MainActivity;
import com.teamnotfoundexception.impetus.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailET,passET, phoneEt,collegeEt;
    private Button singIn,signUp;
    private FirebaseAuth mAuth;
    private String email,password;
    private ProgressBar progressBar;
    private String clg,phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        emailET = (EditText) findViewById(R.id.signEmail);
        passET = (EditText) findViewById(R.id.signPass);
        signUp = (Button) findViewById(R.id.signSignUp);
        phoneEt = (EditText) findViewById(R.id.phone);
        collegeEt = (EditText) findViewById(R.id.collegeName);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        singIn.setEnabled(false);
        email = emailET.getText().toString();
        password = passET.getText().toString();
        phn = phoneEt.getText().toString();
        clg = collegeEt.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        if ( email.isEmpty() ) {
            emailET.setError("Email can't be empty");
            return;
        }
        if (password.isEmpty()){
            passET.setError("Password cant be empty");
            return;
        }
        if (phn.isEmpty() || clg.isEmpty()){
            Toast.makeText(getApplicationContext(),"Fill everything up",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isNetworkAvailableAndConnected()) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Network not available",Toast.LENGTH_SHORT).show();
            return ;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Check your creds!",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        } else {
                            makeLogin();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.i("i", "Creating user");
                            progressBar.setVisibility(View.INVISIBLE);
                            finishActivity(900);

                        }
                    }
                });
    }


    public void makeLogin() {
        // Do your thing man TODO

    }

    private boolean isNetworkAvailableAndConnected() {
        // Do your thing man TODO
        return true;
    }
}
