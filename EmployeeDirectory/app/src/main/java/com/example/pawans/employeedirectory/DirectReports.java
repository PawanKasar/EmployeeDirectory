package com.example.pawans.employeedirectory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
@SuppressWarnings("deprecation")
public class DirectReports extends AppCompatActivity {

    protected Cursor cursor=null;
    protected ListAdapter adapter;
    ListView lv_report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direct_reports);

        lv_report = (ListView)findViewById(R.id.empreport);

        SQLiteDatabase db = (new DataBaseHelper(this)).getWritableDatabase();

        int employeeId = getIntent().getIntExtra("EMPLOYEE_ID", 0);

        Cursor cursor = db.rawQuery("SELECT _id, firstName, lastName, title FROM employee WHERE _id = ?",
                new String[]{""+employeeId});

        if (cursor.getCount() != 1)
        {
            return;
        }

        cursor.moveToFirst();

        TextView employeeNameText = (TextView) findViewById(R.id.employeeName);
        employeeNameText.setText(cursor.getString(cursor.getColumnIndex("firstName")) + " " + cursor.getString(cursor.getColumnIndex("lastName")));

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText(cursor.getString(cursor.getColumnIndex("title")));

        cursor = db.rawQuery("SELECT _id, firstName, lastName, title, officePhone, cellPhone, email FROM employee WHERE managerId = ?",
                new String[]{""+employeeId});
        adapter = new SimpleCursorAdapter(
                this,
                R.layout.employee_list_item,
                cursor,
                new String[] {"firstName", "lastName", "title"},
                new int[] {R.id.firstName, R.id.lastName, R.id.title});
        lv_report.setAdapter(adapter);

    }

    public void onListItemClick(ListView parent, View view, int position, long id) {
        Intent intent = new Intent(this, EmployeeDetails.class);
        Cursor cursor = (Cursor) adapter.getItem(position);
        intent.putExtra("EMPLOYEE_ID", cursor.getInt(cursor.getColumnIndex("_id")));
        startActivity(intent);
    }
}
