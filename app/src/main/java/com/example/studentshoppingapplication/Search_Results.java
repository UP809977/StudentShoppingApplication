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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__results);
        addNewItem = findViewById(R.id.add_new_button);
        Main = new MainActivity();


        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        database = databaseHandler.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.itemResultsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemSearchAdapter(this,getSearchItems());
        recyclerView.setAdapter(mAdapter);

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

        String[] searchQuery = new String[]{incomingSearch};

        return database.rawQuery("select * from " + TABLE_NAME + " where " + C_Item_Name + " = ?",searchQuery);

    }

}
