package com.teamnotfoundexception.impetus.LogSign;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.teamnotfoundexception.impetus.R;
import com.teamnotfoundexception.impetus.activities.MainActivity;
import com.teamnotfoundexception.impetus.activities.OrgoActivity;

import java.util.ArrayList;

public class OrganizerLoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mEmail,mPass;
    String email,pass,event;
    Button button;
    FirebaseAuth mAuth;
    public ArrayList<Valid> valids;
    Spinner mEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_login);

        String[] events = getResources().getStringArray(R.array.event_list);

        mAuth = FirebaseAuth.getInstance();
        mEmail = (EditText) findViewById(R.id.orgEmail);
        mPass = (EditText) findViewById(R.id.orgPass);
        button = (Button) findViewById(R.id.orgSignIn);
        button.setOnClickListener(this);
        mEvent = (Spinner) findViewById(R.id.orgEvent);
        valids = new ArrayList<>();
       
        valids.add(new Valid("stealjobs@orgo.com","orgopass",events[0]));
        valids.add(new Valid("itwiz@orgo.com","orgopass",events[1]));
        valids.add(new Valid("techcharads@orgo.com","orgopass",events[2]));
        valids.add(new Valid("vaaksamara@orgo.com","orgopass",events[3]));
        valids.add(new Valid("techmark@orgo.com","orgopass",events[4]));
        valids.add(new Valid("automania@orgo.com","orgopass",events[5]));
        valids.add(new Valid("electrophilia@orgo.com","orgopass",events[6]));
        valids.add(new Valid("spektrom@orgo.com","orgopass",events[7]));
        valids.add(new Valid("over@orgo.com","orgopass",events[8]));
        valids.add(new Valid("appathon@orgo.com","orgopass",events[9]));
        valids.add(new Valid("sumowars@orgo.com","orgopass",events[10]));
        valids.add(new Valid("gaming@orgo.com","orgopass",events[11]));
        valids.add(new Valid("cyborg@orgo.com","orgopass",events[12]));
        valids.add(new Valid("neutraloxide@orgo.com","orgopass",events[13]));
        valids.add(new Valid("summit@orgo.com","orgopass",events[14]));
        valids.add(new Valid("codestorm@orgo.com","orgopass",events[15]));
        valids.add(new Valid("enigma@orgo.com","orgopass",events[16]));
        valids.add(new Valid("marvel@orgo.com","orgopass",events[17]));
        valids.add(new Valid("robo@orgo.com","orgopass",events[18]));
        valids.add(new Valid("mechrbreak@orgo.com","orgopass",events[19]));
        valids.add(new Valid("lasertag@orgo.com","orgopass",events[20]));
    }

    @Override
    public void onClick(View v) {


        email = mEmail.getText().toString();
        pass = mPass.getText().toString();
        event = mEvent.getSelectedItem().toString();
        button.setEnabled(false);

        if (email.isEmpty() || pass.isEmpty()){
            mEmail.setError("Can't be empty");
            mPass.setError("Can't be empty");
            button.setEnabled(true);
            return;
        } else {

            if (validate(email,pass,event)){

                if (!isNetworkAvailableAndConnected()) {
                    Toast.makeText(getApplicationContext(), "Network not available", Toast.LENGTH_SHORT).show();
                    button.setEnabled(true);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Check your creds!",
                                            Toast.LENGTH_SHORT).show();

                                    button.setEnabled(true);
                                    Log.i("e", "successful");

                                } else {
                                    Intent intent = new Intent(getApplicationContext(), OrgoActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finishActivity(900);

                                }
                            }
                        });

            } else {
                button.setEnabled(true);
                Toast.makeText(getApplicationContext(),"Are you an organizer ?",Toast.LENGTH_SHORT).show();

            }





        }

    }
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    public boolean validate(String email,String pass, String event){
        Valid valid = new Valid(email,pass,event);
        for (int i = 0; i < valids.size(); ++i){
            Valid curValid = valids.get(i);
            if (curValid.equale(valid)){
                return true;
            }
        }

        return false;
    }

    class Valid {
        String email,pass,event;

        public boolean equale(Valid obj) {
            if (this.email.equals(obj.email) && this.event.equals(obj.event) && this.pass.equals(obj.pass)) return true;
            return false;
        }

        public Valid(String email, String pass, String event) {
            this.email = email;
            this.pass = pass;
            this.event = event;
        }

    }
}
