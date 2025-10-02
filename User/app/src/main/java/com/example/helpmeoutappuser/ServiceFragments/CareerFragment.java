package com.example.helpmeoutappuser.ServiceFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.helpmeoutappuser.CafeAdapter;
import com.example.helpmeoutappuser.CafeModel;
import com.example.helpmeoutappuser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CareerFragment extends Fragment {


    private RecyclerView recyclerView;
    private CafeAdapter cafeAdapter;
    private List<CafeModel> cafeList;
    private DatabaseReference databaseReference;

    private TextView tvEmpty; // Empty View
    public CareerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_career, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cafeList = new ArrayList<>();
        cafeAdapter = new CafeAdapter(getContext(), cafeList);
        tvEmpty = view.findViewById(R.id.tv_empty); // Initialize Empty View
        recyclerView.setAdapter(cafeAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Services").child("Career Building");
        fetchCafeData();

        return view;
    }

    private void fetchCafeData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cafeList.clear();

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CafeModel model = dataSnapshot.getValue(CafeModel.class);
                        cafeList.add(model);
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE); // Hide empty message when data exists
                } else {
                    recyclerView.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE); // Show empty message if no data exists
                }

                cafeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}