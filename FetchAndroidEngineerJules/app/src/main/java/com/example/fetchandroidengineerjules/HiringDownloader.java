package com.example.fetchandroidengineerjules;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class HiringDownloader {

    private static MainActivity mainAct;
    private static final String hiringURL = "https://fetch-hiring.s3.amazonaws.com/hiring.json";

    public static void downloadHiring(MainActivity mainActivityIn) {

        mainAct = mainActivityIn;
        RequestQueue queue = Volley.newRequestQueue(mainAct);

        Uri.Builder buildURL = Uri.parse(hiringURL).buildUpon();
        String urlToUse = buildURL.build().toString();

        Response.Listener<JSONArray> listener =
                response -> parseJSON(response.toString());

        Response.ErrorListener error =
                error1 -> mainAct.noDataFound();


        // Request a string response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                urlToUse, null, listener, error);

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    private static void parseJSON(String s) {
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonItem = jsonArray.getJSONObject(i);
                int id = jsonItem.getInt("id");
                int listId = jsonItem.getInt("listId");
                String name = jsonItem.optString("name", "");
                if (!name.isEmpty() && !name.equals("null")) {
                    Item item = new Item(id, listId, name);
                    mainAct.addItem(item);
                }
            }
            mainAct.sortItemList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
