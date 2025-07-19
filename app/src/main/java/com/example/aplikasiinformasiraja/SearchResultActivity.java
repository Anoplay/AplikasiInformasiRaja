package com.example.aplikasiinformasiraja;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvNoResults;
    private List<Raja> rajaList;
    private RajaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Toolbar toolbar = findViewById(R.id.toolbar_search_result);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recycler_view_search_result);
        progressBar = findViewById(R.id.progress_bar);
        tvNoResults = findViewById(R.id.tv_no_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rajaList = new ArrayList<>();

        String query = getIntent().getStringExtra("SEARCH_QUERY");
        if (query != null && !query.isEmpty()) {
            setTitle("Hasil untuk: " + query);
            searchHistoricalFigures(query);
        }
    }

    private void searchHistoricalFigures(String name) {
        progressBar.setVisibility(View.VISIBLE);
        tvNoResults.setVisibility(View.GONE);
        RequestQueue queue = Volley.newRequestQueue(this);

        String apiKey = "G1ErCfG12Ac2aoM2o/BcWQ==3N46g2rP75KLoPCD";
        String url = "https://api.api-ninjas.com/v1/historicalfigures?name=" + name;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        if (response.length() == 0) {
                            tvNoResults.setVisibility(View.VISIBLE);
                            return;
                        }

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject figureObject = response.getJSONObject(i);
                            String rajaName = figureObject.optString("name", "Nama tidak diketahui");
                            String reign = figureObject.optString("title", "Gelar tidak diketahui");

                            StringBuilder descBuilder = new StringBuilder();
                            if (figureObject.has("info")) {
                                JSONObject info = figureObject.getJSONObject("info");
                                String born = info.optString("born", "").trim();
                                String died = info.optString("died", "").trim();

                                if (!born.isEmpty()) {
                                    descBuilder.append("Lahir: ").append(born);
                                }
                                if (!died.isEmpty()) {
                                    if (descBuilder.length() > 0) {
                                        descBuilder.append("\n");
                                    }
                                    descBuilder.append("Wafat: ").append(died);
                                }
                            }

                            String description = descBuilder.toString();
                            if (description.isEmpty()) {
                                description = "Tidak ada detail tambahan dari API.";
                            }

                            rajaList.add(new Raja("api_id", rajaName, reign, description, ""));
                        }

                        adapter = new RajaAdapter(this, rajaList, raja -> {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("EXTRA_NAME", raja.getName());
                            resultIntent.putExtra("EXTRA_REIGN", raja.getReign());
                            resultIntent.putExtra("EXTRA_DESC", raja.getDescription());
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        });
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Gagal mengolah data.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    String errorMessage = "Gagal mengambil data.";
                    if (error.networkResponse != null) {
                        errorMessage += " Kode Error: " + error.networkResponse.statusCode;
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Api-Key", apiKey);
                return headers;
            }
        };

        queue.add(jsonArrayRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}