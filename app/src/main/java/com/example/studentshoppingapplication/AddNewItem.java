package com.example.studentshoppingapplication;

//imports
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddNewItem extends AppCompatActivity {
    //creation of variables used in the calss
    private DatabaseHandler database;
    private Button submitNewEntry;
    private EditText barcode, itemName, itemPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);
        //used to bind the items in the form to the variavles
        database = new DatabaseHandler(this);
        barcode = findViewById(R.id.barcodeEntry);
        itemName = findViewById(R.id.itemNameEntry);
        itemPrice = findViewById(R.id.priceEntry);
        submitNewEntry = findViewById(R.id.submitEdit);


        insertNewItemRecord();

    }

    //this method is used when the Add New button is pressed in the form. it takes the data in the EditTexts and adds it to the database
    public void insertNewItemRecord() {
        submitNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insertIName = itemName.getText().toString();
                String insertPrice = itemPrice.getText().toString();
                if (insertIName.matches("") || insertPrice.matches("")) {//used to check the user is not adding data with no name or price
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

    //used to send the user back to the mainactivity after the item is added
    public void backToMain(){
        Intent openMain = new Intent(this,MainActivity.class);
        startActivity(openMain);
    }

}

