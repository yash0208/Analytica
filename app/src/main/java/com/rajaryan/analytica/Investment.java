package com.rajaryan.analytica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.internal.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class Investment extends AppCompatActivity {
    EditText amount, note, name, upivirtualid;
    Button send;
    RoundedImageView image;

    TextView cap,value,pe,fpe,tpe,dhigh,dlow,drange,vol,tvm,r52,l52,h52,a50,c50,a200,c200,espf,esp12,espy,espp;
    String cap2,value2,pe2,fpe2,tpe2,dhigh2,dlow2,drange2,vol2,tvm2,r522,l522,h522,a502,c502,a2002,c2002,espf2,esp122,espy2,espp2;
    String change,low_lev,high_lev,open,close,pe22,cap22;
    String name11;
    String TAG ="main";
    TextView name_txt,symb,up,up_perc,high,low,price,open1,close1,pe1,cap1;
    String link;
    Adapter5 adapter2;
    String price1;
    float av_need;
    String symbol;
    RecyclerView rec;
    final int UPI_PAYMENT = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
        amount = (EditText)findViewById(R.id.amount);
        note = (EditText)findViewById(R.id.note);
        name = (EditText) findViewById(R.id.name1);
        upivirtualid =(EditText) findViewById(R.id.upi);
        Intent i=getIntent();
        name_txt=findViewById(R.id.name);
        symb=findViewById(R.id.symb);
        up=findViewById(R.id.up);
        image=findViewById(R.id.image);
        up_perc=findViewById(R.id.up_percentage);
        high=findViewById(R.id.high);
        low=findViewById(R.id.low);
        price=findViewById(R.id.price);
        symbol=i.getStringExtra("symbol");
        Picasso.get().load("https://finnhub.io/api/logo?symbol="+symbol+".NS").into(image);
        Toast.makeText(Investment.this,symbol,Toast.LENGTH_SHORT).show();

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
                            espy2=itemObj.getString("epsCurrentYear");
                            espp2=itemObj.getString("priceEpsCurrentYear");
                            Float cha=Float.parseFloat(change);
                            price1 = itemObj.getString("regularMarketPrice");
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
                                    name_txt.setText(name11);
                                    symb.setText(symbol);
                                    price.setText(price1+" INR");
                                    up.setText(change);
                                    up_perc.setText(regularMarketChangePercent+"%");
                                    high.setText(high_lev);
                                    low.setText(low_lev);

                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
            rec=findViewById(R.id.rec);
        Query query3= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Stocks").child(symbol);;
        FirebaseRecyclerOptions<Trade_Data> option3 =
                new FirebaseRecyclerOptions.Builder<Trade_Data>()
                        .setQuery(query3,Trade_Data.class)
                        .setLifecycleOwner(Investment.this)
                        .build();
        adapter2=new Adapter5(option3);
        adapter2.startListening();
        rec.setAdapter(adapter2);
        rec.setLayoutManager(new LinearLayoutManager(this));
        adapter2.startListening();
        }

    public class Adapter5 extends FirebaseRecyclerAdapter<Trade_Data, Adapter5.viewholder> {

        public Adapter5(@NonNull FirebaseRecyclerOptions<Trade_Data> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull Adapter5.viewholder viewholder, int i, @NonNull Trade_Data newsData) {
            viewholder.details.setText(newsData.Symbol);
            viewholder.buy_price.setText("Buy Price: "+newsData.Buy_Value);
            viewholder.buy_value.setText("Buy Amount : "+newsData.Amount);
            viewholder.quantity.setText("Total Share : "+newsData.Shares);
            viewholder.cost.setText("Buy Date\n"+newsData.BuyDate);
            float shares=Float.parseFloat(viewholder.quantity.getText().toString());
            Log.e("shares",String.valueOf(shares));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewholder.details.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Dialog mDialog;
                            mDialog = new Dialog(getApplicationContext());
                            mDialog.setContentView(R.layout.sell);
                            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            mDialog.show();
                            TextView delete = mDialog.findViewById(R.id.report);
                        }
                    });
                }
            });

        }

        @NonNull
        @Override
        public Adapter5.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.available, parent, false);
            return new Adapter5.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder {
            TextView details,buy_value,buy_price,quantity,cost,date;
            ImageView img;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                details=itemView.findViewById(R.id.details);
                buy_price=itemView.findViewById(R.id.buy_price);
                buy_value=itemView.findViewById(R.id.buy_value);
                quantity=itemView.findViewById(R.id.quantity);
                cost=itemView.findViewById(R.id.cost);
                date=itemView.findViewById(R.id.date);
                img=itemView.findViewById(R.id.img);
            }
        }
    }

    public void back(View view) {
        onBackPressed();
    }


    public void pay(View view) {
        if (TextUtils.isEmpty(name.getText().toString().trim())){
            Toast.makeText(Investment.this," Name is invalid", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(note.getText().toString().trim())){
            Toast.makeText(Investment.this," Note is invalid", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(amount.getText().toString().trim())){
            Toast.makeText(Investment.this," Amount is invalid", Toast.LENGTH_SHORT).show();
        }else{
            payUsingUpi(name.getText().toString(), "7990584385@ybl",
                    note.getText().toString(), amount.getText().toString());
        }
    }
    void payUsingUpi(  String name,String upiId, String note, String amount) {
        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(Investment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(Investment.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(Investment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                float amount1=Float.parseFloat(amount.getText().toString().trim());
                float shares=amount1/Float.parseFloat(price1);
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("Symbol",symbol);
                hashMap.put("Amount",amount.getText().toString().trim());
                hashMap.put("Buy_Value",price1);
                hashMap.put("Shares",String.valueOf(shares));
                hashMap.put("Current_Price",price1);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = simpleDateFormat.format(c);
                String year=formattedDate.substring(0,4);
                String month=formattedDate.substring(5,6);
                int m=Integer.parseInt(month)+1;
                month=String.valueOf(m);
                String date=formattedDate.substring(8,10);
                Log.i(getClass().getName(), "Selected Day: "
                        + year + "/" + (month + 1) + "/" + date);
                hashMap.put("BuyDate",year + "/" + (month + 1) + "/" + date);
                DatabaseReference medicines = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Stocks").child(symbol);
                medicines.push().setValue(hashMap);

                DatabaseReference medicines1 = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Stock_Value").child(symbol).child("Value");
                medicines1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            av_need=Float.parseFloat(snapshot.getValue().toString());
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                av_need=av_need+amount1;
                DatabaseReference medicines5 = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Stock_Value").child(symbol).child("Value");
                medicines5.setValue(av_need);
                Log.e("UPI", "payment successfull: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(Investment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);
            }
            else {
                Toast.makeText(Investment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(Investment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(Investment context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}