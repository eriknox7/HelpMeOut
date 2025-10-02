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
import com.example.helpmeoutappuser.ServiceFragments.CafeFragment;
import com.example.helpmeoutappuser.ServiceFragments.CareerFragment;
import com.example.helpmeoutappuser.ServiceFragments.CyberCafeFragment;
import com.example.helpmeoutappuser.ServiceFragments.DoctorFragment;
import com.example.helpmeoutappuser.ServiceFragments.EventFragment;
import com.example.helpmeoutappuser.ServiceFragments.LearningFragment;
import com.example.helpmeoutappuser.ServiceFragments.RentalFragment;
import com.example.helpmeoutappuser.ServiceFragments.TiffinFragment;
import com.example.helpmeoutappuser.ServiceFragments.WaterFragment;
import com.example.helpmeoutappuser.ServiceFragments.WorkshopFragment;
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


public class HomeFragment extends Fragment {



    private int[] bannerImages = {
            R.drawable.iqooneo, // Replace with your drawable images
            R.drawable.realme,
            R.drawable.safari,
            R.drawable.infinix,
            R.drawable.macbook
    };

    private String[] bannerLinks = {
            "https://www.amazon.in/dp/B0DW47XR3X/?pd_rd_plhdr=t&aref=ayQNH7QYdb&aaxitk=365807d2565a64b840c2d20807fdd43c&pf_rd_r=WTDYYN59X6EPEWN2M3Y7", // Replace with your links
            "https://www.flipkart.com/realme-p3-5g-coming-soon-store?ctx=eyJjYXJkQ29udGV4dCI6eyJhdHRyaWJ1dGVzIjp7InNvdXJjZUNvbnRlbnRUeXBlIjp7InNpbmdsZVZhbHVlQXR0cmlidXRlIjp7ImtleSI6InNvdXJjZUNvbnRlbnRUeXBlIiwiaW5mZXJlbmNlVHlwZSI6IlNDVCIsInZhbHVlIjoiQUQiLCJ2YWx1ZVR5cGUiOiJTSU5HTEVfVkFMVUVEIn19fX19&nnc=O4XSE41KUCHX_AD&pageUID=1742199733760",
            "https://www.flipkart.com/reh/plk/~cs-xuq5fvydjy/pr?sid=reh%2Cplk&-tab-name=Safari&pageCriteria=BlueAndMall&ctx=eyJjYXJkQ29udGV4dCI6eyJhdHRyaWJ1dGVzIjp7InNvdXJjZUNvbnRlbnRUeXBlIjp7InNpbmdsZVZhbHVlQXR0cmlidXRlIjp7ImtleSI6InNvdXJjZUNvbnRlbnRUeXBlIiwiaW5mZXJlbmNlVHlwZSI6IlNDVCIsInZhbHVlIjoiQUQiLCJ2YWx1ZVR5cGUiOiJTSU5HTEVfVkFMVUVEIn19fX19&nnc=PTO33MLSG6HL_AD&pageUID=1742199782832",
            "https://www.flipkart.com/infinix-note-50x-coming-soon-store?ctx=eyJjYXJkQ29udGV4dCI6eyJhdHRyaWJ1dGVzIjp7InNvdXJjZUNvbnRlbnRUeXBlIjp7InNpbmdsZVZhbHVlQXR0cmlidXRlIjp7ImtleSI6InNvdXJjZUNvbnRlbnRUeXBlIiwiaW5mZXJlbmNlVHlwZSI6IlNDVCIsInZhbHVlIjoiQUQiLCJ2YWx1ZVR5cGUiOiJTSU5HTEVfVkFMVUVEIn19fX19&nnc=S8G4C5VGMQMF_AD&pageUID=1742199821649",
            "https://www.flipkart.com/6bo/b5g/~cs-o99fwwy59y/pr?sid=6bo%2Cb5g&collection-tab-name=Apple+Macbook+Air+M2&pageCriteria=BlueAndMall&ctx=eyJjYXJkQ29udGV4dCI6eyJhdHRyaWJ1dGVzIjp7InNvdXJjZUNvbnRlbnRUeXBlIjp7InNpbmdsZVZhbHVlQXR0cmlidXRlIjp7ImtleSI6InNvdXJjZUNvbnRlbnRUeXBlIiwiaW5mZXJlbmNlVHlwZSI6IlNDVCIsInZhbHVlIjoiQUQiLCJ2YWx1ZVR5cGUiOiJTSU5HTEVfVkFMVUVEIn19fX19&nnc=DSGIE763R3AF_AD&pageUID=1742199847785"
    };

    private ViewFlipper viewFlipper;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Click listeners for each card to open the respective fragment
        view.findViewById(R.id.card_tiffin).setOnClickListener(v -> openFragment(new TiffinFragment()));
        view.findViewById(R.id.card_rental).setOnClickListener(v -> openFragment(new RentalFragment()));
        view.findViewById(R.id.card_water).setOnClickListener(v -> openFragment(new WaterFragment()));
        view.findViewById(R.id.card_event).setOnClickListener(v -> openFragment(new EventFragment()));
        view.findViewById(R.id.card_learning).setOnClickListener(v -> openFragment(new LearningFragment()));
        view.findViewById(R.id.card_cafe).setOnClickListener(v -> openFragment(new CafeFragment()));
        view.findViewById(R.id.card_cyber).setOnClickListener(v -> openFragment(new CyberCafeFragment()));
        view.findViewById(R.id.card_doctor).setOnClickListener(v -> openFragment(new DoctorFragment()));
        view.findViewById(R.id.card_workshop).setOnClickListener(v -> openFragment(new WorkshopFragment()));
        view.findViewById(R.id.card_career).setOnClickListener(v -> openFragment(new CareerFragment()));


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
        viewFlipper.setFlipInterval(5000);
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