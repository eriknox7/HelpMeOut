package com.example.helpmeoutappuser;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.helpmeoutappuser.NonessentialServiceFragments.ElectricianFragment;
import com.example.helpmeoutappuser.NonessentialServiceFragments.MechanicFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class NonEssentialServicesFragment extends Fragment {

    private int[] bannerImages = {
            R.drawable.banner1, // Replace with your drawable images
            R.drawable.banner2,
            R.drawable.banner3
    };

    private String[] bannerLinks = {
            "https://www.google.com", // Replace with your links
            "https://www.magicbricks.com/pg-in-baner-pune-pppfr",
            "https://www.vijaysales.com/"
    };

    private ViewFlipper viewFlipper;

    public NonEssentialServicesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_non_essential_services, container, false);


        // Click listeners for each card to open the respective fragment
        view.findViewById(R.id.card_elecrician).setOnClickListener(v -> openFragment(new ElectricianFragment()));
        view.findViewById(R.id.card_mechanic).setOnClickListener(v -> openFragment(new MechanicFragment()));


        viewFlipper = view.findViewById(R.id.viewFlipper);

        for (int i = 0; i < bannerImages.length; i++) {
            final int index = i;
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(bannerImages[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Set OnClickListener to open link in browser
            imageView.setOnClickListener(v -> openLink(bannerLinks[index]));

            viewFlipper.addView(imageView);
        }

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();

        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}