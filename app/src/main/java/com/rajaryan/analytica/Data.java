package com.rajaryan.analytica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Data extends AppCompatActivity {
    TextView total,need,inv,med,debt,ent;
    String month,date,year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        total=findViewById(R.id.tinc);
        need=findViewById(R.id.need);
        med=findViewById(R.id.medical);
        inv=findViewById(R.id.inv);
        debt=findViewById(R.id.deb);
        ent=findViewById(R.id.ent);
        Calendar cal;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = simpleDateFormat.format(c);
        year=formattedDate.substring(0,4);
        month=formattedDate.substring(5,6);
        int m=Integer.parseInt(month)+1;
        month=String.valueOf(m);
        date=formattedDate.substring(8,10);
        DatabaseReference mb1= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Need Expense");
        mb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float av_need=Float.parseFloat(snapshot.getValue().toString());
                need.setText(String.valueOf(av_need));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb2= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Income");
        mb2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float av_need=Float.parseFloat(snapshot.getValue().toString());
                total.setText(String.valueOf(av_need));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb3= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Investment Expense");
        mb3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float av_need=Float.parseFloat(snapshot.getValue().toString());
                inv.setText(String.valueOf(av_need));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb4= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Medical Expense");
        mb4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float av_need=Float.parseFloat(snapshot.getValue().toString());
                med.setText(String.valueOf(av_need));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb5= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Debt Expense");
        mb5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float av_need=Float.parseFloat(snapshot.getValue().toString());
                debt.setText(String.valueOf(av_need));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb6= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Entertainment Expense");
        mb6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                float av_need=Float.parseFloat(snapshot.getValue().toString());
                ent.setText(String.valueOf(av_need));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,Ground.class);
        startActivity(i);
    }
}