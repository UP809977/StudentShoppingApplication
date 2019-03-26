package com.example.studentshoppingapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.studentshoppingapplication.DatabaseHandler.C_Item_Name;
import static com.example.studentshoppingapplication.DatabaseHandler.TABLE_NAME;



public class Search_Results extends AppCompatActivity {
    private Button addNewItem;
    private SQLiteDatabase database;
    private ItemSearchAdapter mAdapter;
    public MainActivity Main;
    public String search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__results);
        addNewItem = findViewById(R.id.add_new_button);






        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        database = databaseHandler.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.itemResultsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemSearchAdapter(this,getAllItems());
        recyclerView.setAdapter(mAdapter);

        mAdapter.swapCursor(getAllItems());

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

    private Cursor getAllItems(){
        //selects all items from the database and displays them in the itemsearch recycler view.
        //this cod is not meant to be un the final version but is here so that the code from the recycler can be tested as working

        //return database.rawQuery("select * from " +TABLE_NAME +" where "+ C_Item_Name +" = " +C_Item_Name ,null);


        //This is the code that is used to search the database
        //currently this code does not function as intended as it gives a null object reference error

        search ="";
        if (MainActivity.itemSearch != null){
            search = MainActivity.itemSearch.getText().toString(); // this line appears to be the problem
        }

        String[] searchQuery = new String[]{search};
//        if (searchQuery == null){
//            Toast.makeText(Search_Results.this, "Search id null", Toast.LENGTH_SHORT).show();
//        }

        return database.rawQuery("select * from " + TABLE_NAME + " where " + C_Item_Name + " = ? ",searchQuery);

    }

}
