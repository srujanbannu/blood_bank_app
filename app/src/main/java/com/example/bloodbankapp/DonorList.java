package com.example.bloodbankapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class DonorList extends AppCompatActivity {
    String city;
    String group;
    String add;
    ArrayList<String> donorList,adlist,conlist;
    ListView listView,addlis,conlis;
    ArrayAdapter<String> arrayAdapter,addadepetr,conadepter;
    public static ArrayList<Donor> donorInfo,addinof,coninof;
    Button b1,c;
    LinearLayout la;
    TextView t1,t2;
    ScrollView srl;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        city = extras.getString("city");
        group = extras.getString("group");
        add = extras.getString("add");
        Log.i("NAME",city);
        Log.i("NAME",group);


        c = findViewById(R.id.c);
        b1 = findViewById(R.id.request);


        donorList = new ArrayList<>();
        adlist = new ArrayList<>();
        conlist = new ArrayList<>();


        donorInfo = new ArrayList<>();
        addinof = new ArrayList<>();
        coninof = new ArrayList<>();


        addlis = new ListView(DonorList.this);
        conlis = new ListView(DonorList.this);
        listView =  findViewById(R.id.list_donor);


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, donorList);
        addadepetr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, adlist);

        listView.setAdapter(arrayAdapter);
        addlis.setAdapter(addadepetr);
        conlis.setAdapter(conadepter);

        listView.getMaxScrollAmount();
        la=findViewById(R.id.linlay);
        listView.canScrollHorizontally(View.FOCUS_LEFT);

        t1 = findViewById(R.id.na);
        t2 = findViewById(R.id.ag);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l)
            {
                    la.setVisibility(View.VISIBLE);
                    String name = listView.getItemAtPosition(i).toString();
                    String str = addlis.getItemAtPosition(i).toString();
                    sai(name, str);

            }
        });


        DatabaseReference myRef = MainActivity.database.getReference("donors");
        myRef.child(city).child(group).addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s)
            {
                final Donor donor = dataSnapshot.getValue(Donor.class);
                donorInfo.add(donor);
                assert donor != null;
                String donorInfo = donor.name;

                String addInfo = "ADDRESS :"+donor.add+" NUMBER :"+donor.contuctNumber;
                adlist.add(addInfo);

                donorList.add(donorInfo);
                arrayAdapter.notifyDataSetChanged();
                t2.setText(donor.bloodGroup);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void sai(String s1, final String s2){
        final DatabaseReference ref,requsts,user,stats,list;
        ref = FirebaseDatabase.getInstance().getReference();
        requsts = ref.child("reguests");
        final FirebaseUser maut = FirebaseAuth.getInstance().getCurrentUser();
        assert maut != null;
        user = requsts.child(Objects.requireNonNull(maut.getDisplayName()));
        list = user.child(s1);
        stats = list.child("STATUS");
        t1.setText(s1);
        b1.setText("Check status");
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try {
                    list.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            String s = dataSnapshot.child("STATUS").getValue(String.class);
                            try {
                                assert s != null;
                                if (s.equals("T"))
                                {
                                    b1.setText("Accepted");
                                    b1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightGreen));
                                    donorList.add(String.valueOf(addinof));
                                    try {
                                        t2.setText(s2);
                                    }catch (Exception e){
                                        Toast.makeText(DonorList.this, e+"", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else if (s.equals("R"))
                                {
                                    b1.setText("Rejected");
                                }
                                else
                                    {
                                    b1.setText("Pending");
                                        b1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.yellow));
                                    }
                            }
                            catch (Exception e)
                            {
                                b1.setText("Request sent");
                                stats.setValue("F");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });
                }catch
                (Exception e){
                    Toast.makeText(DonorList.this, e+"", Toast.LENGTH_SHORT).show();
                    stats.setValue("F");
                }
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                la.setVisibility(View.INVISIBLE);
            }
        });

    }
}
