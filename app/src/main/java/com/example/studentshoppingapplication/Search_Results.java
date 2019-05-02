package com.example.studentshoppingapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.studentshoppingapplication.DatabaseHandler.C_Item_Name;
import static com.example.studentshoppingapplication.DatabaseHandler.TABLE_NAME;



public class Search_Results extends AppCompatActivity {
    private Button addNewItem;
    private SQLiteDatabase database;
    private ItemSearchAdapter mAdapter;
    public MainActivity Main;
    public Cursor mCursor;
    private TextView itemSearched;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__results);
        addNewItem = findViewById(R.id.add_new_button);
        Main = new MainActivity();
        itemSearched = findViewById(R.id.itemSearched);


        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        database = databaseHandler.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.itemResultsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCursor = getSearchItems();
        mAdapter = new ItemSearchAdapter(this,mCursor);

        recyclerView.setAdapter(mAdapter);





        mAdapter.setOnItemClickListener(new ItemSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name,float price) {
                Log.d("i: ","i test");
                Log.d("item name sr: ",name);
                Log.d("item price sr: ",String.valueOf(price));

                //SelectedItems chosenItem = selecteditems.add(new SelectedItems(name,price));
                sendSelectedItemToMainActivity(name,price);

            }

            @Override
            public void onEditClick(String name, float price) {
                sendToEdit(name,price);

            }
        });

        mAdapter.swapCursor(getSearchItems());

        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNew();
            }
        });
    }

    public void openAddNew() {
        Intent addNewItem = new Intent(this, AddNewItem.class);
        startActivity(addNewItem);
    }

    private Cursor getSearchItems(){
        //This is the code that is used to search the database

        Intent search = getIntent();
        String incomingSearch = search.getStringExtra("search");
        itemSearched.setText(incomingSearch);

        String[] searchQuery = new String[]{incomingSearch};

        return database.rawQuery("select * from " + TABLE_NAME + " where " + C_Item_Name + " = ?",searchQuery);

    }

    private void sendSelectedItemToMainActivity(String name, Float price){
        Intent itemChosen = new Intent(this,MainActivity.class);
        itemChosen.putExtra("iName",name);
        itemChosen.putExtra("iPrice",price);
        setResult(RESULT_OK,itemChosen);
        finish();

    }

    private void sendToEdit(String name, Float price){
        Intent itemEdit = new Intent(this, EditItem.class);
        itemEdit.putExtra("eName",name);
        itemEdit.putExtra("ePrice",price);
        startActivity(itemEdit);
    }


}
