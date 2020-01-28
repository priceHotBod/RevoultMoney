package com.example.revolutapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.revolutapplication.POJO.Currency;
import com.example.revolutapplication.adapters.CurrencyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main Activity";
    private final int UPDATER_DELAY = 1000;
    private final String BASE_URL ="https://revolut.duckdns.org/latest?base=";

    private String baseCurrency = "USD";  // AKA real money;

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
        // Bootstrap by adding the first base value to the list right away.
        currencyArrayList.add(0, new Currency(baseCurrency, 1));
        queue = Volley.newRequestQueue(this);

        buildRecyclerView();
        buildRunnable();
    }

    // Initialization code for the runnable which will trigger the API call every X ms
    private void buildRunnable(){
        timerHandler = new Handler();
        updater = new Runnable() {
            public void run() {
                volleyUpdateRequest(baseCurrency);
                timerHandler.postDelayed(this, UPDATER_DELAY);
            }
        };
        timerHandler.postDelayed(updater, UPDATER_DELAY);
    }

    // Initialization code for this activities' recycler view. Sets the onItemClickListener so that
    // every time a recycler view item is clicked, its viewholder is sent to the top and set as the
    // new base currency
    private void buildRecyclerView(){
        recyclerView = findViewById(R.id.currency_recycler_view);
        // Normally, updating a view holder makes that item flash to indicate it has changed. Disable this here
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        adapter = new CurrencyAdapter(currencyArrayList);
        recyclerView.setHasFixedSize(true);

        // Set a basic vertical layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // When an item is clicked, send that item to the top of the list, and make it the new base currency
        adapter.setOnItemClickListener(new CurrencyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                baseCurrency = currencyArrayList.get(position).getCurrencyAbbreviatedName();
                currencyArrayList.add(0, currencyArrayList.remove(position));
                // Set the quantity of the new base currency equal to whatever that items edittext currently reads
                // This makes it so selecting a new base currency won't immediately affect the values displayed on the other currencies
                for (int i = 1; i < currencyArrayList.size(); i++){
                    currencyArrayList.get(i).setBaseCurrencyQuantity(currencyArrayList.get(0).getBaseCurrencyQuantity() * currencyArrayList.get(0).getExchangeRate());
                }
                adapter.notifyDataSetChanged();
                volleyUpdateRequest(baseCurrency);
                recyclerView.scrollToPosition(0);
            }
        });

        // This listener will only respond to changes made in the editText of the first item in the recycler view
        adapter.setOnEditTextChangedListener(new CurrencyAdapter.OnEditTextChangedListener() {
            @Override
            public void onTextChanged(int position, String charSeq) {
                // Check first if the value is a double
                try {
                    double val = Double.parseDouble(charSeq);
                    // Updates the baseCurrencyQuantity value for each item, which will in turn update each items edittext
                    for (int i = 1; i < currencyArrayList.size(); i++){
                        currencyArrayList.get(i).setBaseCurrencyQuantity(val);
                    }

                    volleyUpdateRequest(baseCurrency);

                } catch (NumberFormatException ex) {
                    Log.e(TAG, "Not a double");
                }
            }
        });
    }

    private void volleyUpdateRequest(final String baseCurrency) {

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL + baseCurrency,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject rates = new JSONObject(response).getJSONObject("rates");

                            // For any items already in the arraylist, update the exchange rates accordingly
                            for (int i = 0; i < currencyArrayList.size(); i++) {
                                // Only get the currencies which are in the exchange rates JSON. This will exclude the base currency.
                                if (rates.has(currencyArrayList.get(i).getCurrencyAbbreviatedName())) {
                                    currencyArrayList.get(i).setExchangeRate(rates.getDouble(currencyArrayList.get(i).getCurrencyAbbreviatedName()));
                                    rates.remove(currencyArrayList.get(i).getCurrencyAbbreviatedName());
                                    adapter.notifyItemChanged(i);
                                }
                            }

                            // For any items which were not already in the arraylist, add them now
                            Iterator<String> keys = rates.keys();
                            while (keys.hasNext()) {
                                String keyStr = keys.next();
                                currencyArrayList.add(new Currency(keyStr,  rates.getDouble(keyStr)));
                                adapter.notifyItemInserted(adapter.getItemCount());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "error");
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
