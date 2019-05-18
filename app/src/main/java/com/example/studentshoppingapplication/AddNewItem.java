package com.example.studentshoppingapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewItem extends AppCompatActivity {
    private DatabaseHandler database;
    private Button submitNewEntry;
    private EditText barcode, itemName, itemPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        database = new DatabaseHandler(this);
        barcode = findViewById(R.id.barcodeEntry);
        itemName = findViewById(R.id.itemNameEntry);
        itemPrice = findViewById(R.id.priceEntry);
        submitNewEntry = findViewById(R.id.submitEdit);


        insertNewItemRecord();

    }

    public void insertNewItemRecord() {
        submitNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insertIName = itemName.getText().toString();
                String insertPrice = itemPrice.getText().toString();
                if (insertIName.matches("") || insertPrice.matches("")) {
                    Toast.makeText(AddNewItem.this, "An Error occurred please check data and try again", Toast.LENGTH_SHORT).show();
                    barcode.getText().clear();
                    itemName.getText().clear();
                    itemPrice.getText().clear();
                } else {
                    database.insertNewItem(barcode.getText().toString(), itemName.getText().toString(), itemPrice.getText().toString());
                    Toast.makeText(AddNewItem.this, "Item Added", Toast.LENGTH_SHORT).show();
                    barcode.getText().clear();
                    itemName.getText().clear();
                    itemPrice.getText().clear();
                    backToMain();
                }


            }
        });

    }

    public void backToMain(){
        Intent openMain = new Intent(this,MainActivity.class);
        startActivity(openMain);
    }

}

