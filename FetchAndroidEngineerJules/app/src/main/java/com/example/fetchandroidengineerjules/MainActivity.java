package com.example.fetchandroidengineerjules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private MainAdapter itemAdapter;
    private final ArrayList<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.item_recycler);
        itemAdapter = new MainAdapter(itemList, this);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (hasNetworkConnection()){
            HiringDownloader.downloadHiring(this);
        } else {
            findViewById(R.id.no_data).setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sortItemList() {
        // Sort the itemList by listId and then by name
        itemList.sort(new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                // Compare listId first
                int listIdComparison = Integer.compare(item1.getListId(), item2.getListId());
                if (listIdComparison != 0) {
                    return listIdComparison;
                }

                // If listId is the same, compare name
                int number1 = extractNumberFromName(item1.getName());
                int number2 = extractNumberFromName(item2.getName());

                // Compare the numeric parts
                return Integer.compare(number1, number2);
                //return item1.getName().compareTo(item2.getName());
            }

            private int extractNumberFromName(String name) {
                // Extract the numeric part from the name (assuming it starts with "Item" followed by a number)
                String numberString = name.substring("Item ".length());
                return Integer.parseInt(numberString);
            }
        });

        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about_item) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        Network network = connectivityManager.getActiveNetwork();
        if (network != null) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        }
        return false;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(Item item){
        itemList.add(item);
        itemAdapter.notifyDataSetChanged();
    }
    public void noDataFound(){
        Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildAdapterPosition(v);
        Toast.makeText(this, "Item clicked: " + itemList.get(pos).getName(), Toast.LENGTH_SHORT).show();
    }
}