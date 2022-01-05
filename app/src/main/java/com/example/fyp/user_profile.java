package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class user_profile extends AppCompatActivity {

    private EditText name, birth, phone, ic, email, gender;
    private Button save;

    private FirebaseAuth auth;
    private DatabaseReference dbref;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        name = findViewById(R.id.et_name);
        birth = findViewById(R.id.et_birthday);
        phone = findViewById(R.id.et_phone);
        ic = findViewById(R.id.et_ic);
        email = findViewById(R.id.et_email);
        gender = findViewById(R.id.et_gender);
        save = findViewById(R.id.bt_save);

        name.setText(getIntent().getStringExtra("name1").toString());
        birth.setText(getIntent().getStringExtra("birth1").toString());
        phone.setText(getIntent().getStringExtra("phone1").toString());
        ic.setText(getIntent().getStringExtra("ic1").toString());
        email.setText(getIntent().getStringExtra("email1").toString());
        gender.setText(getIntent().getStringExtra("gender1").toString());

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        dbref = db.getReference("Users");

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().length()!=0 && birth.getText().length()!=0 && phone.getText().length()!=0 && ic.getText().length()!=0 && email.getText().length()!=0 && gender.getText().length()!=0){
                            String uid = auth.getUid();
                            String name1 = name.getText().toString();
                            String birth1 = birth.getText().toString();
                            String phone1 = phone.getText().toString();
                            String ic1 = ic.getText().toString();
                            String email1 = email.getText().toString();
                            String gender1 = gender.getText().toString();

                            dbref.child(uid).child("user_name").setValue(name1);
                            dbref.child(uid).child("user_birth").setValue(birth1);
                            dbref.child(uid).child("user_phone").setValue(phone1);
                            dbref.child(uid).child("user_ic").setValue(ic1);
                            dbref.child(uid).child("user_email").setValue(email1);
                            dbref.child(uid).child("user_gender").setValue(gender1);
                            Toast.makeText(user_profile.this,"Profile Update Successful!",Toast.LENGTH_SHORT).show();
                            Intent intent2main = new Intent(user_profile.this, user_main.class);
                            startActivity(intent2main);
                        }else {
                            Toast.makeText(user_profile.this,"Fail to Update Profile!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}