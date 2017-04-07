package com.example.user.myapplicationfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by user on 2017/3/17.
 */

public class MSG  extends AppCompatActivity {
    TextView tv1,tv2;
    EditText et1;
    Button bt1;
    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("USER");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record2);
        tv1  = (TextView)findViewById(R.id.tv1);
        et1 = (EditText)findViewById(R.id.et1);
        bt1 = (Button)findViewById(R.id.bt1);
        ListView listView = (ListView) findViewById(R.id.lv1);
        //   tv2  = (TextView)findViewById(R.id.tv2);
        Bundle bundle = getIntent().getExtras();
        final String name = bundle.getString("name");
        tv1.setText("Welcome  "+name);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        listView.setAdapter(adapter);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String y  = et1.getText().toString();
                reference_contacts.child(name).child("MSG").setValue(y);

            }
        });
        reference_contacts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    adapter.add(ds.child("account").getValue().toString() + "          " + ds.child("MSG").getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tv1.setText("帳號或密碼錯誤");
            }
        });


    }


}