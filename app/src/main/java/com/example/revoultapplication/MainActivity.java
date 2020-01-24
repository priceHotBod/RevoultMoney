package com.example.revoultapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.revoultapplication.POJO.Currency;
import com.example.revoultapplication.adapters.CurrencyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private final int UPDATER_DELAY = 1000;
    private final String BASE_URL ="https://revolut.duckdns.org/latest?base=";

    private String baseCurrency = "USD";  // AKA real money
    private boolean isUpdating = true;

    private Runnable updater;
    private ArrayList<Currency> currencyArrayList;
    private RequestQueue queue;
    private CurrencyAdapter adapter;
    private Handler timerHandler;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyArrayList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);

        buildRunnable();
        volleyUpdateRequest(baseCurrency);
        buildRecyclerView();

    }

    private void buildRunnable(){
        timerHandler = new Handler();
        updater = new Runnable() {
            public void run() {
                if (isUpdating) { volleyUpdateRequest(baseCurrency); }
                timerHandler.postDelayed(this, UPDATER_DELAY);
            }
        };
        timerHandler.postDelayed(updater, UPDATER_DELAY);
    }

    private void buildRecyclerView(){
        recyclerView = findViewById(R.id.currency_recycler_view);
        adapter = new CurrencyAdapter(currencyArrayList);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CurrencyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getApplicationContext(), "Clicked: " + currencyArrayList.get(position).getCurrencyAbbreviatedName(), Toast.LENGTH_SHORT).show();
//                isUpdating = !isUpdating;
                baseCurrency = currencyArrayList.get(position).getCurrencyAbbreviatedName();
                currencyArrayList.add(0, currencyArrayList.remove(position));
                volleyUpdateRequest(baseCurrency);
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
            }
        });
    }

    private void volleyUpdateRequest(final String baseCurrency){

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL + baseCurrency,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject rates = new JSONObject(response).getJSONObject("rates");

                            // Loop through existing arraylist, updating exchange rates of existing currencies
                            int baseIndex = -1;
                            for (int i = 0; i < currencyArrayList.size(); i++) {
                                if (currencyArrayList.get(i).getCurrencyAbbreviatedName().equals(baseCurrency)){
                                    baseIndex = i;
                                    continue;
                                }
                                try {
                                    currencyArrayList.get(i).setExchangeRate(rates.getDouble(currencyArrayList.get(i).getCurrencyAbbreviatedName()));
                                    rates.remove(currencyArrayList.get(i).getCurrencyAbbreviatedName());
                                } catch (JSONException e){
                                    Log.e("asdf", "Couldn't find exchange rate");
                                }
                            }

                            // To ensure the base value is always at the top
                            if (baseIndex != -1){
                                currencyArrayList.remove(baseIndex);
                            }
                            currencyArrayList.add(0, new Currency(baseCurrency, 1));

                            Iterator<String> keys = rates.keys();
                            while (keys.hasNext()) {
                                String keyStr = keys.next();
                                currencyArrayList.add(new Currency(keyStr,  rates.getDouble(keyStr)));
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("asdf", "error");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(updater);
    }


}
