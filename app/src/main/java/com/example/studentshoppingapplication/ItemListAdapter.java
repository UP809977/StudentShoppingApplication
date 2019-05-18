package com.example.studentshoppingapplication;
//imports
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
        //used to create variable that are used for the two textviews in the used view
        public TextView mItem;
        public TextView mPrice;

        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            //binds the two textviews to the variables declared earlier
            mItem = itemView.findViewById(R.id.textView_ItemName);
            mPrice = itemView.findViewById(R.id.textView_ItemPrice);
        }
    }
    public ItemListAdapter(ArrayList<SelectedItems> selecteditems){
        //takes the data from the selecteditems arraylist and put them into the view
        mSelectItems = selecteditems;
    }

    @NonNull
    @Override
    //used to create the views used in the recyclerview
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_recycler,viewGroup, false);
        ItemListViewHolder itemlistview = new ItemListViewHolder(v);
        return itemlistview;
    }

    //used to get the data used in the recyclerview from the selecteditems arraylist
    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder itemListViewHolder, int i) {
        SelectedItems currentItem =mSelectItems.get(i);

        itemListViewHolder.mItem.setText(currentItem.getmItemName());
        itemListViewHolder.mPrice.setText(String.valueOf(currentItem.getmItemPrice()));


    }

    //used to get the number of items in the arraylist and count until the end of the list
    @Override
    public int getItemCount() {
        return mSelectItems.size();
    }
}
