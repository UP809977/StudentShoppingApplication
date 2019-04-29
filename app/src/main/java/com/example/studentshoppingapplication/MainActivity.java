package com.example.studentshoppingapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button searchResults;
    private Button barcodeScanner;
    public EditText itemSearch;
    private RecyclerView itemRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private final int SEARCH_RES = 1;
    private ArrayList<SelectedItems> selecteditems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selecteditems.add(new SelectedItems("Select an Item",0));
        //Intent item = getIntent();


        itemRecycler = findViewById(R.id.itemRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ItemListAdapter(selecteditems);

        itemRecycler.setLayoutManager(mLayoutManager);
        itemRecycler.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int pos = viewHolder.getAdapterPosition();
                selecteditems.remove(pos);
                mAdapter.notifyDataSetChanged();

            }
        }).attachToRecyclerView(itemRecycler);


        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        searchResults = findViewById(R.id.search_button);
        barcodeScanner = findViewById(R.id.barcode_button);
        itemSearch = findViewById(R.id.itemSearch);




        searchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String search = searchResults.getText().toString().trim();
                Toast.makeText(MainActivity.this, itemSearch.getText(), Toast.LENGTH_SHORT).show();
                if(itemSearch.getText() != null){
                    openSearch_Results();
                    itemSearch.getText().clear();
                }else{
                    Toast.makeText(MainActivity.this, "Cannot leave search blank", Toast.LENGTH_SHORT).show();
                }

            }
        });

        barcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarcodeScanner();

            }
        });

    }

    public void openSearch_Results() {
        Intent searchResults = new Intent(this, Search_Results.class);
        searchResults.putExtra("search",itemSearch.getText().toString());
        startActivityForResult(searchResults,SEARCH_RES);
    }

    public void openBarcodeScanner(){
        Intent barcodeScanner = new Intent(this,BarcodeScanner.class);
        startActivity(barcodeScanner);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data ){
        if (resultCode == RESULT_OK && requestCode == SEARCH_RES){
            Bundle extras = data.getExtras();
            if (extras != null){
                String name = extras.getString("iName");
                float price = extras.getFloat("iPrice");
                selecteditems.add(new SelectedItems(name,price));
            }
        }

    }
}





