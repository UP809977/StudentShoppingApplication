package com.example.studentshoppingapplication;

//imports
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //vars for button and edit text fields
    private Button searchResults;
    private Button barcodeScanner;
    public EditText itemSearch;

    // vars for the recyclerview
    private RecyclerView itemRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //var for OnActivityResult method
    private final int SEARCH_RES = 1;

    //Arraylist for itemRecycler
    private ArrayList<SelectedItems> selecteditems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // sets up default test value for selecteditems ArrayList
        selecteditems.add(new SelectedItems("Select an Item",0));

        //layout for the itemRecycler so it can be drawn on the UI
        itemRecycler = findViewById(R.id.itemRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ItemListAdapter(selecteditems);
        itemRecycler.setLayoutManager(mLayoutManager);
        itemRecycler.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) { //these variables allow a swipe right or swipe left function to be handled on the recyclerview
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
                //Not used as this variable is used so items can be dragged up and down the recyclerview but this is not necessary for this application so is not implemented
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                //This code is used to allow swipe to delete functionality to work, it gets position of the item and correlates it to the item in the arraylist
                //when item is swiped to left or the right the item is removed from the arraylist and the user is notifed and the dataset the arraylist reads from is updated.
                int pos = viewHolder.getAdapterPosition();
                selecteditems.remove(pos);
                mAdapter.notifyDataSetChanged();
                //toast message is displayed on screen letting users know they have removed an item from the shopping list
                Toast.makeText(MainActivity.this,"item removed from shopping list",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(itemRecycler); //this allows the swipe to delete functionality to be activated to the recyctlerview


        //binds the buttons and edittext to these variables
        searchResults = findViewById(R.id.search_button);
        barcodeScanner = findViewById(R.id.barcode_button);
        itemSearch = findViewById(R.id.itemSearch);




        searchResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creates a toast message for what the user has searched for
                Toast.makeText(MainActivity.this, itemSearch.getText(), Toast.LENGTH_SHORT).show();
                //takes the data from the edittext so i can be compared against when searching
                String search = itemSearch.getText().toString();
                if(search.matches("")){ //uses so that that user is unable to search with a blank EditText
                    //Messagge to inform user they cannot search with a blank EditText
                    Toast.makeText(MainActivity.this, "Cannot search for nothing", Toast.LENGTH_SHORT).show();
                }else{
                    //opens the Search_Results form via calling the openSearch_Results method and clears the EditText
                    openSearch_Results();
                    itemSearch.getText().clear();
                }

            }
        });

        //creates an click function for the barcode scanner button so that it opens up the barcode scanner form
        barcodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBarcodeScanner();

            }
        });

    }

    //creates the menu so it can be used in the form
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainactivity_menu,menu);
        return true;
    }

    //adds functionality to the menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAll:
                selecteditems.clear();
                mAdapter.notifyDataSetChanged();
                Toast.makeText(this,"Shopping List Cleared!",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //code to open the Search_Results form
    public void openSearch_Results() {
        Intent searchResults = new Intent(this, Search_Results.class);
        searchResults.putExtra("search",itemSearch.getText().toString());
        startActivityForResult(searchResults,SEARCH_RES);
    }

    //Code to open the barcode scanner form
    public void openBarcodeScanner(){
        Intent barcodeScanner = new Intent(this,BarcodeScanner.class);
        startActivity(barcodeScanner);
    }
    //code to recieve the selcted item from the search results recyclerview and then add it to the selecteditems arraylist
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





