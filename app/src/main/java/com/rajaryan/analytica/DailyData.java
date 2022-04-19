package com.rajaryan.analytica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DailyData extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner;
    TextView dt;
    TextView txt;
    String type;
    LinearLayout exp;
    String date,month,year;
    RecyclerView rec;
    float av_need,av_inv,av_med,av_ent,av_deb;
    EditText details,cost;
    Adapter adepter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_data);
        spinner=findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rec=findViewById(R.id.rec);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        txt=findViewById(R.id.txt);
        dt=findViewById(R.id.date);
        details=findViewById(R.id.details);
        cost=findViewById(R.id.exp);
        Intent i=getIntent();
        date=i.getStringExtra("Date");
        year=i.getStringExtra("Year");
        exp=findViewById(R.id.exp_img);
        month=i.getStringExtra("Month");
        Query query= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Expense").child(year).child(month).child(date);
        FirebaseRecyclerOptions<Tracker> option =
                new FirebaseRecyclerOptions.Builder<Tracker>()
                        .setQuery(query,Tracker.class)
                        .setLifecycleOwner(this)
                        .build();
        adepter=new Adapter(option);

        adepter.startListening();
        rec.setAdapter(adepter);

        rec.setLayoutManager(new LinearLayoutManager(this));
        dt.setText(date+ "/ "+ month +"/ "+year);
        DatabaseReference mb1= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Need Expense");
        mb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                av_need=Float.parseFloat(snapshot.getValue().toString());
                Toast.makeText(getApplicationContext(),String.valueOf(av_need),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb2=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Investment Expense");
        mb2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                av_inv=Float.parseFloat(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb3=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Medical Expense");
        mb3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                av_med=Float.parseFloat(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb4=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Entertainment Expense");
        mb4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                av_ent=Float.parseFloat(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Debt Expense");
        mb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                av_deb=Float.parseFloat(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void back(View view) {
        Intent i=new Intent(this,Ground.class);
        startActivity(i);
    }

    public class Adapter extends FirebaseRecyclerAdapter<Tracker,Adapter.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull  FirebaseRecyclerOptions<Tracker> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull  Adapter.viewholder viewholder, int i, @NonNull Tracker tracker) {
            viewholder.cost.setText(tracker.Cost);
            viewholder.details.setText(tracker.Details);
            viewholder.type.setText(tracker.Type);
            if(tracker.Type.equals("Basic Needs")){
                viewholder.tp.setImageResource(R.drawable.basket);
            }
            if(tracker.Type.equals("Investment")){
                viewholder.tp.setImageResource(R.drawable.investment);
            }
            if(tracker.Type.equals("Medical")){
                viewholder.tp.setImageResource(R.drawable.cardiogram);
            }
            if(tracker.Type.equals("Entertainment")){
                viewholder.tp.setImageResource(R.drawable.ent);
            }
            if(tracker.Type.equals("Debt")){
                viewholder.tp.setImageResource(R.drawable.debt);
            }
            String key=getRef(i).getKey();
            viewholder.details.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Dialog mDialog;
                    mDialog=new Dialog(getApplicationContext());
                    mDialog.setContentView(R.layout.delete);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.show();
                    TextView delete=mDialog.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            float ca=Float.parseFloat(tracker.Cost);
                            DatabaseReference mb1= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child("Need Expense");
                            mb1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    av_need=Float.parseFloat(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            DatabaseReference mb2=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child("Investment Expense");
                            mb2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    av_inv=Float.parseFloat(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            DatabaseReference mb3=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child("Medical Expense");
                            mb3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    av_med=Float.parseFloat(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            DatabaseReference mb4=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child("Entertainment Expense");
                            mb4.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    av_ent=Float.parseFloat(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            DatabaseReference mb=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child("Debt Expense");
                            mb.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    av_deb=Float.parseFloat(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            if(tracker.Type.equals("Basic Needs")){
                                float bn=av_need+Float.parseFloat(tracker.Cost);
                                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Need Expense");
                                db.setValue(String.valueOf(bn));
                            }
                            if(tracker.Type.equals("Investment")){
                                float bn=av_inv+Float.parseFloat(tracker.Cost);
                                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Investment Expense");
                                db.setValue(String.valueOf(bn));
                            }
                            if(tracker.Type.equals("Medical")){
                                float bn=av_med+Float.parseFloat(tracker.Cost);
                                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Medical Expense");
                                db.setValue(String.valueOf(bn));
                            }
                            if(tracker.Type.equals("Entertainment")){
                                float bn=av_ent+Float.parseFloat(tracker.Cost);
                                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Entertainment Expense");
                                db.setValue(String.valueOf(bn));
                            }
                            if(tracker.Type.equals("Debt")){
                                float bn=av_deb+Float.parseFloat(tracker.Cost);
                                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Debt Expense");
                                db.setValue(String.valueOf(bn));
                            }
                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Expense").child(year).child(month).child(date).child(key);
                            ref.removeValue();
                            mDialog.dismiss();
                        }
                    });
                    return false;
                }
            });
        }


        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exp_adepter, parent, false);
            return new viewholder(view);
        }

        @Override
        public int getItemCount() {
            if(super.getItemCount()>0){
                txt.setVisibility(View.VISIBLE);
                exp.setVisibility(View.GONE);
            }
            else {
                txt.setVisibility(View.GONE);
                exp.setVisibility(View.GONE);
            }
            return super.getItemCount();
        }

        public class viewholder extends RecyclerView.ViewHolder{
            TextView details,cost,type;
            ImageView tp;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                details=itemView.findViewById(R.id.details);
                cost=itemView.findViewById(R.id.cost);
                type=itemView.findViewById(R.id.type);
                tp=itemView.findViewById(R.id.tp);
            }
        }
    }

    @Override
    protected void onStart() {
        adepter.startListening();
        super.onStart();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void add(View view) {
        if(TextUtils.isEmpty(details.getText().toString())){
            Toast.makeText(getApplicationContext(),"Enter Valid Expense Details",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(cost.getText().toString())){
            Toast.makeText(getApplicationContext(),"Enter Valid Cost",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(type)){
            Toast.makeText(getApplicationContext(),"Select Valid Cost Type",Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Expense").child(year).child(month).child(date);
            HashMap<String ,String> hashMap=new HashMap<>();
            hashMap.put("Date",date);
            hashMap.put("Details",details.getText().toString());
            hashMap.put("Cost",cost.getText().toString());
            hashMap.put("Type",type);
            databaseReference.push().setValue(hashMap);
            Query query= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Expense").child(year).child(month).child(date);
            FirebaseRecyclerOptions<Tracker> option =
                    new FirebaseRecyclerOptions.Builder<Tracker>()
                            .setQuery(query,Tracker.class)
                            .setLifecycleOwner(this)
                            .build();
            adepter=new Adapter(option);
            adepter.startListening();
            rec.setAdapter(adepter);
            rec.setLayoutManager(new LinearLayoutManager(this));
            if(type.equals("Basic Needs")){
                float bn=av_need-Integer.parseInt(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Need Expense");
                db.setValue(String.valueOf(bn));
            }
            if(type.equals("Investment")){
                float bn=av_inv-Integer.parseInt(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Investment Expense");
                db.setValue(String.valueOf(bn));
            }
            if(type.equals("Medical")){
                float bn=av_med-Integer.parseInt(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Medical Expense");
                db.setValue(String.valueOf(bn));
            }
            if(type.equals("Entertainment")){
                float bn=av_ent-Integer.parseInt(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Entertainment Expense");
                db.setValue(String.valueOf(bn));
            }
            if(type.equals("Debt")){
                float bn=av_deb-Integer.parseInt(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Debt Expense");
                db.setValue(String.valueOf(bn));
            }

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(this,Ground.class);
        startActivity(i);
    }
}