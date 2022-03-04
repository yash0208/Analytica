package com.rajaryan.analytica;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Done#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Done extends BottomSheetDialogFragment implements AdapterView.OnItemSelectedListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner spinner;
    TextView dt;
    TextView txt;
    String type;
    LinearLayout exp;
    String date,month,year;
    Button add;
    RecyclerView rec;
    float av_need,av_inv,av_med,av_ent,av_deb;
    EditText details,cost;
    float cm;
    DailyData.Adapter adepter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Done() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Done.
     */
    // TODO: Rename and change types and number of parameters
    public static Done newInstance(String param1, String param2) {
        Done fragment = new Done();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_done, container, false);
        spinner=view.findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.expense_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rec=view.findViewById(R.id.rec);
        Calendar cal;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = simpleDateFormat.format(c);
        year=formattedDate.substring(0,4);
        month=formattedDate.substring(5,6);
        int m=Integer.parseInt(month)+1;
        month=String.valueOf(m);
        date=formattedDate.substring(8,10);
        Log.i(getClass().getName(), "Selected Day: "
                + year + "/" + (month + 1) + "/" + date);
        spinner.setAdapter(adapter);
        String cost1=getArguments().getString("cost");
        spinner.setOnItemSelectedListener(this);
        txt=view.findViewById(R.id.txt);
        add=view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        dt=view.findViewById(R.id.date);
        details=view.findViewById(R.id.details);
        cost=view.findViewById(R.id.exp);
        cost.setText(cost1);
        DatabaseReference mb1= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Need Expense");
        mb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                av_need=Float.parseFloat(snapshot.getValue().toString());
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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void add() {
        if(TextUtils.isEmpty(details.getText().toString())){
            Toast.makeText(getContext(),"Enter Valid Expense Details",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(cost.getText().toString())){
            Toast.makeText(getContext(),"Enter Valid Cost",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(type)){
            Toast.makeText(getContext(),"Select Valid Cost Type",Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Bills").child(year).child(month).child(date);
            HashMap<String ,String> hashMap=new HashMap<>();
            hashMap.put("Date",date);
            hashMap.put("Details",details.getText().toString());
            try{
               cm=Float.parseFloat(cost.getText().toString());
            } catch (NumberFormatException e) {
                cm=0;
                e.printStackTrace();
            }
            if(cm == 0){
                Toast.makeText(getContext(),"Enter Valid Amount",Toast.LENGTH_SHORT).show();
            }
            else {
                hashMap.put("Cost",String.valueOf(cm));
                String link=getArguments().getString("image_link");
                hashMap.put("Image",link);
                hashMap.put("Type",type);
                databaseReference.push().setValue(hashMap);
            }

            if(type.equals("Basic Needs")){
                float bn=av_need-Float.parseFloat(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Need Expense");
                db.setValue(String.valueOf(bn));
            }
            if(type.equals("Investment")){
                float bn=av_inv-Float.parseFloat(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Investment Expense");
                db.setValue(String.valueOf(bn));
            }
            if(type.equals("Medical")){
                float bn=av_med-Float.parseFloat(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Medical Expense");
                db.setValue(String.valueOf(bn));
            }
            if(type.equals("Entertainment")){
                float bn=av_ent-Float.parseFloat(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Entertainment Expense");
                db.setValue(String.valueOf(bn));
            }
            if(type.equals("Debt")){
                float bn=av_deb-Float.parseFloat(cost.getText().toString());
                DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Debt Expense");
                db.setValue(String.valueOf(bn));
            }
            Intent i=new Intent(getActivity(),Base.class);
            startActivity(i);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        type=adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}