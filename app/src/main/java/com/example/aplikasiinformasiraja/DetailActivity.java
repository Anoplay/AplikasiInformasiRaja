package com.example.aplikasiinformasiraja;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {
    ImageView imgDetailRaja;
    TextView tvNama, tvPeriode, tvDeskripsi;
    Button btnEdit, btnHapus;
    LinearLayout layoutAdminButtons;
    DatabaseHelper db;
    String rajaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgDetailRaja = findViewById(R.id.img_detail_raja);
        tvNama = findViewById(R.id.tv_detail_nama_raja);
        tvPeriode = findViewById(R.id.tv_detail_periode_raja);
        tvDeskripsi = findViewById(R.id.tv_detail_deskripsi);
        btnEdit = findViewById(R.id.btn_edit);
        btnHapus = findViewById(R.id.btn_hapus);
        layoutAdminButtons = findViewById(R.id.layout_admin_buttons);
        db = new DatabaseHelper(this);

        rajaId = getIntent().getStringExtra("RAJA_ID");

        loadRajaData();

        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        boolean isGuest = sharedPreferences.getBoolean("isGuest", false);
        if (!isGuest) {
            layoutAdminButtons.setVisibility(View.VISIBLE);
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, AddEditActivity.class);
            intent.putExtra("RAJA_ID", rajaId);
            startActivity(intent);
        });

        btnHapus.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Hapus Data")
                    .setMessage("Apakah Anda yakin ingin menghapus data raja ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        Integer deletedRows = db.deleteKing(rajaId);
                        if (deletedRows > 0) {
                            Toast.makeText(this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        });
    }

    private void loadRajaData() {
        Cursor cursor = db.getKingById(rajaId);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_NAME));
            String reign = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_REIGN));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_DESC));
            String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_IMAGE_PATH));

            tvNama.setText(name);
            tvPeriode.setText(reign);
            tvDeskripsi.setText(description);
            Glide.with(this).load(Uri.parse(imagePath)).into(imgDetailRaja);
            cursor.close();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRajaData();
    }
}