package com.example.studentshoppingapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    private ArrayList<SelectedItems> mSelectItems;

    public static class ItemListViewHolder extends RecyclerView.ViewHolder{
        public TextView mItem;
        public TextView mPrice;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            mItem = itemView.findViewById(R.id.textView_ItemName);
            mPrice = itemView.findViewById(R.id.textView_ItemPrice);
        }
    }
    public ItemListAdapter(ArrayList<SelectedItems> selecteditems){
        mSelectItems = selecteditems;
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_recycler,viewGroup, false);
        ItemListViewHolder itemlistview = new ItemListViewHolder(v);
        return itemlistview;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder itemListViewHolder, int i) {
        SelectedItems currentItem =mSelectItems.get(i);

        itemListViewHolder.mItem.setText(currentItem.getmItemName());
        itemListViewHolder.mPrice.setText(String.valueOf(currentItem.getmItemPrice()));


    }

    @Override
    public int getItemCount() {
        return mSelectItems.size();
    }
}
