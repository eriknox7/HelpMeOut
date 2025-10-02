package com.example.helpmeoutappuser;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    View v;
    TextView editName, editPhone;
    CircleImageView profileimgview;
    TextView textEmail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    static String name, phone, imageurls;
    String currentUserEmail;
    Button button;


    private static int PICK_IMAGE=123;
    Uri uri;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        editName = v.findViewById(R.id.editname);
        textEmail = v.findViewById(R.id.textemail);
        editPhone = v.findViewById(R.id.editphone);
        profileimgview = v.findViewById(R.id.imgvi);

        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Profile");

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model member = dataSnapshot.getValue(Model.class);
                editName.setText(member.getName());
                textEmail.setText(member.getEmail());
                editPhone.setText(member.getMno());
            /*
                Glide.with(MainScreen.this)
                        .load(member.getImageurl())
                        .into(admin_nav_image);

                 */

            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

                Toast.makeText(getContext(), "Retrieve Failed !", Toast.LENGTH_SHORT).show();


            }
        });



        return v;
    }
}