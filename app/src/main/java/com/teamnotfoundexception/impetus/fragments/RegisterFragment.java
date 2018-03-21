package com.teamnotfoundexception.impetus.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.Databases.FirebaseHelper;
import com.teamnotfoundexception.impetus.Databases.StatusManager;
import com.teamnotfoundexception.impetus.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {


    EditText mTeamMembers,mCollege,mPhone,mTeam;
    Button btn;
    EventItem eventItem;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        Bundle bundle = getArguments();
        if (bundle == null) {
            Toast.makeText(getContext(), "Something is wrong with your phone!", Toast.LENGTH_SHORT).show();
            return view;
        }
        mTeam = (EditText) view.findViewById(R.id.regTeamName);
        eventItem = (EventItem) bundle.getSerializable("dope");
        mTeamMembers = (EditText) view.findViewById(R.id.regTeamMem);
        mCollege = (EditText) view.findViewById(R.id.regCollege);
        mPhone = (EditText) view.findViewById(R.id.regPhone);
        btn = (Button) view.findViewById(R.id.regRegister);
        btn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        String teamName = mTeam.getText().toString();
        String[] teamMembers= mTeamMembers.getText().toString().split(",");
        ArrayList<String> teamMembersList = new ArrayList<>();
        for(int i = 0; i < teamMembers.length; i++) {
            teamMembersList.add(teamMembers[0]);
        }
        String collegeName = mCollege.getText().toString();

        FirebaseHelper.Participant participant = new FirebaseHelper.Participant(teamName, collegeName, teamMembersList);

        StatusManager.get(getActivity().getApplicationContext()).addToRegistered(eventItem, participant);
    }
}
