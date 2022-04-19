package com.rajaryan.analytica;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Investing#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Investing extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MeowBottomNavigation bottomNavigation;
    Adapter adapter;
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
    
    String date;

    Base.Adapter4 adepter;
    String year;
    android.widget.ImageView bud;
    RecyclerView rec;
    RecyclerView rec_inv;
    String i1,i2,i3;
    RoundedImageView t1,t2,t3;
    android.widget.ImageView eco;
    float av_need,av_inv,av_med,av_ent,av_deb;

    public String[] images;
    TextView txt,txt1;
    String month;
    private ArrayList<String> arrayList = new ArrayList<>();
    FloatingActionButton floatingActionButton,fb2;
    
    Base.Adapter2 adapter2;
    // TODO: Rename and change types of parameters
    
    ViewPager2 viewPager2;
    String income;
    RecyclerView rec1;
    FrameLayout news_lay;
    Base.Adapter3 adapter3;
    String pp,pc;
    String price,change1;
    TextView artical,type;
    FrameLayout base;
    String Sym;
    RecyclerView rec_base;
    ImageView image;
    
    ViewPager2 viewPager3;
    EditText search_stock;
    Base.Adapter5 adepter5;
    String status="2";
    public Investing() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Investing.
     */
    // TODO: Rename and change types and number of parameters
    public static Investing newInstance(String param1, String param2) {
        Investing fragment = new Investing();
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
        View view=inflater.inflate(R.layout.fragment_investing, container, false);
        type=view.findViewById(R.id.type);
        sensex=view.findViewById(R.id.sensex);
        sensexc=view.findViewById(R.id.sensexc);
        nift=view.findViewById(R.id.nift);//50
        niffc=view.findViewById(R.id.niftc);//50
        niff=view.findViewById(R.id.nifb);
        niftc=view.findViewById(R.id.niftb);
        gold=view.findViewById(R.id.gold);
        goldc=view.findViewById(R.id.goldc);
        artical=view.findViewById(R.id.artical);
        image=view.findViewById(R.id.image);
        open1=view.findViewById(R.id.open);
        search=view.findViewById(R.id.search);
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
                Intent i=new Intent( getActivity(),WebLayout.class);
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
        rec_inv=view.findViewById(R.id.rec_inv);
        adapter=new Adapter(option);
        adapter.startListening();
        rec_inv.setAdapter(adapter);
        rec_inv.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    private void listStock(String stock) {
        Query query= FirebaseDatabase.getInstance().getReference().child("Companies").orderByChild("Symbol").startAt(stock).endAt(stock);
        FirebaseRecyclerOptions<Stock> option =
                new FirebaseRecyclerOptions.Builder<Stock>()
                        .setQuery(query,Stock.class)
                        .setLifecycleOwner(this)
                        .build();

        adapter=new Adapter(option);
        adapter.startListening();
        rec_inv.setAdapter(adapter);
        rec_inv.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                        getActivity().runOnUiThread(new Runnable() {
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
                         getActivity().runOnUiThread(new Runnable() {
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
                         getActivity().runOnUiThread(new Runnable() {
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
                         getActivity().runOnUiThread(new Runnable() {
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
                             getActivity().runOnUiThread(new Runnable() {
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
                                     getActivity().runOnUiThread(new Runnable() {
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
                    Intent i=new Intent( getActivity(),StockView.class);
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

}