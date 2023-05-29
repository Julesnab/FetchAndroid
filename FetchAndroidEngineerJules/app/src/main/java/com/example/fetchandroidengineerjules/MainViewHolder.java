package com.example.fetchandroidengineerjules;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView listId;

    public MainViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name_element);
        listId = itemView.findViewById(R.id.list_id_element);
    }
}
