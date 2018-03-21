package com.teamnotfoundexception.impetus.LogSign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.teamnotfoundexception.impetus.R;

public class OrganizerLoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText mEmail,mPass;
    String email,pass,event;
    Button button;
    Spinner mEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_login);
        mEmail = (EditText) findViewById(R.id.orgEmail);
        mPass = (EditText) findViewById(R.id.orgPass);
        button = (Button) findViewById(R.id.orgSignIn);
        mEvent = (Spinner) findViewById(R.id.orgEvent);
    }

    @Override
    public void onClick(View v) {
        email = mEmail.getText().toString();
        pass = mPass.getText().toString();
        event = mEvent.getSelectedItem().toString().toLowerCase();
        button.setEnabled(false);

        if (email.isEmpty() || pass.isEmpty()){
            mEmail.setError("Can't be empty");
            mPass.setError("Can't be empty");
            button.setEnabled(true);
            return;
        }else{

            //TODO validate and login the organizer of event


        }

    }
}
