package com.example.bloodbankapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import static com.example.bloodbankapp.MainActivity.database;

public class DonorForm extends AppCompatActivity {
    Spinner cityChoice;
    Spinner groupChoice;

    EditText Name;
    EditText Mobile,add;
    ProgressDialog pm;
    Button Save;

    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form);
        pm = new ProgressDialog(this);
        pm.setMessage("loading...");
        cityChoice =  findViewById(R.id.dropdownCity);

        String[] citis = new String[]{"Guntur","Vizag", "Amravathi", "HYD","Tenali", "Vijayawada", "Thirupathi", "Kadapa"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citis);
        cityChoice.setAdapter(adapter);


        groupChoice = (Spinner) findViewById(R.id.dropdownGroup);
        String[] group = new String[]{"O+","O-", "A+", "B+","A-", "B-", "AB+", "AB-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, group);
        groupChoice.setAdapter(adapter1);

        Name = (EditText) findViewById(R.id.edt_name);
        Mobile = (EditText) findViewById(R.id.edt_mobileNumber);
        Save = (Button) findViewById(R.id.btn_saveDonor);
        add = findViewById(R.id.add);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String city = cityChoice.getSelectedItem().toString();
                String group = groupChoice.getSelectedItem().toString();
                String mobile = Mobile.getText().toString();
                String lat = MainActivity.lat.toString();
                String lng = MainActivity.lng.toString();
                String a = add.getText().toString();

                if (name.isEmpty() || city.isEmpty() || group.isEmpty() || mobile.isEmpty() || a.isEmpty())
                {
                    Toast.makeText(DonorForm.this, "All fields mandetory", Toast.LENGTH_SHORT).show();
                } else
                    {
                    pm.show();
                    pm.setCancelable(false);
                    Donor donor = new Donor(name, mobile, group, city, lat, lng, a);
                    DatabaseReference myRef = database.getReference("donors");
                    myRef.child(city).child(group).push().setValue(donor).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pm.dismiss();
                            Toast.makeText(DonorForm.this, "Sucess fully uploaded", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pm.dismiss();
                            Toast.makeText(DonorForm.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });

    }
}
