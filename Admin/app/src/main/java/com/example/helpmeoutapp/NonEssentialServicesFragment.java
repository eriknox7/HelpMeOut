package com.example.helpmeoutapp;

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

import com.example.helpmeoutapp.NonessentialServiceFragments.ElectricianFragment;
import com.example.helpmeoutapp.NonessentialServiceFragments.MechanicFragment;
import com.example.helpmeoutapp.ServiceFragments.CafeFragment;
import com.example.helpmeoutapp.ServiceFragments.CareerFragment;
import com.example.helpmeoutapp.ServiceFragments.CyberCafeFragment;
import com.example.helpmeoutapp.ServiceFragments.DoctorFragment;
import com.example.helpmeoutapp.ServiceFragments.EventFragment;
import com.example.helpmeoutapp.ServiceFragments.LearningFragment;
import com.example.helpmeoutapp.ServiceFragments.RentalFragment;
import com.example.helpmeoutapp.ServiceFragments.TiffinFragment;
import com.example.helpmeoutapp.ServiceFragments.WaterFragment;
import com.example.helpmeoutapp.ServiceFragments.WorkshopFragment;
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

    private static final int PICK_IMAGE_REQUEST = 1;
    private FloatingActionButton fabAddService;
    private Spinner categorySpinner;
    private EditText etStoreName, etOwnerName, etPhone, etAddress, etPrice, etOpeningTime, etClosingTime, etGmapUrl;

    RatingBar ratingBar;
    private LinearLayout layoutDynamicFields;
    private ImageView ivServiceImage;
    private Uri imageUri;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;
    Map<String, String[]> categoryFields = new HashMap<>();

    public NonEssentialServicesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_non_essential_services, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Services");
        fabAddService = view.findViewById(R.id.fab_add_service);
        fabAddService.setOnClickListener(v -> showAddServicePopup());

        // Click listeners for each card to open the respective fragment
        view.findViewById(R.id.card_elecrician).setOnClickListener(v -> openFragment(new ElectricianFragment()));
        view.findViewById(R.id.card_mechanic).setOnClickListener(v -> openFragment(new MechanicFragment()));


        return view;
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showAddServicePopup() {
        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_add_service);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        categorySpinner = dialog.findViewById(R.id.sp_category);
        etStoreName = dialog.findViewById(R.id.et_store_name);
        etOwnerName = dialog.findViewById(R.id.et_owner_name);
        etPhone = dialog.findViewById(R.id.et_phone);
        etAddress = dialog.findViewById(R.id.et_address);
        etPrice = dialog.findViewById(R.id.et_price);
        etOpeningTime = dialog.findViewById(R.id.et_opening_time);
        etClosingTime = dialog.findViewById(R.id.et_closing_time);
        layoutDynamicFields = dialog.findViewById(R.id.layout_dynamic_fields);
        ivServiceImage = dialog.findViewById(R.id.iv_service_image);
        Button uploadImage = dialog.findViewById(R.id.btn_upload_image);
        Button submitButton = dialog.findViewById(R.id.btn_submit);

        etGmapUrl = dialog.findViewById(R.id.et_gmap_url);
        ratingBar = dialog.findViewById(R.id.ratingBar);

        // Initialize Progress Dialog
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Saving Service...");
        progressDialog.setCancelable(false);

        // Time Picker Setup
        etOpeningTime.setOnClickListener(v -> showTimePicker(etOpeningTime));
        etClosingTime.setOnClickListener(v -> showTimePicker(etClosingTime));

        // Populate Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Electrician", "Mechanic"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Dynamic Fields Update
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDynamicFields(categorySpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Image Picker
        uploadImage.setOnClickListener(v -> openImagePicker());

        submitButton.setOnClickListener(v -> {
            progressDialog.show();
            uploadData(dialog);
        });


        dialog.show();
    }

    private void updateDynamicFields(String category) {
        layoutDynamicFields.removeAllViews();

        // Define mandatory fields for each category


        categoryFields.put("Electrician", new String[]{"Services Offered", "Emergency Service Available", "Experience"});
        categoryFields.put("Mechanic", new String[]{"Vehicle Type", "Specialization", "At Home Service"});

        // Check if the category has predefined fields
        if (categoryFields.containsKey(category)) {
            for (String field : categoryFields.get(category)) {
                EditText dynamicField = new EditText(requireContext());
                dynamicField.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dynamicField.setHint(field);
                layoutDynamicFields.addView(dynamicField);
            }
            layoutDynamicFields.setVisibility(View.VISIBLE);
        } else {
            layoutDynamicFields.setVisibility(View.GONE);
        }
    }


    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            ivServiceImage.setImageURI(imageUri);
        }
    }

    private void uploadData(Dialog dialog) {
        if (imageUri != null) {
            String category = categorySpinner.getSelectedItem().toString();
            String serviceId = UUID.randomUUID().toString();

            uploadImageToSecondaryFirebase(imageUri, category, serviceId, dialog);
        } else {
            Toast.makeText(getContext(), "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void showTimePicker(EditText timeField) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                (view, selectedHour, selectedMinute) -> {
                    String formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    timeField.setText(formattedTime);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void uploadImageToSecondaryFirebase(Uri imageUri, String category, String serviceId, Dialog dialog) {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId("cloud-burst-predication-app")
                .setApplicationId("1:1058676005319:android:c0b4e85067e088fc6a21e2")
                .setApiKey("YOUR_API_KEY")
                .setStorageBucket("cloud-burst-predication-app.appspot.com")
                .build();

        FirebaseApp secondaryApp;
        try {
            secondaryApp = FirebaseApp.getInstance("SecondaryFirebase");
        } catch (IllegalStateException e) {
            secondaryApp = FirebaseApp.initializeApp(requireContext(), options, "SecondaryFirebase");
        }

        FirebaseStorage secondaryStorage = FirebaseStorage.getInstance(secondaryApp);
        StorageReference storageRef = secondaryStorage.getReference().child("service_images/" + serviceId);

        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> saveServiceData(category, serviceId, uri.toString(), dialog))
                )
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Image Upload Failed", Toast.LENGTH_SHORT).show());
    }

    private void saveServiceData(String category, String serviceId, String imageUrl, Dialog dialog) {
        Map<String, String> dynamicData = new HashMap<>();
        String[] fieldNames = categoryFields.get(category);
        for (int i = 0; i < layoutDynamicFields.getChildCount(); i++) {
            assert fieldNames != null;
            String fieldName = fieldNames[i];
            EditText field = (EditText) layoutDynamicFields.getChildAt(i);
            dynamicData.put(fieldName, field.getText().toString());
        }

        // Get values from fields
        String storeName = etStoreName.getText().toString().trim();
        String ownerName = etOwnerName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String openingTime = etOpeningTime.getText().toString().trim();
        String closingTime = etClosingTime.getText().toString().trim();
        String gmapUrl = etGmapUrl.getText().toString().trim();
        float rating = ratingBar.getRating();

        // Get the selected category name from the spinner
        String selectedCategory = categorySpinner.getSelectedItem().toString();

        // Store in Firebase
        Map<String, Object> service = new HashMap<>();
        service.put("id", serviceId);
        service.put("category", selectedCategory); // Store the selected category
        service.put("storeName", storeName);
        service.put("ownerName", ownerName);
        service.put("phone", phone);
        service.put("address", address);
        service.put("price", price);
        service.put("openingTime", openingTime);
        service.put("closingTime", closingTime);
        service.put("gmapUrl", gmapUrl);
        service.put("rating", rating);
        service.put("imageUrl", imageUrl);
        service.put("dynamicFields", dynamicData);

        databaseReference.child(selectedCategory).child(serviceId).setValue(service)
                .addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Service Created Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed to Save Service", Toast.LENGTH_SHORT).show();
                });
    }
}