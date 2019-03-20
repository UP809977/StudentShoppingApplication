package com.example.studentshoppingapplication;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewItem extends AppCompatActivity {
    private DatabaseHandler database;
    private Button submitNewEntry;
    private Button databaseTest;
    private EditText barcode, itemName, itemPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        database = new DatabaseHandler(this);
        barcode = findViewById(R.id.barcodeEntry);
        databaseTest = findViewById(R.id.database_test_button);
        itemName = findViewById(R.id.itemNameEntry);
        itemPrice = findViewById(R.id.priceEntry);
        submitNewEntry = findViewById(R.id.submitNewEntry);


        insertNewItemRecord();
        seeData();

    }

    public void insertNewItemRecord() {
        submitNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean insert = database.insertNewItem(barcode.getText().toString(), itemName.getText().toString(), itemPrice.getText().toString());
                if (insert && itemName.getText().toString().trim().length() != 0 && itemPrice.getText().toString().trim().length() != 0) {
                    Toast.makeText(AddNewItem.this, "Item Added", Toast.LENGTH_SHORT).show();
                    barcode.getText().clear();
                    itemName.getText().clear();
                    itemPrice.getText().clear();

                } else {
                    Toast.makeText(AddNewItem.this, "An Error occurred please check data and try again", Toast.LENGTH_SHORT).show();
                    barcode.getText().clear();
                    itemName.getText().clear();
                    itemPrice.getText().clear();
                }


            }
        });

    }

    public void seeData() {
        databaseTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor results = database.getAllData();
                if (results.getCount() == 0) {
                    showData("Error", "No Data");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (results.moveToNext()) {
                    buffer.append("ID " + results.getString(0) + "\n");
                    buffer.append("Barcode " + results.getString(1) + "\n");
                    buffer.append("Item_Name " + results.getString(2) + "\n");
                    buffer.append("Price " + results.getString(3) + "\n\n");
                }
                // show data
                showData("Data", buffer.toString());
            }
        });
    }

    //test code remove or repurpose after data insertion solved
    public void showData(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}

