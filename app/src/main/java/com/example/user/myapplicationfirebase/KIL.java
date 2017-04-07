package com.example.user.myapplicationfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class KIL extends AppCompatActivity {
    EditText et1,et2;
    String h[];
    Button bt1;
    String y,x;
    String g[];
    int o = 0;
    int a = 0;
    int b = 0;
    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("USER");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record3);
        et1 = (EditText)findViewById(R.id.et1);
        et2 =(EditText)findViewById(R.id.et2);
        bt1 = (Button) findViewById(R.id.bt1);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                y = et1.getText().toString();
                x = et2.getText().toString();
                reference_contacts.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){
                            a++;
                        }
                        h = new String[a+1];

                        adapter.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren() ){

                                h[b] = ds.child("account").getValue().toString();
                                b++;
                                if(h[b]==null){
                                    break;
                                }


                        }
                        for(int i = 0;i<h.length-1;i++){
                          if(y.equals(h[i])) {
                              o = 1;
                          }
                        }
                        if(o ==0){
                            if(x!=null&&y!=null) {
                                reference_contacts.child(y).child("account").setValue(y);
                                reference_contacts.child(y).child("pas").setValue(x);
                                reference_contacts.child(y).child("MSG").setValue("");
                            }
                            x = null;
                            y = null;
                            reference_contacts.removeEventListener(this);
                            Intent intent = new Intent();
                            intent.setClass(KIL.this,MainActivity.class );
                            startActivity(intent);
                            KIL.this.finish();

                        }

                        else if(o==1){
                            Toast.makeText(getApplicationContext(), "ˋ帳號重複", Toast.LENGTH_LONG).show();
                            et1.setText("");
                            et2.setText("");

                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }

                });



            }
        });

    }
}