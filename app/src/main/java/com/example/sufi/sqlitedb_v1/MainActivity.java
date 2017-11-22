package com.example.sufi.sqlitedb_v1;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sufi.sqlitedb_v1.database.DatabaseHelper;
import com.example.sufi.sqlitedb_v1.model.Student;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText eTusername, eTpassword, eTcgpa, eTphone;
    ListView listViewStudents;
    ArrayList<Student> arrayListStudents;
    ArrayAdapter<Student> adapter;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTusername = (EditText) findViewById(R.id.editTextUsername);
        eTpassword = (EditText) findViewById(R.id.editTextPassword);
        eTcgpa = (EditText) findViewById(R.id.editTextCGPA);
        eTphone = (EditText) findViewById(R.id.editTextPhone);

        dbhelper = new DatabaseHelper(MainActivity.this);

        listViewStudents = (ListView) findViewById(R.id.listViewStudent);
        arrayListStudents = dbhelper.getAllStudentData();
        adapter = new ArrayAdapter<Student>(MainActivity.this, R.layout.listview_item_layout, arrayListStudents);
        listViewStudents.setAdapter(adapter);

        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Person Details");
                dialog.setMessage(arrayListStudents.get(position).toStringStudent());
                dialog.setCancelable(false);

                dialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.show();
            }
        });

        listViewStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Delete");
                dialog.setMessage("Do you want to delete this student");
                dialog.setCancelable(false);

                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dbhelper.deleteStudent(arrayListStudents.get(position).getId())) {
                            Toast.makeText(MainActivity.this, "Data is deleted successfully", Toast.LENGTH_LONG).show();
                            arrayListStudents.clear();
                            arrayListStudents.addAll(dbhelper.getAllStudentData());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainActivity.this, "Data is not deleted", Toast.LENGTH_LONG).show();
                        }
                    }

                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }


    public void showData(View view) {

        boolean error = false;
        Student std = new Student();
        String userName = eTusername.getText().toString().trim();

        if (userName.isEmpty()) {
            error = true;
            eTusername.setError("Username is missing!");
        } else {
            if (userName.length() < 5) {
                error = true;
                eTusername.setError("Username is too short!");
            } else {
                std.setUsername(userName);
            }
        }

        std.setPassword(eTpassword.getText().toString());

        String cgpaString = eTcgpa.getText().toString().trim();
        if (cgpaString.isEmpty()) {
            eTcgpa.setError("CGPA is missing!");
        } else {
            Float cgpa = Float.parseFloat(cgpaString);
            if (cgpa <= 4.00 && cgpa > 0) {
                std.setCgpa(cgpa);
            } else {
                eTcgpa.setError("CGPA is not valid!");
            }
        }

        String phoneNo = eTphone.getText().toString().trim();
        if (phoneNo.isEmpty()) {
            error = true;
            eTphone.setError("PhoneNo is missing!");
        } else if (phoneNo.length() == 11) {
            if (phoneNo.startsWith("017") || phoneNo.startsWith("018") || phoneNo.startsWith("019") || phoneNo.startsWith("016")) {
                std.setPhone(phoneNo);
            } else {
                error = true;
                eTphone.setError("PhoneNo is not valid!");
            }
        } else if (phoneNo.length() == 14) {
            if (phoneNo.startsWith("+880")) {
                std.setPhone(phoneNo);
            } else {
                error = true;
                eTphone.setError("PhoneNo should start with +880");
            }
        } else {
            error = true;
            eTphone.setError("PhoneNo should be 11 or 14 digits");
        }

        if (error) {
            Toast.makeText(MainActivity.this, "Please insert proper data!", Toast.LENGTH_LONG).show();
        } else {
            //arrayListStudents.add(std);

            dbhelper.insertStudent(std);
            arrayListStudents.clear();
            arrayListStudents.addAll(dbhelper.getAllStudentData());
            Log.i("Get Data", arrayListStudents.toString());
            adapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Data is saved properly!!!", Toast.LENGTH_LONG).show();
            emptyField();
        }
    }

    public void clearData(View view) {
        emptyField();
    }

    public void exitSystem(View view) {
        emptyField();
        MainActivity.this.finish();
    }

    public void emptyField() {
        eTusername.setText(null);
        eTpassword.setText(null);
        eTcgpa.setText(null);
        eTphone.setText(null);
    }
}



