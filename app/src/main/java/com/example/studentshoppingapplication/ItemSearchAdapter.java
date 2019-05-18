package com.example.studentshoppingapplication;

//imports
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ItemSearchHolder> {
    //vars so that the database can be searched
    private Context mContext;
    private Cursor mCursor;
    //var that holds the user click
    private OnItemClickListener mListener;


    //interfaces that create the onclicklistners for the item so it can be sent to the mainactibity or the edititem
    public interface OnItemClickListener{
        void onItemClick(String name, float price);
        void onEditClick (int id ,String barcode , String name, float price);
    }

    //creates the handler for the onclick
    public void setOnItemClickListener(OnItemClickListener listner){
        mListener = listner;
    }



    //used to populate the recyclerview from data searched from the database
    public ItemSearchAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;

    }

    public static class ItemSearchHolder extends RecyclerView.ViewHolder{
        public TextView itemName;
        public TextView itemPrice;
        public TextView itemBarcode;
        public TextView itemID;
        public ImageView mEdit;

        public ItemSearchHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            //binds the textviews and icon to variables
            itemName = itemView.findViewById(R.id.textView_ItemName);
            itemPrice = itemView.findViewById(R.id.textView_ItemPrice);
            itemBarcode = itemView.findViewById(R.id.barcode);
            itemID = itemView.findViewById(R.id.id);
            mEdit = itemView.findViewById(R.id.edit);


            //logic to handle the user click on the itemview so it can be sent to the MainActivity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("onClick test: ","hello");

                    if (listener != null){
                        int i = getAdapterPosition();
                        if(i != RecyclerView.NO_POSITION){

                            listener.onItemClick(itemName.getText().toString(),Float.valueOf(itemPrice.getText().toString()));

                        }
                    }

                }
            });
            //logic to handle user click on the icon so that it can sent to the EditItem fom.
            mEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int i = getAdapterPosition();
                        if(i != RecyclerView.NO_POSITION){

                            listener.onEditClick(Integer.valueOf(itemID.getText().toString()),itemBarcode.getText().toString(),itemName.getText().toString(),Float.valueOf(itemPrice.getText().toString()));

                        }
                    }

                }
            });
        }
    }

    //creates the views used for the recyclerview
    @Override
    public ItemSearchHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_search_recycler, viewGroup, false);
        return new ItemSearchHolder(view, mListener);
    }

    //used to get the data that the user has searched for and add them to the views inside the recyclerview
    @Override
    public void onBindViewHolder(ItemSearchHolder itemSearchHolder, int i) {
        if (mCursor.moveToPosition(i)){
            String name = mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.C_Item_Name));
            float price = mCursor.getFloat(mCursor.getColumnIndex(DatabaseHandler.C_Price));
            int id = mCursor.getInt(mCursor.getColumnIndex(DatabaseHandler.C_ID));
            String barcode = mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.C_Barcode));

            itemSearchHolder.itemName.setText(name);
            itemSearchHolder.itemPrice.setText(String.valueOf(price));
            itemSearchHolder.itemID.setText(String.valueOf(id));
            itemSearchHolder.itemBarcode.setText(barcode);


        }

    }

    //used to iterate though the database so that all the relevant data is taken
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    //used when the user searches for a different item in the main activity and new data is neeeded in the recyclerview
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

