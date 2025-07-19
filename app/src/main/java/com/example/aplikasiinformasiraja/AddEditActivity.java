package com.example.aplikasiinformasiraja;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int SEARCH_REQUEST_CODE = 2;

    ImageView imgRaja;
    TextInputEditText etNama, etPeriode, etDeskripsi;
    Button btnSimpan, btnPilihGambar;
    EditText etSearchOnline;
    ImageButton btnSearchOnline;

    private Uri imageUri;
    private String imagePath;
    private DatabaseHelper db;
    private String currentRajaId;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        db = new DatabaseHelper(this);

        imgRaja = findViewById(R.id.img_add_edit_raja);
        etNama = findViewById(R.id.et_nama_raja);
        etPeriode = findViewById(R.id.et_periode_raja);
        etDeskripsi = findViewById(R.id.et_deskripsi_raja);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnPilihGambar = findViewById(R.id.btn_pilih_gambar);
        etSearchOnline = findViewById(R.id.et_search_online);
        btnSearchOnline = findViewById(R.id.btn_search_online);

        if (getIntent().hasExtra("RAJA_ID")) {
            isEditMode = true;
            currentRajaId = getIntent().getStringExtra("RAJA_ID");
            loadRajaDataForEdit();
            setTitle("Edit Data Raja");
        } else {
            isEditMode = false;
            setTitle("Tambah Data Raja");
        }

        btnSearchOnline.setOnClickListener(v -> {
            String query = etSearchOnline.getText().toString().trim();
            if (!query.isEmpty()) {
                Intent intent = new Intent(AddEditActivity.this, SearchResultActivity.class);
                intent.putExtra("SEARCH_QUERY", query);
                startActivityForResult(intent, SEARCH_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Masukkan nama raja untuk dicari", Toast.LENGTH_SHORT).show();
            }
        });

        btnPilihGambar.setOnClickListener(v -> openFilePicker());
        btnSimpan.setOnClickListener(v -> saveData());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == SEARCH_REQUEST_CODE) {
                String name = data.getStringExtra("EXTRA_NAME");
                String reign = data.getStringExtra("EXTRA_REIGN");
                String description = data.getStringExtra("EXTRA_DESC");

                etNama.setText(name);
                etPeriode.setText(reign);
                etDeskripsi.setText(description);
                Toast.makeText(this, "Data telah diisi, silakan tambah gambar", Toast.LENGTH_LONG).show();

            } else if (requestCode == PICK_IMAGE_REQUEST) {
                imageUri = data.getData();
                getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imagePath = imageUri.toString();
                imgRaja.setImageTintList(null);
                Glide.with(this).load(imageUri).into(imgRaja);
            }
        }
    }

    private void loadRajaDataForEdit() {
        Cursor cursor = db.getKingById(currentRajaId);
        if (cursor != null && cursor.moveToFirst()) {
            etNama.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_NAME)));
            etPeriode.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_REIGN)));
            etDeskripsi.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_DESC)));
            imagePath = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_IMAGE_PATH));
            if (imagePath != null && !imagePath.isEmpty()) {
                imageUri = Uri.parse(imagePath);
                imgRaja.setImageTintList(null);
                Glide.with(this).load(imageUri).into(imgRaja);
            }
            cursor.close();
        }
    }

    private void saveData() {
        String name = etNama.getText().toString().trim();
        String reign = etPeriode.getText().toString().trim();
        String description = etDeskripsi.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(reign) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imagePath == null || imagePath.isEmpty()) {
            Toast.makeText(this, "Silakan pilih gambar", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEditMode) {
            boolean updated = db.updateKing(currentRajaId, name, reign, description, imagePath);
            if (updated) {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
            }
        } else {
            boolean inserted = db.addKing(name, reign, description, imagePath);
            if (inserted) {
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}