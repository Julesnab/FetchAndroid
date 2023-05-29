package com.example.fetchandroidengineerjules;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder>  {

    private final ArrayList<Item> itemList;
    private final MainActivity mainAct;

    public MainAdapter(ArrayList<Item> itemList, MainActivity mainAct) {
        this.itemList = itemList;
        this.mainAct = mainAct;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent, false);
        view.setOnClickListener(mainAct);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.listId.setText(String.valueOf(item.getListId()));
        holder.name.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
