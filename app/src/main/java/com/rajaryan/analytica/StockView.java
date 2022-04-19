package com.rajaryan.analytica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class StockView extends AppCompatActivity {
    WebView webView,web1;
    String link;
    String symbol;
    TextView name,symb,up,up_perc,high,low,price,open1,close1,pe1,cap1,name_pred,symbol_pred,pred_price;
    TabLayout tabLayout;
    ViewPager2 pager2;
    ImageView arrow,arrow2;
    FrameLayout news;
    LinearLayout prediction;
    LinearLayout data,stats;
    ImageView image_pred;
    String name11;
    ViewPager2 viewPager2;
    RoundedImageView image;
    String pp,pc;
    ChipNavigationBar chipNavigationBar;
    TextView cap,value,pe,fpe,tpe,dhigh,dlow,drange,vol,tvm,r52,l52,h52,a50,c50,a200,c200,espf,esp12,espy,espp;
    String cap2,value2,pe2,fpe2,tpe2,dhigh2,dlow2,drange2,vol2,tvm2,r522,l522,h522,a502,c502,a2002,c2002,espf2,esp122,espy2,espp2;
    String change,low_lev,high_lev,open,close,pe22,cap22;
    Adapter5 adapter5;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_view);
        chipNavigationBar = findViewById(R.id.menu);
        cap     =findViewById(R.id.cap);
        value   =findViewById(R.id.value);
        pe      =findViewById(R.id.pe);
        fpe     =findViewById(R.id.fpe);
        tpe     =findViewById(R.id.tpe);
        dhigh   =findViewById(R.id.dhigh);
        dlow    =findViewById(R.id.dlow);
        drange  =findViewById(R.id.drange);
        vol     =findViewById(R.id.vol);
        tvm     =findViewById(R.id.tvm);//3 mon avg
        r52     =findViewById(R.id.r52);
        l52     =findViewById(R.id.l52);
        h52     =findViewById(R.id.h52);
        a50     =findViewById(R.id.a50);
        c50     =findViewById(R.id.c50);
        a200    =findViewById(R.id.a200);
        c200    =findViewById(R.id.c200);
        espf    =findViewById(R.id.espf);
        esp12   =findViewById(R.id.esp12);
        espy    =findViewById(R.id.espy);
        espy.setVisibility(View.GONE);
        espp    =findViewById(R.id.espp);
        espp.setVisibility(View.GONE);
        web1=findViewById(R.id.web1);
        name_pred=findViewById(R.id.name_pred);
        arrow=findViewById(R.id.arrow);
        image_pred=findViewById(R.id.image_pred);
        arrow2=findViewById(R.id.arrow2);
        pred_price=findViewById(R.id.cap4);
        prediction=findViewById(R.id.prediction);

        symbol_pred=findViewById(R.id.symb_pred);
        chipNavigationBar.setItemSelected(R.id.home,
                true);
        bottomMenu();
        stats=findViewById(R.id.stats);
        stats.setVisibility(View.GONE);
        Intent i=getIntent();
        name=findViewById(R.id.name);
        symb=findViewById(R.id.symb);
        up=findViewById(R.id.up);
        image=findViewById(R.id.image);
        up_perc=findViewById(R.id.up_percentage);
        high=findViewById(R.id.high);
        low=findViewById(R.id.low);
        price=findViewById(R.id.price);
        symbol=i.getStringExtra("symbol");
        Toast.makeText(StockView.this,symbol,Toast.LENGTH_SHORT).show();
        Picasso.get().load("https://finnhub.io/api/logo?symbol="+symbol+".NS").into(image);
        Picasso.get().load("https://finnhub.io/api/logo?symbol="+symbol+".NS").into(image_pred);
        Query query12=FirebaseDatabase.getInstance().getReference("Companies").orderByChild("Symbol").equalTo(symbol);
        query12.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        String name1=""+ds.child("Predicted_Price").getValue().toString();
                        String newsp=""+ds.child("News_Prediction").getValue().toString();
                        String tweetp=""+ds.child("Tweet_Prediction").getValue().toString();
                        if(newsp.equals("0")){
                            arrow.setImageResource(R.drawable.arrowup);
                        }
                        else {
                            arrow.setImageResource(R.drawable.arrowdown);
                        }
                        if(tweetp.equals("0")){
                            arrow2.setImageResource(R.drawable.arrowup);
                        }
                        else {
                            arrow2.setImageResource(R.drawable.arrowdown);
                        }

                        pred_price.setText(name1);
                        Log.e("Pred",name1);
                        break;
                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        data=findViewById(R.id.data);
        news=findViewById(R.id.news);
        open1=findViewById(R.id.open1);

        data.setVisibility(View.VISIBLE);
        cap1=findViewById(R.id.cap1);
        close1=findViewById(R.id.close1);
        pe1=findViewById(R.id.pe1);
        webView = (WebView) findViewById(R.id.web);
        setContent();
        Query query= FirebaseDatabase.getInstance().getReference().child("Companies_News").child(symbol);
        FirebaseRecyclerOptions<NewsData> option =
                new FirebaseRecyclerOptions.Builder<NewsData>()
                        .setQuery(query,NewsData.class)
                        .setLifecycleOwner(this)
                        .build();
        adapter5=new Adapter5(option);
        viewPager2=findViewById(R.id.newspagger);
        viewPager2.setAdapter(adapter5);
        link="https://finance.yahoo.com/chart/"+symbol+".NS";
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Toast.makeText(getApplicationContext(),link,Toast.LENGTH_LONG).show();
        webView.loadUrl(link);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSaveFormData(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setSupportZoom(false);
        webView.setClickable(true);
        // Use remote resource
        webView.postDelayed(new Runnable() {

            @Override
            public void run() {
                webView.loadUrl(link);
            }
        }, 500);
        web1.loadUrl(link);
        web1.setWebChromeClient(new WebChromeClient());
        web1.getSettings().setJavaScriptEnabled(true);
        web1.getSettings().setDomStorageEnabled(true);
        web1.setWebViewClient(new WebViewClient());
        web1.getSettings().setSaveFormData(true);
        web1.getSettings().setAllowContentAccess(true);
        web1.getSettings().setAllowFileAccess(true);
        web1.getSettings().setAllowFileAccessFromFileURLs(true);
        web1.getSettings().setAllowUniversalAccessFromFileURLs(true);
        web1.getSettings().setSupportZoom(false);
        web1.setClickable(true);
        // Use remote resource
        web1.postDelayed(new Runnable() {

            @Override
            public void run() {
                web1.loadUrl(link);
            }
        }, 500);
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String status="s";
                okhttp3.Request request1 = new okhttp3.Request.Builder()
                        .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols="+symbol+".NS")
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
                                String name1 = itemObj.getString("longName");
                                name11=itemObj.getString("longName");

                                String change=itemObj.getString("regularMarketChange");
                                String low_lev=itemObj.getString("regularMarketDayLow");
                                String high_lev=itemObj.getString("regularMarketDayHigh");
                                String open=itemObj.getString("regularMarketOpen");
                                String close=itemObj.getString("regularMarketPreviousClose");
                                String pe22=itemObj.getString("forwardPE");
                                String cap22=itemObj.getString("marketCap");
                                cap2=itemObj.getString("marketCap");
                                value2=itemObj.getString("regularMarketPrice");
                                pe2=itemObj.getString("regularMarketChange");
                                fpe2=itemObj.getString("forwardPE");
                                tpe2=itemObj.getString("trailingPE");
                                dhigh2=itemObj.getString("regularMarketDayHigh");
                                dlow2=itemObj.getString("regularMarketDayLow");
                                drange2=itemObj.getString("regularMarketDayRange");
                                vol2=itemObj.getString("regularMarketVolume");
                                tvm2=itemObj.getString("averageDailyVolume3Month");
                                r522=itemObj.getString("fiftyTwoWeekRange");
                                l522=itemObj.getString("fiftyTwoWeekLowChange");
                                h522=itemObj.getString("fiftyTwoWeekHigh");
                                a502=itemObj.getString("fiftyDayAverage");
                                c502=itemObj.getString("fiftyDayAverageChange");
                                a2002 =itemObj.getString("twoHundredDayAverage");
                                c2002=itemObj.getString("twoHundredDayAverageChange");
                                espf2=itemObj.getString("epsForward");
                                esp122=itemObj.getString("epsTrailingTwelveMonths");


                                Float cha=Float.parseFloat(change);
                                String price1 = itemObj.getString("regularMarketPrice");
                                String regularMarketChangePercent=itemObj.getString("regularMarketChangePercent");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(cha<0){
                                            up.setTextColor(Color.rgb(236,76,61));
                                            up_perc.setTextColor(Color.rgb(236,76,61));
                                        }
                                        else {
                                            up.setTextColor(Color.rgb(0,135,104));
                                            up_perc.setTextColor(Color.rgb(0,135,104));
                                        }
                                        name.setText(name1);
                                        symb.setText(symbol);
                                        name_pred.setText(name1);
                                        symbol_pred.setText(symbol);
                                        price.setText(price1+" INR");
                                        up.setText(change);
                                        up_perc.setText(regularMarketChangePercent+"%");
                                        high.setText(high_lev);
                                        low.setText(low_lev);
                                        open1.setText(open);
                                        close1.setText(close);
                                        pe1.setText(pe22);
                                        cap1.setText(cap22);
                                        cap.setText(cap22);
                                        value.setText(value2);
                                        pe.setText(pe2);
                                        fpe.setText(fpe2);
                                        tpe.setText(tpe2);
                                        dhigh.setText(dhigh2);
                                        dlow.setText(dlow2);
                                        drange.setText(drange2);
                                        vol .setText(vol2);
                                        tvm.setText(tvm2);
                                        r52.setText(r522);
                                        l52.setText(l522);
                                        h52.setText(h522);
                                        a50.setText(a502);
                                        c50.setText(c502);
                                        a200.setText(a2002);
                                        c200.setText(c2002);
                                        espf.setText(espf2);
                                        esp12.setText(esp122);


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
        handler.postDelayed(runnable,500);
        List<NewsModel> new_items = new ArrayList<>();
        OkHttpClient client1 = new OkHttpClient();
        String status1="s";
        okhttp3.Request request = new okhttp3.Request.Builder()
                .header("x-api-key", "JVhaqecPooFf1HBGcMnCPXu-_Y5Wxj4MADmMuyFhqP0")
                .url("https://api.newscatcherapi.com/v2/search?q="+name11+"&lang=en")
                .get()
                .build();
        client1.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                String myresponse = response.body().string();

                try {
                    JSONObject json = new JSONObject(myresponse);
                    JSONArray res= json.getJSONArray("articles");
                    for (int i = 0; i < res.length(); i++) {
                        try {
                            JSONObject songObject = res.getJSONObject(i);
                            NewsModel song = new NewsModel();
                            song.setTitle(songObject .getString("title").toString());
                            song.setId(songObject.getString("_id".toString()));
                            song.setImage(songObject.getString("media"));
                            song.setAuthor(songObject.getString("author"));
                            song.setLink(songObject.getString("link"));
                            song.setSummary(songObject.getString("summary"));
                            song.setTime(songObject.getString("published_date"));

                            new_items.add(song);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Companies_News").child(symbol);
                    myRef.setValue(new_items);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        webView.requestFocus(View.FOCUS_DOWN);

    }
    public void refresh_data(int millisecond){
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                setContent();
            }
        };
        handler.postDelayed(runnable,millisecond);
    }

    private void setContent() {
        OkHttpClient client = new OkHttpClient();
        String status="s";
        okhttp3.Request request1 = new okhttp3.Request.Builder()
                .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols="+symbol+".NS")
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
                Log.e("data",myresponse);

                try {
                    JSONObject songObject = new JSONObject(myresponse);
                    JSONObject obj=songObject.getJSONObject("quoteResponse");

                    JSONArray jsonArray =obj.getJSONArray("result");
                    for (int i=0; i<jsonArray.length(); ++i) {
                        JSONObject itemObj = jsonArray.getJSONObject(i);
                        String name1 = itemObj.getString("longName");
                        name11=itemObj.getString("longName");

                        String change=itemObj.getString("regularMarketChange");
                        String low_lev=itemObj.getString("regularMarketDayLow");
                        String high_lev=itemObj.getString("regularMarketDayHigh");
                        String open=itemObj.getString("regularMarketOpen");
                        String close=itemObj.getString("regularMarketPreviousClose");
                        String pe22=itemObj.getString("forwardPE");
                        String cap22=itemObj.getString("marketCap");
                        cap2=itemObj.getString("marketCap");
                        value2=itemObj.getString("regularMarketPrice");
                        pe2=itemObj.getString("regularMarketChange");
                        fpe2=itemObj.getString("forwardPE");
                        tpe2=itemObj.getString("trailingPE");
                        dhigh2=itemObj.getString("regularMarketDayHigh");
                        dlow2=itemObj.getString("regularMarketDayLow");
                        drange2=itemObj.getString("regularMarketDayRange");
                        vol2=itemObj.getString("regularMarketVolume");
                        tvm2=itemObj.getString("averageDailyVolume3Month");
                        r522=itemObj.getString("fiftyTwoWeekRange");
                        l522=itemObj.getString("fiftyTwoWeekLowChange");
                        h522=itemObj.getString("fiftyTwoWeekHigh");
                        a502=itemObj.getString("fiftyDayAverage");
                        c502=itemObj.getString("fiftyDayAverageChange");
                        a2002 =itemObj.getString("twoHundredDayAverage");
                        c2002=itemObj.getString("twoHundredDayAverageChange");
                        espf2=itemObj.getString("epsForward");
                        esp122=itemObj.getString("epsTrailingTwelveMonths");
                        espy2=itemObj.getString("epsCurrentYear");
                        espp2=itemObj.getString("priceEpsCurrentYear");
                        Float cha=Float.parseFloat(change);
                        String price1 = itemObj.getString("regularMarketPrice");
                        String regularMarketChangePercent=itemObj.getString("regularMarketChangePercent");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(cha<0){
                                    up.setTextColor(Color.rgb(236,76,61));
                                    up_perc.setTextColor(Color.rgb(236,76,61));
                                }
                                else {
                                    up.setTextColor(Color.rgb(0,135,104));
                                    up_perc.setTextColor(Color.rgb(0,135,104));
                                }
                                name.setText(name1);
                                symb.setText(symbol);
                                price.setText(price1+" INR");
                                up.setText(change);
                                up_perc.setText(regularMarketChangePercent+"%");
                                high.setText(high_lev);
                                low.setText(low_lev);
                                open1.setText(open);
                                close1.setText(close);
                                pe1.setText(pe22);
                                cap1.setText(cap22);
                                cap.setText(cap22);
                                value.setText(value2);
                                pe.setText(pe2);
                                fpe.setText(fpe2);
                                tpe.setText(tpe2);
                                dhigh.setText(dhigh2);
                                dlow.setText(dlow2);
                                drange.setText(drange2);
                                vol .setText(vol2);
                                tvm.setText(tvm2);
                                r52.setText(r522);
                                l52.setText(l522);
                                h52.setText(h522);
                                a50.setText(a502);
                                c50.setText(c502);
                                a200.setText(a2002);
                                c200.setText(c2002);
                                espf.setText(espf2);
                                esp12.setText(esp122);
                                espy.setText(espy2);
                                espp.setText(espp2);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
        refresh_data(5000);
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {

                        switch (i){
                            case R.id.home:
                                data.setVisibility(View.VISIBLE);
                                news.setVisibility(View.GONE);
                                prediction.setVisibility(View.GONE);
                                stats.setVisibility(View.GONE);
                                break;
                            case R.id.activity:
                                stats.setVisibility(View.VISIBLE);
                                data.setVisibility(View.GONE);
                                prediction.setVisibility(View.GONE);
                                news.setVisibility(View.GONE);
                                break;
                            case R.id.settings:
                                stats.setVisibility(View.GONE);
                                prediction.setVisibility(View.VISIBLE);
                                data.setVisibility(View.GONE);
                                news.setVisibility(View.GONE);
                                break;
                            case R.id.favorites:
                                stats.setVisibility(View.GONE);
                                data.setVisibility(View.GONE);
                                prediction.setVisibility(View.GONE);
                                news.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
    }

    public void refresh(View view) {
        symbol=getIntent().getStringExtra("symbol");
        Toast.makeText(StockView.this,symbol,Toast.LENGTH_SHORT).show();
        OkHttpClient client = new OkHttpClient();
        String status="s";
        okhttp3.Request request1 = new okhttp3.Request.Builder()
                .url("https://query1.finance.yahoo.com/v7/finance/quote?symbols="+symbol+".NS")
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
                        String name1 = itemObj.getString("longName");
                        name11  =itemObj.getString("longName");

                        change  =itemObj.getString("regularMarketChange");
                        low_lev =itemObj.getString("regularMarketDayLow");
                        high_lev=itemObj.getString("regularMarketDayHigh");
                        open    =itemObj.getString("regularMarketOpen");
                        close   =itemObj.getString("regularMarketPreviousClose");
                        pe22    =itemObj.getString("forwardPE");
                        cap22   =itemObj.getString("marketCap");
                        cap2    =itemObj.getString("marketCap");
                        value2  =itemObj.getString("regularMarketPrice");
                        pe2     =itemObj.getString("regularMarketChange");
                        fpe2    =itemObj.getString("forwardPE");
                        tpe2    =itemObj.getString("trailingPE");
                        dhigh2  =itemObj.getString("regularMarketDayHigh");
                        dlow2   =itemObj.getString("regularMarketDayLow");
                        drange2 =itemObj.getString("regularMarketDayRange");
                        vol2    =itemObj.getString("regularMarketVolume");
                        tvm2    =itemObj.getString("averageDailyVolume3Month");
                        r522    =itemObj.getString("fiftyTwoWeekRange");
                        l522    =itemObj.getString("fiftyTwoWeekLowChange");
                        h522    =itemObj.getString("fiftyTwoWeekHigh");
                        a502    =itemObj.getString("fiftyDayAverage");
                        c502    =itemObj.getString("fiftyDayAverageChange");
                        a2002   =itemObj.getString("twoHundredDayAverage");
                        c2002   =itemObj.getString("twoHundredDayAverageChange");
                        espf2   =itemObj.getString("epsForward");
                        esp122  =itemObj.getString("epsTrailingTwelveMonths");
                        espy2   =itemObj.getString("epsCurrentYear");
                        espp2   =itemObj.getString("priceEpsCurrentYear");
                        Float cha=Float.parseFloat(change);
                        String price1 = itemObj.getString("regularMarketPrice");
                        String regularMarketChangePercent=itemObj.getString("regularMarketChangePercent");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(cha<0){
                                    up.setTextColor(Color.rgb(236,76,61));
                                    up_perc.setTextColor(Color.rgb(236,76,61));
                                }
                                else {
                                    up.setTextColor(Color.rgb(0,135,104));
                                    up_perc.setTextColor(Color.rgb(0,135,104));
                                }
                                name.setText(name1);
                                symb.setText(symbol);
                                price.setText(price1+" INR");
                                float price=Float.parseFloat(price1);
                                up.setText(change);
                                up_perc.setText(regularMarketChangePercent+"%");
                                high.setText(high_lev);
                                low.setText(low_lev);
                                open1.setText(open);
                                close1.setText(close);
                                pe1.setText(pe22);
                                cap1.setText(cap22);
                                cap.setText(cap22);
                                value.setText(value2);
                                pe.setText(pe2);
                                fpe.setText(fpe2);
                                tpe.setText(tpe2);
                                dhigh.setText(dhigh2);
                                dlow.setText(dlow2);
                                drange.setText(drange2);
                                vol .setText(vol2);
                                tvm.setText(tvm2);
                                r52.setText(r522);
                                l52.setText(l522);
                                h52.setText(h522);
                                a50.setText(a502);
                                c50.setText(c502);
                                a200.setText(a2002);
                                c200.setText(c2002);
                                espf.setText(espf2);
                                esp12.setText(esp122);
                                espy.setText(espy2);
                                espp.setText(espp2);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    public void trade(View view) {
        Intent i=new Intent(StockView.this,Investment.class);
        i.putExtra("symbol",symbol);
        startActivity(i);
    }


    public class Adapter5 extends FirebaseRecyclerAdapter<NewsData,Adapter5.viewholder> {

        public Adapter5(@NonNull FirebaseRecyclerOptions<NewsData> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter5.viewholder viewholder, int i, @NonNull NewsData newsData) {
            viewholder.Tittle.setText(newsData.Title);
            viewholder.News.setText(newsData.summary);
            viewholder.Src.setText(newsData.Src);
            Glide.with(getApplicationContext())
                    .load(newsData.Image)
                    .into(viewholder.img);
            viewholder.Opinion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomSheetDialogFragment bottomSheet=new Opinion();
                    Bundle b = new Bundle();
                    b.putString("opinion_id",newsData.Id);
                    bottomSheet.setArguments(b);
                    bottomSheet.show(StockView.this.getSupportFragmentManager(), bottomSheet.getTag());

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
    public void back(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onBackPressed();
        Intent i=new Intent(this,Ground.class);
        startActivity(i);
    }
}