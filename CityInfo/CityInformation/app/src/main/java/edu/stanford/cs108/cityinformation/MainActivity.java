package edu.stanford.cs108.cityinformation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("MyDB", MODE_PRIVATE, null);

        Cursor tablesCursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table' AND name='cities';", null);

        if (tablesCursor.getCount() == 0) {
            setupDatabase();
            populateDatabase();
        }
    }

    private void setupDatabase() {
        String clearStr = "DROP TABLE IF EXISTS cities;";
        System.err.println(clearStr);
        db.execSQL(clearStr);

        String setupStr = "CREATE TABLE cities ("
                + "name TEXT, continent TEXT, population INTEGER, "
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ");";

        System.err.println(setupStr);
        db.execSQL(setupStr);
    }

    private void populateDatabase() {
        String dataStr = "INSERT INTO cities VALUES "
                + "('Cairo','Africa',15200000,NULL),"
                + "('Lagos','Africa',21000000,NULL),"
                + "('Kyoto','Asia',1474570,NULL),"
                + "('Mumbai','Asia',20400000,NULL),"
                + "('Shanghai','Asia',24152700,NULL),"
                + "('Melbourne','Australia',3900000,NULL),"
                + "('London','Europe',8580000,NULL),"
                + "('Rome','Europe',2715000,NULL),"
                + "('Rostov-on-Don','Europe',1052000,NULL),"
                + "('San Francisco','North America',5780000,NULL),"
                + "('San Jose','North America',7354555,NULL),"
                + "('New York','North America',21295000,NULL),"
                + "('Rio de Janeiro','South America',12280702,NULL),"
                + "('Santiago','South America',5507282,NULL)"
                + ";";
        System.err.println(dataStr);
        db.execSQL(dataStr);
    }


    public void onLookup(View view) {
        Intent intent = new Intent(this, LookupActivity.class);
        startActivity(intent);
    }

    public void onAdd(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void onReset(View view) {
        String resetStr = "DROP TABLE IF EXISTS cities;";
        db.execSQL(resetStr);

        setupDatabase();
        populateDatabase();

        Context context = getApplicationContext();
        Toast.makeText(context, "Database Reset !", Toast.LENGTH_SHORT).show();
    }

}