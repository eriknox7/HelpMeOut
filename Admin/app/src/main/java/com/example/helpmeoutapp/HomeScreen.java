package com.example.helpmeoutapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawers;
    View headerViw1;
    CircleImageView admin_nav_image;
    private TextView nav_name;
    private TextView nav_email;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public String resulturl="";
    private DatabaseReference databaseReference1;
    private ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        // Firebase Initialization

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users").child(firebaseAuth.getCurrentUser().getUid());
        databaseReference.keepSynced(true);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawers = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Drawer


        headerViw1=navigationView.getHeaderView(0);
        nav_name=(TextView)headerViw1.findViewById(R.id.username);
        nav_email=(TextView)headerViw1.findViewById(R.id.usermail);
        admin_nav_image=headerViw1.findViewById(R.id.imageView);

        navigationView.setNavigationItemSelectedListener(this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Model member = dataSnapshot.getValue(Model.class);
                assert member != null;
                nav_name.setText(member.getName());
                nav_email.setText(member.getEmail());
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), "Retrieve Failed !", Toast.LENGTH_SHORT).show();


            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawers, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawers.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (drawers.isDrawerOpen(GravityCompat.START)){
            drawers.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_nonessential:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new NonEssentialServicesFragment()).commit();
                break;

            case R.id.nav_video:
                 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VideoFragment()).commit();
                break;

            case R.id.nav_profiles:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.nav_privacy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PrivacyFragment()).commit();
                break;

            case R.id.nav_aboutapp:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AboutAppFragment()).commit();
               // startActivity(new Intent(MainScreen.this,About_App.class));
                break;

            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(getApplicationContext(), "User LogOut...", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;
        }

        drawers.closeDrawer(GravityCompat.START);
        return true;
    }

}