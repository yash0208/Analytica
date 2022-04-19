package com.rajaryan.analytica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.compose.animation.core.Easing;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Budget extends AppCompatActivity implements OnChartValueSelectedListener{
    EditText income,debt,med;
    private PieChart pieChart;
    boolean available;
    Button budget;
    String month,date,year;
    CardView card;
    String[] info={"Basic Needs","Investment","Medical","Entertainment","Debt"};
    float ic,dc,mc,tc,fi,be,ie,me,ee,de,df,mf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        pieChart = findViewById(R.id.chart1);
        setupPieChart();
        loadPieChartData();
        card=findViewById(R.id.card);
        AnyChartView anyChartView = findViewById(R.id.chart);
        Pie pie = AnyChart.pie();
        income=findViewById(R.id.income);
        debt=findViewById(R.id.debt);
        available=false;
        med=findViewById(R.id.mc);
        Calendar cal;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = simpleDateFormat.format(c);
        year=formattedDate.substring(0,4);
        month=formattedDate.substring(5,6);
        int m=Integer.parseInt(month)+1;
        month=String.valueOf(m);
        date=formattedDate.substring(8,10);
        DatabaseReference mb1= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Income");
        mb1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    available=true;
                    float av_need=Float.parseFloat(snapshot.getValue().toString());
                    income.setText(String.valueOf(av_need));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb2= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("EMI");
        mb2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    float av_need=Float.parseFloat(snapshot.getValue().toString());
                    debt.setText(String.valueOf(av_need));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb3= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month).child("Medical Debt");
        mb3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    float av_need=Float.parseFloat(snapshot.getValue().toString());
                    med.setText(String.valueOf(av_need));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(Budget.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Basic Needs", 50));
        data.add(new ValueDataEntry("Investment", 20));
        data.add(new ValueDataEntry("Medical", 10));
        data.add(new ValueDataEntry("Entertainment", 12));
        data.add(new ValueDataEntry("Debt", 8));
        pie.data(data);

        pie.title("Standard Expense Chart");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Expense Type")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        anyChartView.setChart(pie);

        budget=findViewById(R.id.genrate);
        budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(available) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Dialog mDialog;
                            mDialog = new Dialog(Budget.this);
                            mDialog.setContentView(R.layout.update);
                            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            mDialog.show();
                            TextView delete = mDialog.findViewById(R.id.delete);
                            TextView update= mDialog.findViewById(R.id.update);
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i=new Intent(Budget.this,Base.class);
                                            startActivity(i);
                                        }
                                    });
                                }
                            });
                            update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(TextUtils.isEmpty(income.getText().toString())){
                                        Toast.makeText(getApplicationContext(),"Enter Valid Income Amount",Toast.LENGTH_SHORT).show();
                                    }
                                    if(TextUtils.isEmpty(debt.getText().toString())){
                                        Toast.makeText(getApplicationContext(),"Enter Valid Debt Amount Or Enter 0 If NA",Toast.LENGTH_SHORT).show();
                                    }
                                    if(TextUtils.isEmpty(med.getText().toString())){
                                        Toast.makeText(getApplicationContext(),"Enter Valid Income Amount Or Enter 0 If NA",Toast.LENGTH_SHORT).show();
                                    }
                                    else {

                                        card.setVisibility(View.VISIBLE);
                                        ic=Float.parseFloat(income.getText().toString());
                                        mc=Float.parseFloat(med.getText().toString());
                                        dc=Float.parseFloat(debt.getText().toString());
                                        //Total Pre Paid Amount
                                        tc=mc+dc;
                                        //Final Available Amount
                                        fi=ic-tc;
                                        //Get ALl Sectional Expense From Final Amount
                                        be= (float) (fi*0.5);
                                        ie= (float) (fi*0.2);
                                        me= (float) (fi*0.1);
                                        ee= (float) (fi*0.12);
                                        de= (float) (fi*0.08);
                                        //Final Amount For Debt And Medical
                                        df= de+dc;
                                        mf= me+mc;
                                        AnyChartView anyChartView = findViewById(R.id.chart2);
                                        Pie pie2 = AnyChart.pie();
                                        pie2.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                                            @Override
                                            public void onClick(Event event) {
                                                Toast.makeText(Budget.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                                        String personName = account.getDisplayName();
                                        String personGivenName = account.getGivenName();
                                        String personFamilyName = account.getFamilyName();
                                        String personEmail = account.getEmail();
                                        String personId = account.getId();

                                        HashMap<String,String> hashMap=new HashMap<>();
                                        hashMap.put("Uid", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                        hashMap.put("Name",personName);
                                        hashMap.put("Debt Expense", String.valueOf(df));
                                        hashMap.put("EMI",String.valueOf(dc));
                                        hashMap.put("Entertainment Expense",String.valueOf(ee));
                                        hashMap.put("Income",String.valueOf(ic));
                                        hashMap.put("Investment Expense",String.valueOf(ie));
                                        hashMap.put("Medical Debt",String.valueOf(mc));
                                        hashMap.put("Medical Expense",String.valueOf(mf));
                                        hashMap.put("Need Expense",String.valueOf(be));
                                        DatabaseReference medicines = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month);
                                        medicines.setValue(hashMap);
                                        List<DataEntry> data = new ArrayList<>();
                                        data.add(new ValueDataEntry("Basic Needs", be));
                                        data.add(new ValueDataEntry("Investment", ie));
                                        data.add(new ValueDataEntry("Medical", mf));
                                        data.add(new ValueDataEntry("Entertainment", ee));
                                        data.add(new ValueDataEntry("Debt", df));
                                        pie2.data(data);

                                        pie2.title("Your Expense Chart");

                                        pie2.labels().position("outside");

                                        pie2.legend().title().enabled(true);
                                        pie2.legend().title()
                                                .text("Expense Type")
                                                .padding(0d, 0d, 10d, 0d);

                                        pie2.legend()
                                                .position("center-bottom")
                                                .itemsLayout(LegendLayout.HORIZONTAL)
                                                .align(Align.CENTER);
                                        anyChartView.setChart(pie2);
                                    }
                                    mDialog.dismiss();
                                }
                            });
                        }
                    });

                }
                else {
                    if(TextUtils.isEmpty(income.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Enter Valid Income Amount",Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(debt.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Enter Valid Debt Amount Or Enter 0 If NA",Toast.LENGTH_SHORT).show();
                    }
                    if(TextUtils.isEmpty(med.getText().toString())){
                        Toast.makeText(getApplicationContext(),"Enter Valid Income Amount Or Enter 0 If NA",Toast.LENGTH_SHORT).show();
                    }
                    else {

                        card.setVisibility(View.VISIBLE);
                        ic=Float.parseFloat(income.getText().toString());
                        mc=Float.parseFloat(med.getText().toString());
                        dc=Float.parseFloat(debt.getText().toString());
                        //Total Pre Paid Amount
                        tc=mc+dc;
                        //Final Available Amount
                        fi=ic-tc;
                        //Get ALl Sectional Expense From Final Amount
                        be= (float) (fi*0.5);
                        ie= (float) (fi*0.2);
                        me= (float) (fi*0.1);
                        ee= (float) (fi*0.12);
                        de= (float) (fi*0.08);
                        //Final Amount For Debt And Medical
                        df= de+dc;
                        mf= me+mc;
                        AnyChartView anyChartView = findViewById(R.id.chart2);
                        Pie pie2 = AnyChart.pie();
                        pie2.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                            @Override
                            public void onClick(Event event) {
                                Toast.makeText(Budget.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
                            }
                        });
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        String personName = account.getDisplayName();
                        String personGivenName = account.getGivenName();
                        String personFamilyName = account.getFamilyName();
                        String personEmail = account.getEmail();
                        String personId = account.getId();

                        HashMap<String,String> hashMap=new HashMap<>();
                        hashMap.put("Uid", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                        hashMap.put("Name",personName);
                        hashMap.put("Debt Expense", String.valueOf(df));
                        hashMap.put("EMI",String.valueOf(dc));
                        hashMap.put("Entertainment Expense",String.valueOf(ee));
                        hashMap.put("Income",String.valueOf(ic));
                        hashMap.put("Investment Expense",String.valueOf(ie));
                        hashMap.put("Medical Debt",String.valueOf(mc));
                        hashMap.put("Medical Expense",String.valueOf(mf));
                        hashMap.put("Need Expense",String.valueOf(be));
                        DatabaseReference medicines = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Budget").child(month);
                        medicines.setValue(hashMap);

                        List<DataEntry> data = new ArrayList<>();
                        data.add(new ValueDataEntry("Basic Needs", be));
                        data.add(new ValueDataEntry("Investment", ie));
                        data.add(new ValueDataEntry("Medical", mf));
                        data.add(new ValueDataEntry("Entertainment", ee));
                        data.add(new ValueDataEntry("Debt", df));
                        pie2.data(data);

                        pie2.title("Your Expense Chart");

                        pie2.labels().position("outside");

                        pie2.legend().title().enabled(true);
                        pie2.legend().title()
                                .text("Expense Type")
                                .padding(0d, 0d, 10d, 0d);

                        pie2.legend()
                                .position("center-bottom")
                                .itemsLayout(LegendLayout.HORIZONTAL)
                                .align(Align.CENTER);
                        anyChartView.setChart(pie2);
                    }
                }
            }
        });
    }
    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(13);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending by Category");
        pieChart.setCenterTextSize(20);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.rgb(25,25,35));
        pieChart.setCenterTextColor(Color.WHITE);
        l.setEnabled(false);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(50, "Basic Needs"));
        entries.add(new PieEntry(20, "Investment"));
        entries.add(new PieEntry(10, "Medical"));
        entries.add(new PieEntry(12, "Entertainment"));
        entries.add(new PieEntry(8, "Debt"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);

        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar cal;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = simpleDateFormat.format(c);
        year=formattedDate.substring(0,4);
        month=formattedDate.substring(5,6);
        int m=Integer.parseInt(month)+1;
        month=String.valueOf(m);
        date=formattedDate.substring(8,10);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void home(View view) {
        Intent i=new Intent(Budget.this,Ground.class);
        i.putExtra("status","2");
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i=new Intent(this,Ground.class);
        startActivity(i);
    }
}