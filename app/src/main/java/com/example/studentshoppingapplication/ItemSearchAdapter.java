package com.example.studentshoppingapplication;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ItemSearchHolder> {
    private Context mContext;
    private Cursor mCursor;
    private OnItemClickListener mListener;
    //private ItemSearchAdapter that;

    public interface OnItemClickListener{
        void onItemClick(int i,String name, float price);
    }

    public void setOnItemClickListener(OnItemClickListener listner){
        mListener = listner;
    }



    public ItemSearchAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;

    }

    public static class ItemSearchHolder extends RecyclerView.ViewHolder{
        public TextView itemName;
        public TextView itemPrice;

        public ItemSearchHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            itemName = itemView.findViewById(R.id.textView_ItemName);

            itemPrice = itemView.findViewById(R.id.textView_ItemPrice);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("onClick test: ","hello");

                    if (listener != null){
                        int i = getAdapterPosition();
                        if(i != RecyclerView.NO_POSITION){

                            listener.onItemClick(i,itemName.getText().toString(),Float.valueOf(itemPrice.getText().toString()));
                            

                        }
                    }

                }
            });
        }
    }

    @Override
    public ItemSearchHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_search_recycler, viewGroup, false);
        return new ItemSearchHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ItemSearchHolder itemSearchHolder, int i) {
        if (mCursor.moveToPosition(i)){
            String name = mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.C_Item_Name));
            float price = mCursor.getFloat(mCursor.getColumnIndex(DatabaseHandler.C_Price));

            itemSearchHolder.itemName.setText(name);
            itemSearchHolder.itemPrice.setText(String.valueOf(price));


        }

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (mCursor != null){
            mCursor.close();

        }
        mCursor = newCursor;
        if (newCursor != null){
            notifyDataSetChanged();
        }
    }


}

