package com.tradealizer.blabla;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText myInput;
    EditText myBeschreibung;
    TextView gesamtkosten;
    //TextView myText;
    AllesDBHandler dbHandler;
    private static final String TAG = "MainClass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myInput=(EditText)findViewById(R.id.myInput);
        myBeschreibung =(EditText) findViewById(R.id.myBeschreibung);
        gesamtkosten = (TextView) findViewById(R.id.tv_gesamtkosten);
        //myText =(TextView)findViewById(R.id.myText);
        dbHandler = new AllesDBHandler(this, null, null, 1);
        printDatabase();
        Log.d(TAG, "Vor populate ");
        populateListView();
    }

    public void addButtonClicked(View v){
       /* Products product = new Products(myInput.getText().toString());
        dbHandler.addProduct(product);
        printDatabase();*/
        //myText.setText("Hallo");
        Alles alles = new Alles(Integer.parseInt(myInput.getText().toString()),myBeschreibung.getText().toString());
        dbHandler.addProduct(alles);
        printDatabase();
        populateListView();
    }
    public void deleteButtonClicked(View v){
        String inputText = myInput.getText().toString();
        dbHandler.deleteProduct(inputText);
        printDatabase();
        populateListView();
    }

    public void printDatabase(){
        String[] dbString = dbHandler.databaseToString();
        //myText.setText(dbString);

        //myInput.setText("");
        gesamtkosten.setText("Gesamtkosten: " + dbString[1] + " €");
    }
    private void populateListView(){
        Log.d(TAG, "Vor cursor ");
        Cursor cursor = dbHandler.getAllRows();
        Log.d(TAG, "Vor arrays und adapter ");
        String[] from = new String[] {AllesDBHandler.COLUMN_Kosten, AllesDBHandler.COLUMN_Beschreibung};
        int[] to = new int[] {R.id.ID_Kosten, R.id.ID_Beschreibung };
        SimpleCursorAdapter cursorAdapter;
        Log.d(TAG, "Vor adapter instanz ");
        cursorAdapter = new SimpleCursorAdapter(this,R.layout.item_layout,cursor,from,to,0);
        Log.d(TAG, "Vor listview instanz ");
        ListView listv = (ListView) findViewById(R.id.listView);
        Log.d(TAG, "Vor set adapter ");
        listv.setAdapter(cursorAdapter);
        Log.d(TAG, "alles gut gelaufen ");
    }
}
