package com.rajaryan.analytica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class Base extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;
    LinearLayout inv;
    TextView sensex,sensexc,nift,niftc,niff,niffc,gold,goldc;
    EditText search;
    String artical_link;
    Button open1;
    ScrollView scrollView;
    String name11  ,change  ,low_lev ,high_lev,open    ,close   ,pe22    ,cap22   ,cap2    ,value2  ,pe2     ,fpe2    ,tpe2    ,dhigh2  ,dlow2   ,drange2 ,vol2    ,tvm2    ,r522    ,l522    ,h522    ,a502    ,c502    ,a2002   ,c2002   ,espf2   ,esp122 , espy2 ,  espp2;
    CalendarView calendar;
    LinearLayout exp;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    String date;

    Adapter4 adepter;
    String year;
    ImageView bud;
    RecyclerView rec;
    RecyclerView rec_inv;
    String i1,i2,i3;
    RoundedImageView t1,t2,t3;
    ImageView eco;
    float av_need,av_inv,av_med,av_ent,av_deb;

    public String[] images;
    TextView txt,txt1;
    String month;
    private ArrayList<String> arrayList = new ArrayList<>();
    FloatingActionButton floatingActionButton,fb2;
    private static final String ARG_PARAM2 = "param2";
    Adapter2 adapter2;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPager2 viewPager2;
    String income;
    RecyclerView rec1;
    FrameLayout news_lay;
    Adapter3 adapter3;
    String pp,pc;
    String price,change1;
    TextView artical,type;
    FrameLayout base;
    String Sym;
    RecyclerView rec_base;
    ImageView image;
    Adapter adapter;
    ViewPager2 viewPager3;
    EditText search_stock;
    Adapter5 adepter5;
    String status="2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        bottomNavigation=findViewById(R.id.bottomnavigation);

        news_lay=findViewById(R.id.news_lay);
        base=findViewById(R.id.home);
        inv=findViewById(R.id.inv);

        scrollView=findViewById(R.id.scroll);

        scrollView.setSmoothScrollingEnabled(true);
        sensex=findViewById(R.id.sensex);
        sensexc=findViewById(R.id.sensexc);
        nift=findViewById(R.id.nift);//50
        niffc=findViewById(R.id.niftc);//50
        niff=findViewById(R.id.nifb);
        niftc=findViewById(R.id.niftb);
        gold=findViewById(R.id.gold);
        goldc=findViewById(R.id.goldc);


        //Coding For Investment Layout


        type=findViewById(R.id.type);
        artical=findViewById(R.id.artical);
        image=findViewById(R.id.image);
        open1=findViewById(R.id.open);
        search=findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listStock(search.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                listStock(search.getText().toString());
            }
        });
        Setcontent();
        String toast=search.getText().toString();
        DatabaseReference artical_title= FirebaseDatabase.getInstance().getReference().child("Articals").child("Tittle");
        artical_title.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String val=snapshot.getValue().toString();
                artical.setText(val);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference artical_title1=FirebaseDatabase.getInstance().getReference().child("Articals").child("Image");
        artical_title1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String val=snapshot.getValue().toString();
                Picasso.get().load(val).into(image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference artical_title2=FirebaseDatabase.getInstance().getReference().child("Articals").child("Link");
        artical_title2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                artical_link=snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        open1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Base.this,WebLayout.class);
                i.putExtra("link",artical_link);
                startActivity(i);
            }
        });
        Query query= FirebaseDatabase.getInstance().getReference().child("Companies");
        FirebaseRecyclerOptions<Stock> option =
                new FirebaseRecyclerOptions.Builder<Stock>()
                        .setQuery(query,Stock.class)
                        .setLifecycleOwner(this)
                        .build();
        rec_inv=findViewById(R.id.rec_inv);
        adapter=new Adapter(option);
        adapter.startListening();
        rec_inv.setAdapter(adapter);
        rec_inv.setLayoutManager(new LinearLayoutManager(this));
        //Coding For Home

        calendar=findViewById(R.id.calender);
        floatingActionButton=findViewById(R.id.floating_action_button);
        CollapsibleCalendar collapsibleCalendar = findViewById(R.id.cal22);
        eco=findViewById(R.id.data);
        txt1=findViewById(R.id.txt1);
        txt=findViewById(R.id.txt);
        bud=findViewById(R.id.buf);
        rec1=findViewById(R.id.rec1);
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
        fb2=findViewById(R.id.floating_action_button2);
        bud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Base.this,Budget.class);
                startActivity(i);
            }
        });
        eco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Base.this,Data.class);
                startActivity(i);
            }
        });
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDayChanged() {

            }

            @Override
            public void onClickListener() {

            }

            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
                date= String.valueOf(day.getDay());
                year= String.valueOf(day.getYear());
                month= String.valueOf(day.getMonth() + 1);
                Query query= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Expense").child(year).child(month).child(date);
                FirebaseRecyclerOptions<Tracker> option =
                        new FirebaseRecyclerOptions.Builder<Tracker>()
                                .setQuery(query,Tracker.class)
                                .setLifecycleOwner(Base.this)
                                .build();
                adepter=new Adapter4(option);
                txt.setVisibility(View.GONE);
                adepter.startListening();
                rec.setAdapter(adepter);
                rec.setLayoutManager(new LinearLayoutManager(Base.this));
                Query query2= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Bills").child(year).child(month).child(date);
                FirebaseRecyclerOptions<Tracker> option2 =
                        new FirebaseRecyclerOptions.Builder<Tracker>()
                                .setQuery(query2,Tracker.class)
                                .setLifecycleOwner(Base.this)
                                .build();
                adapter3=new Adapter3(option2);
                adapter3.startListening();
                rec1.setAdapter(adapter3);
                rec1.setLayoutManager(new LinearLayoutManager(Base.this, LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });
        rec=findViewById(R.id.rec);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date= String.valueOf(i2);
                year= String.valueOf(i);
                month= String.valueOf(i1+1);
                Log.e("sa",date+month+year);
                Query query= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Expense").child(year).child(month).child(date);
                FirebaseRecyclerOptions<Tracker> option =
                        new FirebaseRecyclerOptions.Builder<Tracker>()
                                .setQuery(query,Tracker.class)
                                .setLifecycleOwner(Base.this)
                                .build();
                adepter=new Adapter4(option);
                adepter.startListening();
                rec.setAdapter(adepter);
                rec.setLayoutManager(new LinearLayoutManager(Base.this));
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Base.this,DailyData.class);
                intent.putExtra("Date",date);
                intent.putExtra("Year",year);
                intent.putExtra("Month",month);
                startActivity(intent);
            }
        });
        ViewFlipper viewFlipper=findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(5000);
        t2=findViewById(R.id.imageView2);
        t3=findViewById(R.id.imageView1);
        t1=findViewById(R.id.imageView3);
        DatabaseReference mb5= FirebaseDatabase.getInstance().getReference().child("Offers").child("1").child("Image");
        mb5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                i1=snapshot.getValue().toString();
                Log.e("sad",i1);
                Picasso.get().load(i1).into(t1);
                arrayList.add(i1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb6= FirebaseDatabase.getInstance().getReference().child("Offers").child("2").child("Image");
        mb6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                i2=snapshot.getValue().toString();
                arrayList.add(i2);
                Picasso.get().load(i2).into(t2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference mb7= FirebaseDatabase.getInstance().getReference().child("Offers").child("3").child("Image");
        mb7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                i3=snapshot.getValue().toString();
                arrayList.add(i3);
                Picasso.get().load(i3).into(t3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        exp=findViewById(R.id.exp_img);
        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Base.this,Scanner.class);
                startActivity(i);
            }
        });
        //News Layout Coding

        Query query6= FirebaseDatabase.getInstance().getReference().child("News");
        FirebaseRecyclerOptions<NewsData> option6 =
                new FirebaseRecyclerOptions.Builder<NewsData>()
                        .setQuery(query6,NewsData.class)
                        .setLifecycleOwner(this)
                        .build();
        adepter5=new Adapter5(option6);
        adepter5.startListening();
        viewPager2=findViewById(R.id.newspagger);
        viewPager2.setAdapter(adepter5);
        //Coding For Basic Layout
        bottomNavigation=findViewById(R.id.bottomnavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_amp_stories_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_person_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_monetization_on_24));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                switch (item.getId()){
                    case 1:
                        base.setVisibility(View.GONE);
                        news_lay.setVisibility(View.VISIBLE);
                        inv.setVisibility(View.GONE);
                        break;
                    case 2:
                        base.setVisibility(View.VISIBLE);
                        news_lay.setVisibility(View.GONE);
                        inv.setVisibility(View.GONE);
                        break;
                    case 3:
                        base.setVisibility(View.GONE);
                        news_lay.setVisibility(View.GONE);
                        inv.setVisibility(View.VISIBLE);
                        break;
                }

            }
        });
        bottomNavigation.show(1,true);
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
    }

    private void listStock(String stock) {
        Query query= FirebaseDatabase.getInstance().getReference().child("Companies").orderByChild("Symbol").startAt(stock).endAt(stock);
        FirebaseRecyclerOptions<Stock> option =
                new FirebaseRecyclerOptions.Builder<Stock>()
                        .setQuery(query,Stock.class)
                        .setLifecycleOwner(this)
                        .build();
        rec_inv=findViewById(R.id.rec_inv);
        adapter=new Adapter(option);
        adapter.startListening();
        rec_inv.setAdapter(adapter);
        rec_inv.setLayoutManager(new LinearLayoutManager(this));
    }

    public void refresh_data(int millisecond){
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Setcontent();
            }
        };
        handler.postDelayed(runnable,millisecond);
    }
    public void Setcontent(){
        OkHttpClient client = new OkHttpClient();
        String status="s";
        okhttp3.Request request1 = new okhttp3.Request.Builder()
                .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols=^NSEBANK")
                .get()
                .build();
        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                String myresponse = response.body().string();


                try {
                    JSONObject songObject = new JSONObject(myresponse);
                    JSONObject obj=songObject.getJSONObject("quoteResponse");

                    JSONArray jsonArray =obj.getJSONArray("result");
                    for (int i=0; i<jsonArray.length(); ++i) {
                        JSONObject itemObj = jsonArray.getJSONObject(i);
                        String name = itemObj.getString("regularMarketPrice");
                        String up_lev=itemObj.getString("regularMarketChangePercent");
                        Float change=Float.parseFloat(up_lev);
                        String ch=String.format("%.2f",change);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                nift.setText(name);
                                if(change>0){
                                    niftc.setTextColor(Color.rgb(1,135,60));
                                }
                                else {
                                    niftc.setTextColor(Color.rgb(236,76,61));
                                }
                                niftc.setText(ch+"%");

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        OkHttpClient client1 = new OkHttpClient();
        String status1="s";
        okhttp3.Request request11 = new okhttp3.Request.Builder()
                .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols=^NSEI")
                .get()
                .build();
        client1.newCall(request11).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                String myresponse = response.body().string();


                try {
                    JSONObject songObject = new JSONObject(myresponse);
                    JSONObject obj=songObject.getJSONObject("quoteResponse");

                    JSONArray jsonArray =obj.getJSONArray("result");
                    for (int i=0; i<jsonArray.length(); ++i) {
                        JSONObject itemObj = jsonArray.getJSONObject(i);
                        String name = itemObj.getString("regularMarketPrice");
                        String up_lev=itemObj.getString("regularMarketChangePercent");
                        Float change=Float.parseFloat(up_lev);
                        String ch=String.format("%.2f",change);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                niff.setText(name);
                                if(change>0){
                                    niffc.setTextColor(Color.rgb(1,135,60));
                                }
                                else {
                                    niffc.setTextColor(Color.rgb(236,76,61));
                                }
                                niffc.setText(ch+"%");

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        OkHttpClient client12 = new OkHttpClient();
        String status12="s";
        okhttp3.Request request112 = new okhttp3.Request.Builder()
                .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols=^BSESN")
                .get()
                .build();
        client12.newCall(request112).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                String myresponse = response.body().string();


                try {
                    JSONObject songObject = new JSONObject(myresponse);
                    JSONObject obj=songObject.getJSONObject("quoteResponse");

                    JSONArray jsonArray =obj.getJSONArray("result");
                    for (int i=0; i<jsonArray.length(); ++i) {
                        JSONObject itemObj = jsonArray.getJSONObject(i);
                        String name = itemObj.getString("regularMarketPrice");
                        String up_lev=itemObj.getString("regularMarketChangePercent");
                        Float change=Float.parseFloat(up_lev);
                        String ch=String.format("%.2f",change);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sensex.setText(name);
                                if(change>0){
                                    sensexc.setTextColor(Color.rgb(1,135,60));
                                }
                                else {
                                    sensexc.setTextColor(Color.rgb(236,76,61));
                                }
                                sensexc.setText(ch+"%");

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        OkHttpClient client123 = new OkHttpClient();
        String status123="s";
        okhttp3.Request request1123 = new okhttp3.Request.Builder()
                .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols=^CNXIT")
                .get()
                .build();
        client123.newCall(request1123).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                String myresponse = response.body().string();


                try {
                    JSONObject songObject = new JSONObject(myresponse);
                    JSONObject obj=songObject.getJSONObject("quoteResponse");

                    JSONArray jsonArray =obj.getJSONArray("result");
                    for (int i=0; i<jsonArray.length(); ++i) {
                        JSONObject itemObj = jsonArray.getJSONObject(i);
                        String name = itemObj.getString("regularMarketPrice");
                        String up_lev=itemObj.getString("regularMarketChangePercent");

                        Float change=Float.parseFloat(up_lev);
                        String ch=String.format("%.2f",change);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                gold.setText(name);
                                if(change>0){
                                    goldc.setTextColor(Color.rgb(1,135,60));
                                }
                                else {
                                    goldc.setTextColor(Color.rgb(236,76,61));
                                }
                                goldc.setText(ch+"%");

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        refresh_data(500);
    }
    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigation=findViewById(R.id.bottomnavigation);

        Query query= FirebaseDatabase.getInstance().getReference().child("Companies");
        FirebaseRecyclerOptions<Stock> option =
                new FirebaseRecyclerOptions.Builder<Stock>()
                        .setQuery(query,Stock.class)
                        .setLifecycleOwner(this)
                        .build();
        rec_inv=findViewById(R.id.rec_inv);
        adapter=new Adapter(option);
        adapter.startListening();
        rec.setAdapter(adapter);
        rec.setLayoutManager(new LinearLayoutManager(this));
        //Base
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
        Query query2= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Expense").child(year).child(month).child(date);
        FirebaseRecyclerOptions<Tracker> option2 =
                new FirebaseRecyclerOptions.Builder<Tracker>()
                        .setQuery(query2,Tracker.class)
                        .setLifecycleOwner(Base.this)
                        .build();
        adepter=new Adapter4(option2);
        adepter.startListening();
        rec.setAdapter(adepter);
        rec.setLayoutManager(new LinearLayoutManager(Base.this));
        Query query3= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Expense").child(year).child(month).child(date);
        FirebaseRecyclerOptions<Tracker> option3 =
                new FirebaseRecyclerOptions.Builder<Tracker>()
                        .setQuery(query3,Tracker.class)
                        .setLifecycleOwner(Base.this)
                        .build();
        adapter2=new Adapter2(option3);
        adapter2.startListening();
        Query query4= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Bills").child(year).child(month).child(date);
        FirebaseRecyclerOptions<Tracker> option4 =
                new FirebaseRecyclerOptions.Builder<Tracker>()
                        .setQuery(query4,Tracker.class)
                        .setLifecycleOwner(Base.this)
                        .build();
        adapter3=new Adapter3(option4);
        adapter3.startListening();
        rec1.setAdapter(adapter3);
        rec1.setLayoutManager(new LinearLayoutManager(Base.this));
        Query query6= FirebaseDatabase.getInstance().getReference().child("News");
        FirebaseRecyclerOptions<NewsData> option6 =
                new FirebaseRecyclerOptions.Builder<NewsData>()
                        .setQuery(query6,NewsData.class)
                        .setLifecycleOwner(this)
                        .build();
        adepter5=new Adapter5(option6);
        adepter5.startListening();
        viewPager2=findViewById(R.id.newspagger);
        viewPager2.setAdapter(adepter5);
    }

    public void invs(View view) {
        Intent i=new Intent(Base.this,Trade.class);
        startActivity(i);
    }

    public class Adapter extends FirebaseRecyclerAdapter<Stock, Adapter.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        private final int limit = 10;
        public Adapter(@NonNull FirebaseRecyclerOptions<Stock> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull  Adapter.viewholder viewholder, int i, @NonNull Stock tracker) {
            viewholder.name.setText(tracker.Name);
            viewholder.symbol.setText(tracker.Symbol);
            final Handler handler=new Handler();
            String price,up;
            String name,up_lev,up_percent;
            OkHttpClient client = new OkHttpClient();
            String status="s";
            okhttp3.Request request1 = new okhttp3.Request.Builder()
                    .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols="+tracker.Symbol+".NS")
                    .get()
                    .build();
            client.newCall(request1).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                    String myresponse = response.body().string();


                    try {
                        JSONObject songObject = new JSONObject(myresponse);
                        JSONObject obj=songObject.getJSONObject("quoteResponse");

                        JSONArray jsonArray =obj.getJSONArray("result");
                        for (int i=0; i<jsonArray.length(); ++i) {
                            JSONObject itemObj = jsonArray.getJSONObject(i);
                            String name = itemObj.getString("regularMarketPrice");
                            pp=name;
                            String up_lev=itemObj.getString("regularMarketChange");
                            name11  =itemObj.getString("longName");
                            String regularMarketChangePercent=itemObj.getString("regularMarketChangePercent");
                            pc=regularMarketChangePercent;

                            Float change=Float.parseFloat(up_lev);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    viewholder.price.setText(name);
                                    if(change>0){
                                        viewholder.arrow.setImageResource(R.drawable.arrowup);
                                        viewholder.up.setTextColor(Color.rgb(1,135,60));
                                    }
                                    else {
                                        viewholder.arrow.setImageResource(R.drawable.arrowdown);
                                        viewholder.up.setTextColor(Color.rgb(236,76,61));
                                    }
                                    viewholder.up.setText(up_lev);

                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            final Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    String price,up;
                    String name,up_lev,up_percent;
                    OkHttpClient client = new OkHttpClient();
                    String status="s";
                    okhttp3.Request request1 = new okhttp3.Request.Builder()
                            .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols="+tracker.Symbol+".NS")
                            .get()
                            .build();
                    client.newCall(request1).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                            String myresponse = response.body().string();


                            try {
                                JSONObject songObject = new JSONObject(myresponse);
                                JSONObject obj=songObject.getJSONObject("quoteResponse");

                                JSONArray jsonArray =obj.getJSONArray("result");
                                for (int i=0; i<jsonArray.length(); ++i) {
                                    JSONObject itemObj = jsonArray.getJSONObject(i);
                                    String name = itemObj.getString("regularMarketPrice");
                                    String up_lev=itemObj.getString("regularMarketChange");
                                    Float change=Float.parseFloat(up_lev);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            viewholder.price.setText(name);
                                            if(change>0){
                                                viewholder.arrow.setImageResource(R.drawable.arrowup);
                                                viewholder.up.setTextColor(Color.rgb(1,135,60));
                                            }
                                            else {
                                                viewholder.arrow.setImageResource(R.drawable.arrowdown);
                                                viewholder.up.setTextColor(Color.rgb(236,76,61));
                                            }
                                            viewholder.up.setText(up_lev);

                                        }
                                    });

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            };
            handler.postDelayed(runnable,10000);

            viewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(Base.this,StockView.class);
                    i.putExtra("symbol",tracker.Symbol);
                    startActivity(i);
                }
            });
        }
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.stock, parent, false);
            return new Adapter.viewholder(view);
        }
        @Override
        public int getItemCount() {
            return Math.min(super.getItemCount(), limit);

        }
        public class viewholder extends RecyclerView.ViewHolder{
            TextView name,symbol,price,up;
            ImageView arrow;
            Button button;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name);
                symbol=itemView.findViewById(R.id.symbol);
                price=itemView.findViewById(R.id.price);
                up=itemView.findViewById(R.id.up);
                arrow=itemView.findViewById(R.id.arrow);
                button=itemView.findViewById(R.id.button);
            }
        }
    }
    public class Adapter3 extends FirebaseRecyclerAdapter<Tracker,Adapter3.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter3(@NonNull  FirebaseRecyclerOptions<Tracker> options) {
            super(options);
        }

        @Override
        public int getItemCount() {
            if(super.getItemCount()>0){
                txt1.setVisibility(View.VISIBLE);
                exp.setVisibility(View.GONE);
            }
            else {
                exp.setVisibility(View.GONE);
                txt1.setVisibility(View.GONE);
            }
            return super.getItemCount();
        }


        @Override
        protected void onBindViewHolder(@NonNull  Adapter3.viewholder viewholder, int i, @NonNull Tracker tracker) {
            viewholder.cost.setText(tracker.Cost+" Rs");
            viewholder.details.setText(tracker.Details);
            viewholder.type.setText(tracker.Type);
            Picasso.get().load(tracker.Image).into(viewholder.bill_image);

            viewholder.bill_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(Base.this, com.rajaryan.analytica.ImageView.class);
                    i.putExtra("cost",tracker.Cost+" Rs");
                    i.putExtra("details",tracker.Details);
                    i.putExtra("type",tracker.Type);
                    i.putExtra("image",tracker.Image);
                    startActivity(i);
                }
            });

            String key=getRef(i).getKey();
            viewholder.details.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Dialog mDialog;
                    mDialog=new Dialog(Base.this);
                    mDialog.setContentView(R.layout.delete);
                    mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    mDialog.show();

                    TextView delete=mDialog.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            float ca=Float.parseFloat(tracker.Cost);
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
                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Bills").child(year).child(month).child(date).child(key);
                            ref.removeValue();
                            mDialog.dismiss();
                        }
                    });
                    return false;
                }
            });
        }


        @Override
        public Adapter3.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bill_adapter, parent, false);
            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            TextView details,cost,type;
            ImageView bill_image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                details=itemView.findViewById(R.id.details);
                cost=itemView.findViewById(R.id.cost);
                type=itemView.findViewById(R.id.type);
                bill_image=itemView.findViewById(R.id.bill_image);
            }
        }
    }
    public class Adapter5 extends FirebaseRecyclerAdapter<NewsData,Adapter5.viewholder> {

        public Adapter5(@NonNull FirebaseRecyclerOptions<NewsData> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter5.viewholder viewholder, int i, @NonNull NewsData newsData) {
            viewholder.Tittle.setText(newsData.Title);
            viewholder.News.setText(newsData.News);
            viewholder.Src.setText(newsData.Src);
            Glide.with(Base.this)
                    .load(newsData.Image)
                    .into(viewholder.img);
            viewholder.Opinion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomSheetDialogFragment bottomSheet=new Opinion();
                    Bundle b = new Bundle();
                    b.putString("opinion_id",newsData.Id);
                    bottomSheet.setArguments(b);
                    bottomSheet.show(Base.this.getSupportFragmentManager(), bottomSheet.getTag());

                }
            });
        }

        @NonNull
        @Override
        public Adapter5.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_layout, parent, false);
            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView Tittle,News,Src,Opinion;
            ImageView img;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                Tittle=itemView.findViewById(R.id.tittle);
                News=itemView.findViewById(R.id.news);
                Src=itemView.findViewById(R.id.src);
                Opinion=itemView.findViewById(R.id.opinion_btn);
                img=itemView.findViewById(R.id.image);
            }
        }
    }
    public class Adapter4 extends FirebaseRecyclerAdapter<Tracker,Adapter4.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter4(@NonNull  FirebaseRecyclerOptions<Tracker> options) {
            super(options);
        }

        @Override
        public int getItemCount() {
            if(super.getItemCount()>0){
                txt.setVisibility(View.VISIBLE);
                exp.setVisibility(View.GONE);
            }
            else {
                exp.setVisibility(View.GONE);
                txt.setVisibility(View.GONE);
            }
            return super.getItemCount();
        }


        @Override
        protected void onBindViewHolder(@NonNull  Adapter4.viewholder viewholder, int i, @NonNull Tracker tracker) {
            viewholder.cost.setText(tracker.Cost+" Rs");
            viewholder.details.setText(tracker.Details);
            viewholder.type.setText(tracker.Type);
            String key=getRef(i).getKey();
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
        public Adapter4.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.exp_adepter, parent, false);
            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            TextView details,cost,type;
            ImageView tp;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                details=itemView.findViewById(R.id.details);
                cost=itemView.findViewById(R.id.cost);
                tp=itemView.findViewById(R.id.tp);
                type=itemView.findViewById(R.id.type);
            }
        }
    }
    public class Adapter2 extends FirebaseRecyclerAdapter<Tracker,Adapter2.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter2(@NonNull  FirebaseRecyclerOptions<Tracker> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull  Adapter2.viewholder viewholder, int i, @NonNull Tracker tracker) {
            Glide.with(getApplicationContext()).load(tracker.Image).into(viewholder.images);
        }


        @Override
        public Adapter2.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.offercontainer, parent, false);
            return new viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            ImageView images;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                images = itemView.findViewById(R.id.image);
            }
        }
    }

}