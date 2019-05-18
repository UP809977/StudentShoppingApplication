package com.example.studentshoppingapplication;

//imports
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.valueOf;

public class EditItem extends AppCompatActivity {
    //created varables used in this class
    private DatabaseHandler database;
    private Button submitEdit;
    private TextView editID;
    private EditText editBarcode, editItemName, editPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        //allows the database to be access from this class
        database = new DatabaseHandler(this);

        //binds the varaables to the buttonm and edittetxts in this form
        editID = findViewById(R.id.editID);
        editBarcode = findViewById(R.id.editBarcode);
        editItemName = findViewById(R.id.editItem);
        editPrice = findViewById(R.id.editPrice);
        submitEdit = findViewById(R.id.submitEdit);

        //calls the two methods used in this class
        getEditItems();
        EditItemRecord();
    }

    //gets the data sent from the Search_Results form
    public void getEditItems(){
        Intent itemEdit = getIntent();
        int incomingID = itemEdit.getIntExtra("eID",0);
        String incomingEBarcode = itemEdit.getStringExtra("eBarcode");
        String incomingEName = itemEdit.getStringExtra("eName");
        float incomingEPrice = itemEdit.getFloatExtra("ePrice",0);
        editID.setText(valueOf(incomingID));
        editBarcode.setText(incomingEBarcode);
        editItemName.setText(incomingEName);
        editPrice.setText(valueOf(incomingEPrice));

    }

    //used to update the record, takes the new data and calls the ipDateItem metod in the DatabaseHandler class and then sends the user back to the MainActivity
    public void EditItemRecord(){
        submitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedaAme = editItemName.getText().toString();
                String editedPrice = editPrice.getText().toString();
                if (editedaAme.matches("") || editedPrice.matches("")) {// used to make sure the user is unable to submit an edited item that does not have a name ot a price
                    Toast.makeText(EditItem.this, "An Error occurred please check data and try again", Toast.LENGTH_SHORT).show();
                }else{
                    database.upDateItem(editID.getText().toString(),editBarcode.getText().toString(),editItemName.getText().toString(),editPrice.getText().toString());
                    Toast.makeText(EditItem.this, "Item Updated", Toast.LENGTH_SHORT).show();
                    editBarcode.getText().clear();
                    editItemName.getText().clear();
                    editPrice.getText().clear();
                    returnToMain();
                }

            }
        });
    }

    //Method used to send users back to the main activity
    public void returnToMain(){
        Intent returnToMainAct = new Intent(this,MainActivity.class);
        startActivity(returnToMainAct);
    }
}
