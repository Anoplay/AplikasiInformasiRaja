package com.example.aplikasiinformasiraja;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    DatabaseHelper db;
    List<Raja> rajaList;
    RajaAdapter adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Raja Lokal");

        db = new DatabaseHelper(this);
        rajaList = new ArrayList<>();
        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        recyclerView = findViewById(R.id.recycler_view_kings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.fab_add);

        boolean isGuest = sharedPreferences.getBoolean("isGuest", false);
        if (isGuest) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivity(intent);
        });

        loadDataRaja();
    }

    private void loadDataRaja() {
        rajaList.clear();
        Cursor cursor = db.getAllKings();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                rajaList.add(new Raja(
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_REIGN)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_DESC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_KING_IMAGE_PATH))
                ));
            }
            cursor.close();
        }

        adapter = new RajaAdapter(this, rajaList, raja -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("RAJA_ID", raja.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataRaja();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logoutItem = menu.findItem(R.id.action_logout);
        boolean isGuest = sharedPreferences.getBoolean("isGuest", false);
        logoutItem.setVisible(!isGuest);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}