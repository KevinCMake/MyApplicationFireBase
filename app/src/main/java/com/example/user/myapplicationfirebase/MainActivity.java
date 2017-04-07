package com.example.user.myapplicationfirebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button bt1,bt2;
    EditText et1,et2;
    TextView tv1;
    String s,y;
    DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("USER");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);
        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        tv1  = (TextView)findViewById(R.id.tv1);
      //  ListView listView = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
      //  listView.setAdapter(adapter);

//        data.put("name","OP");
//        ur.updateChildren(data);

//        reference_contacts.child("user").child("pas").setValue("5678");
//        reference_contacts.child("user").child("account").setValue("user");

      // ur.child("MSG").setValue("1234");
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,KIL.class );
                startActivity(intent);

            }
        });
       bt1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               s = et1.getText().toString();
               y = et2.getText().toString();
               reference_contacts.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       adapter.clear();
                       for (DataSnapshot ds : dataSnapshot.getChildren() ){
                           if(ds.child("account").getValue().toString().equals(s)&&ds.child("pas").getValue().toString().equals(y)) {
                               tv1.setText("welcome"+ds.child("account").getValue().toString() );
                               Intent intent = new Intent();
                               intent.setClass(MainActivity.this,MSG.class);
                               Bundle bundle = new Bundle();
                               bundle.putString("name",s);//傳遞String
                               intent.putExtras(bundle);
                               startActivity(intent);
                               break;
                           }
                           else{
                              tv1 .setText("帳號或密碼錯誤");
                           }
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {
                       tv1.setText("帳號或密碼錯誤");
                   }
               });
           }
       });
       // DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("contacts");
       // reference_contacts.child("user").child("pas").setValue("5678");

    }
}
