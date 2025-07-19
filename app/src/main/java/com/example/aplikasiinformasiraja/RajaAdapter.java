package com.example.aplikasiinformasiraja;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class RajaAdapter extends RecyclerView.Adapter<RajaAdapter.ViewHolder> {
    private Context context;
    private List<Raja> rajaList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Raja raja);
    }

    public RajaAdapter(Context context, List<Raja> rajaList, OnItemClickListener listener) {
        this.context = context;
        this.rajaList = rajaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_raja, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Raja raja = rajaList.get(position);
        holder.tvNama.setText(raja.getName());
        holder.tvPeriode.setText(raja.getReign());

        String imagePath = raja.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            holder.imgRaja.setImageTintList(null);
            Glide.with(context)
                    .load(Uri.parse(imagePath))
                    .placeholder(R.drawable.ic_add_crown)
                    .error(R.drawable.ic_add_crown)
                    .into(holder.imgRaja);
        } else {
            holder.imgRaja.setImageResource(R.drawable.ic_add_crown);
            holder.imgRaja.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.soft_gold)));
        }

        holder.itemView.setOnClickListener(v -> listener.onItemClick(raja));
    }

    @Override
    public int getItemCount() {
        return rajaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRaja;
        TextView tvNama, tvPeriode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRaja = itemView.findViewById(R.id.img_raja);
            tvNama = itemView.findViewById(R.id.tv_nama_raja);
            tvPeriode = itemView.findViewById(R.id.tv_periode_raja);
        }
    }
}