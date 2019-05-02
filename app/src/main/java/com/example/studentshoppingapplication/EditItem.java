package com.example.studentshoppingapplication;

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
    private DatabaseHandler database;
    private Button submitEdit;
    private TextView editID;
    private EditText editBarcode, editItemName, editPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        database = new DatabaseHandler(this);
        editID = findViewById(R.id.editID);
        editBarcode = findViewById(R.id.editBarcode);
        editItemName = findViewById(R.id.editItem);
        editPrice = findViewById(R.id.editPrice);
        submitEdit = findViewById(R.id.submitEdit);
        getEditItems();
        EditItemRecord();
    }

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

    public void EditItemRecord(){
        submitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editedaAme = editItemName.getText().toString();
                String editedPrice = editPrice.getText().toString();
                if (editedaAme.matches("") && editedPrice.matches("")) {
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

    public void returnToMain(){
        Intent returnToMainAct = new Intent(this,MainActivity.class);
        startActivity(returnToMainAct);
    }
}
