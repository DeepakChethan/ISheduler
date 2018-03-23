package com.teamnotfoundexception.impetus.LogSign;


import com.teamnotfoundexception.impetus.Databases.FirebaseHelper.Participant;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnotfoundexception.impetus.activities.MainActivity;
import com.teamnotfoundexception.impetus.R;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailET,passET, phoneEt,collegeEt, departmentEditText;
    private Button signUp;
    private FirebaseAuth mAuth;
    private String email, password;
    private ProgressBar progressBar;
    private String clg,phn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        emailET = (EditText) findViewById(R.id.signEmail);
        passET = (EditText) findViewById(R.id.signPass);
        signUp = (Button) findViewById(R.id.signSignUp);
        phoneEt = (EditText) findViewById(R.id.phone);
        collegeEt = (EditText) findViewById(R.id.collegeName);
        departmentEditText = (EditText) findViewById(R.id.dept);
        progressBar = (ProgressBar) findViewById(R.id.progress);

    }


    @Override
    public void onClick(View view) {

    }

    public void onSignUpButtonClicked(View v) {
        signUp.setEnabled(false);
        email = emailET.getText().toString();

        password = passET.getText().toString();
        phn = phoneEt.getText().toString();
        clg = collegeEt.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        if ( email.isEmpty() ) {
            emailET.setError("Email can't be empty");
            signUp.setEnabled(true);
            return;
        }
        if (password.isEmpty()){
            passET.setError("Password cant be empty");
            signUp.setEnabled(true);
            return;
        }
        if (phn.isEmpty() || clg.isEmpty()){
            Toast.makeText(getApplicationContext(),"Fill everything up",Toast.LENGTH_SHORT).show();
            signUp.setEnabled(true);
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
                            Log.i("i", task.getException().toString());
                            progressBar.setVisibility(View.INVISIBLE);
                            signUp.setEnabled(true);

                        } else {
                            makeSignup();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.i("i", "Creating user");
                            //progressBar.setVisibility(View.INVISIBLE);
                            signUp.setEnabled(true);
                            finishActivity(900);

                        }
                    }
                });
    }


    public void makeSignup() {

        FirebaseAuth Auth = FirebaseAuth.getInstance();
        FirebaseUser mUser = Auth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        if(mUser != null) {

            DatabaseReference databaseReference = firebaseDatabase.getReference("users_private").child(mUser.getUid());

            Map<String, User> userMap = new HashMap<>();

            userMap.put("user_info", new User(mUser.getDisplayName()+"a", mUser.getEmail(),
                                        phoneEt.getText().toString(), collegeEt.getText().toString(), departmentEditText.getText().toString()
                    ));

            databaseReference.setValue(userMap);

            databaseReference = firebaseDatabase.getReference("users_public").child(mUser.getEmail().split("@")[0]);

            databaseReference.setValue(new Participant(mUser.getEmail(), collegeEt.getText().toString(), null));

        }

    }

    private boolean isNetworkAvailableAndConnected() {

        return true;
    }




    public static class User {


        public String mUserName;
        public String mEmailId;
        public String mPhoneNumber;
        public String mCollegeName;
        public String mDepartmentName;

        public User() {

        }

        public User(String userName, String emailId, String PhoneNumber, String collegeName, String departmentName) {
            this.mUserName =userName;
            this.mEmailId = emailId;
            this.mPhoneNumber = PhoneNumber;
            this.mCollegeName = collegeName;
            this.mDepartmentName = departmentName;
        }

    }

}
