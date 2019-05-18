package com.example.studentshoppingapplication;

//impoted items
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//These are impotred from the DatabaseHandler class so that the data passed from the EditText in the main menu can be placed into a SQL query so that the database can be searched
import static com.example.studentshoppingapplication.DatabaseHandler.C_Item_Name;
import static com.example.studentshoppingapplication.DatabaseHandler.TABLE_NAME;



public class Search_Results extends AppCompatActivity {
    //vars for button, database and ItemSearchAdapter
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
        //binding buttons to variables
        addNewItem = findViewById(R.id.add_new_button);
        Main = new MainActivity();
        itemSearched = findViewById(R.id.itemSearched);


        //allows the database to be accessed in this form
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        database = databaseHandler.getWritableDatabase();

        //sets out layout for the ItemSearcgAdapyer
        RecyclerView recyclerView = findViewById(R.id.itemResultsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCursor = getSearchItems();
        mAdapter = new ItemSearchAdapter(this,mCursor);
        recyclerView.setAdapter(mAdapter);





        //this allows for the onlicklistner for the recyclerview to work allowing the data the user has clicked on to be extracted and sent to the sendSelectedItemToMainActivity method
        mAdapter.setOnItemClickListener(new ItemSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String name,float price) {
                //testing logs to make sure data is pulled out correclty
                Log.d("i: ","i test");
                Log.d("item name sr: ",name);
                Log.d("item price sr: ",String.valueOf(price));

                //method to send the data main activity via metos
                sendSelectedItemToMainActivity(name,price);

            }

            //allows for data to be selected by the user and sent to the EditItem form
            @Override
            public void onEditClick(int id,String barcode, String name, float price) {
                //takes data chosen by user and sens it to the sendToEdit method
                sendToEdit(id,barcode,name,price);

            }
        });

        //changes the dataset so when the user seach is complete the data populates the recyclerview
        mAdapter.swapCursor(getSearchItems());

        //sets on click listner for the addnewitem form.
        addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddNew();
            }
        });
    }

    //method to openn AddNewItem form
    public void openAddNew() {
        Intent addNewItem = new Intent(this, AddNewItem.class);
        startActivity(addNewItem);
    }

    private Cursor getSearchItems(){
        //This is the code that is used to search the database
        Intent search = getIntent();
        String incomingSearch = search.getStringExtra("search");
        itemSearched.setText(incomingSearch);

        //allows data seearhced by the user be converted so it can be used in a SQL query as a selection argument
        String[] searchQuery = new String[]{incomingSearch};

        return database.rawQuery("select * from " + TABLE_NAME + " where " + C_Item_Name + " = ?",searchQuery);

    }

    //code to take the data selected from the recyclerview and send it to the main activity so it can be added to the selecteditems array list so it can populate the recydlerview hosted there
    private void sendSelectedItemToMainActivity(String name, Float price){
        Intent itemChosen = new Intent(this,MainActivity.class);
        itemChosen.putExtra("iName",name);
        itemChosen.putExtra("iPrice",price);
        setResult(RESULT_OK,itemChosen);
        finish();

    }

    //code so that the item selected from the recyclerview can be sent too the EditItem class
    private void sendToEdit(int id,String barcode ,String name, Float price){
        Intent itemEdit = new Intent(this, EditItem.class);
        itemEdit.putExtra("eID", id);
        itemEdit.putExtra("eBarcode",barcode);
        itemEdit.putExtra("eName",name);
        itemEdit.putExtra("ePrice",price);
        startActivity(itemEdit);
    }


}
