package com.example.studentshoppingapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button searchResults;
    private Button barcodeScanner;
    public EditText itemSearch;
    //public String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        startActivity(searchResults);
    }

    public void openBarcodeScanner(){
        Intent barcodeScanner = new Intent(this,BarcodeScanner.class);
        startActivity(barcodeScanner);
    }
}





