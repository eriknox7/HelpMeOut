package com.example.helpmeoutappuser;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.ViewHolder> {
    private Context context;
    private List<CafeModel> cafeList;

    public CafeAdapter(Context context, List<CafeModel> cafeList) {
        this.context = context;
        this.cafeList = cafeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cyber_cafe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CafeModel model = cafeList.get(position);

        holder.tvStoreName.setText(model.getStoreName());
        holder.tvAddress.setText(model.getAddress());
        holder.tvTiming.setText("Open: " + model.getOpeningTime() + " - " + model.getClosingTime());
        holder.ratingBar.setRating(model.getRating());
        Picasso.get().load(model.getImageUrl()).into(holder.ivServiceImage);

        // Show Extra Dynamic Fields
        StringBuilder extraFieldsText = new StringBuilder();
        for (Map.Entry<String, String> entry : model.getDynamicFields().entrySet()) {
            extraFieldsText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        holder.tvExtraFields.setText(extraFieldsText.toString().trim());

        // Call Button
        holder.btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getPhone()));
            context.startActivity(intent);
        });

        // WhatsApp Button
        holder.btnWhatsapp.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + model.getPhone()));
            context.startActivity(intent);
        });


            // Open `ServiceDetailFragment` when clicking on the card
            holder.itemView.setOnClickListener(v -> {
                ServiceDetailFragment fragment = new ServiceDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("serviceId", model.getId());
                bundle.putString("category", model.getCategory());
                fragment.setArguments(bundle);

                FragmentActivity activity = (FragmentActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
    }

    @Override
    public int getItemCount() {
        return cafeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivServiceImage;
        TextView tvStoreName, tvAddress, tvTiming, tvExtraFields;
        RatingBar ratingBar;
        Button btnCall, btnWhatsapp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivServiceImage = itemView.findViewById(R.id.iv_service_image);
            tvStoreName = itemView.findViewById(R.id.tv_store_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvTiming = itemView.findViewById(R.id.tv_timing);
            tvExtraFields = itemView.findViewById(R.id.tv_extra_fields);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            btnCall = itemView.findViewById(R.id.btn_call);
            btnWhatsapp = itemView.findViewById(R.id.btn_whatsapp);
        }
    }
}
