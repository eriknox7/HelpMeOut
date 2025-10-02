package com.example.helpmeoutapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class ServiceDetailFragment extends Fragment {
    private ImageView ivServiceImage;
    private TextView tvStoreName, tvOwnerName, tvPhone, tvAddress, tvTiming, tvRating, tvExtraFields;
    private Button btnCall, btnWhatsapp, btnEdit, btnDelete, btnMap;
    private String serviceId, categoryName;
    private DatabaseReference databaseReference;
    private ServiceModel serviceModel;
    private RatingBar ratingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);

        serviceId = getArguments().getString("serviceId");
        categoryName = getArguments().getString("category");

        ivServiceImage = view.findViewById(R.id.iv_service_image);
        tvStoreName = view.findViewById(R.id.tv_store_name);
        tvOwnerName = view.findViewById(R.id.tv_owner_name);
        tvPhone = view.findViewById(R.id.tv_phone);
        tvAddress = view.findViewById(R.id.tv_address);
        tvTiming = view.findViewById(R.id.tv_timing);
        tvRating = view.findViewById(R.id.tv_rating);
        tvExtraFields = view.findViewById(R.id.tv_extra_fields);
        btnCall = view.findViewById(R.id.btn_call);
        btnWhatsapp = view.findViewById(R.id.btn_whatsapp);
        btnEdit = view.findViewById(R.id.btn_edit);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnMap = view.findViewById(R.id.btn_open_map);
        ratingBar = view.findViewById(R.id.ratingBar);

        databaseReference = FirebaseDatabase.getInstance().getReference("Services").child(categoryName).child(serviceId);
        fetchServiceDetails();

        btnCall.setOnClickListener(v -> startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + serviceModel.getPhone()))));

        btnWhatsapp.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + serviceModel.getPhone()));
            startActivity(intent);
        });

        btnEdit.setOnClickListener(v -> showEditPopup());

        btnDelete.setOnClickListener(v -> showDeletePopup());

        btnMap.setOnClickListener(v -> {
            if (serviceModel.getGmapUrl() != null && !serviceModel.getGmapUrl().isEmpty()) {
                String url = serviceModel.getGmapUrl().trim();

                // Ensure the URL starts with "http://" or "https://"
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Location not available", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void fetchServiceDetails() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceModel = snapshot.getValue(ServiceModel.class);
                if (serviceModel != null) {
                    tvStoreName.setText(serviceModel.getStoreName());
                    tvOwnerName.setText("Owner: " + serviceModel.getOwnerName());
                    tvPhone.setText("Phone: " + serviceModel.getPhone());
                    tvAddress.setText("Address: " + serviceModel.getAddress());
                    tvTiming.setText("Open: " + serviceModel.getOpeningTime() + " - " + serviceModel.getClosingTime());
                    ratingBar.setRating(serviceModel.getRating());
                    tvRating.setText("Rating: " + serviceModel.getRating() + " ⭐");
                    Picasso.get().load(serviceModel.getImageUrl()).into(ivServiceImage);

                    // Display Extra Fields
                    StringBuilder extraFieldsText = new StringBuilder();
                    for (Map.Entry<String, String> entry : serviceModel.getDynamicFields().entrySet()) {
                        extraFieldsText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                    }
                    tvExtraFields.setText(extraFieldsText.toString().trim());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_service, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText etStoreName = dialogView.findViewById(R.id.et_store_name);
        EditText etOpeningTime = dialogView.findViewById(R.id.et_opening_time);
        EditText etClosingTime = dialogView.findViewById(R.id.et_closing_time);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel); // Cancel Button

        etStoreName.setText(serviceModel.getStoreName());
        etOpeningTime.setText(serviceModel.getOpeningTime());
        etClosingTime.setText(serviceModel.getClosingTime());

        btnUpdate.setOnClickListener(v -> {
            Map<String, Object> updates = new HashMap<>();
            updates.put("storeName", etStoreName.getText().toString());
            updates.put("openingTime", etOpeningTime.getText().toString());
            updates.put("closingTime", etClosingTime.getText().toString());

            databaseReference.updateChildren(updates)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Updated successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        fetchServiceDetails(); // Refresh UI
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Update failed", Toast.LENGTH_SHORT).show());
        });

        // Cancel button to close the dialog
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showDeletePopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete Service");
        builder.setMessage("Are you sure you want to delete this service?");
        builder.setPositiveButton("Yes", (dialog, which) -> deleteService());
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void deleteService() {
        databaseReference.removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Service deleted successfully", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to delete", Toast.LENGTH_SHORT).show());
    }
}