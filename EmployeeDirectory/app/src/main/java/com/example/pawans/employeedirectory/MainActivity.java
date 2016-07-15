package com.example.pawans.employeedirectory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    EditText searchText;
    ListAdapter adapter;
    ListView lv_emp;
    Cursor cursor;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = (EditText)findViewById(R.id.searchText);
        db = (new DataBaseHelper(this)).getWritableDatabase();

    }
    public void onListItemClick(ListView parent, View view, int position, long id){

        Intent intent = new Intent(this, EmployeeDetails.class);
        Cursor cursor = (Cursor) adapter.getItem(position);
        intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("id")));
        startActivity(intent);
    }
    public void search(View view){

        cursor = db.rawQuery("SELECT _id, firstName, lastName, title FROM employee WHERE firstName || ' ' || lastName LIKE ?",
                new String[]{"%" + searchText.getText().toString() + "%"});


        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{"firstname","lastname","title"},
                new int[]{R.id.firstName,R.id.lastName,R.id.title});

        lv_emp = (ListView)findViewById(R.id.list_parent);
        lv_emp.setAdapter(adapter);
    }



}
