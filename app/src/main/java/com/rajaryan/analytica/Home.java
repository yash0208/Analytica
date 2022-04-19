package com.rajaryan.analytica;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    CalendarView calendar;
    LinearLayout exp;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    String date;
    Adapter3 adapter3;
    String year;
    ImageView bud;
    RecyclerView rec;
    String i1,i2,i3;

    RoundedImageView t1,t2,t3;
    ImageView eco;
    float av_need,av_inv,av_med,av_ent,av_deb;
    Adapter4 adepter;
    public String[] images;
    TextView txt,txt1;
    String month;
    private ArrayList<String> arrayList = new ArrayList<>();
    FloatingActionButton floatingActionButton,fb2;
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;ViewPager2 viewPager2;
    String income;
    RecyclerView rec1;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        calendar=view.findViewById(R.id.calender);
        floatingActionButton=view.findViewById(R.id.floating_action_button);
        CollapsibleCalendar collapsibleCalendar = view.findViewById(R.id.cal22);
        eco=view.findViewById(R.id.data);
        txt1=view.findViewById(R.id.txt1);
        txt=view.findViewById(R.id.txt);
        txt.setVisibility(View.GONE);
        txt1.setVisibility(View.GONE);
        bud=view.findViewById(R.id.buf);
        rec1=view.findViewById(R.id.rec1);
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
        fb2=view.findViewById(R.id.floating_action_button2);
        bud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Budget.class);
                startActivity(i);
            }
        });
        eco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Data.class);
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
                                .setLifecycleOwner(getActivity())
                                .build();
                adepter=new Adapter4(option);
                txt.setVisibility(View.GONE);
                adepter.startListening();
                rec.setAdapter(adepter);
                rec.setLayoutManager(new LinearLayoutManager(getActivity()));
                Query query2= FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Bills").child(year).child(month).child(date);
                FirebaseRecyclerOptions<Tracker> option2 =
                        new FirebaseRecyclerOptions.Builder<Tracker>()
                                .setQuery(query2,Tracker.class)
                                .setLifecycleOwner(getActivity())
                                .build();
                adapter3=new Adapter3(option2);
                adapter3.startListening();
                rec1.setAdapter(adapter3);
                rec1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
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
        rec=view.findViewById(R.id.rec);
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
                                .setLifecycleOwner(getActivity())
                                .build();
                adepter=new Adapter4(option);
                adepter.startListening();
                rec.setAdapter(adepter);
                rec.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),DailyData.class);
                Day day = collapsibleCalendar.getSelectedDay();
                intent.putExtra("Date",date);
                intent.putExtra("Year",year);
                month= String.valueOf(day.getMonth() );
                Log.e("Month",month);
                intent.putExtra("Month",month);
                startActivity(intent);
            }
        });
        ViewFlipper viewFlipper=view.findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(5000);
        t2=view.findViewById(R.id.imageView2);
        t3=view.findViewById(R.id.imageView1);
        t1=view.findViewById(R.id.imageView3);
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
        exp=view.findViewById(R.id.exp_img);
        fb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Scanner.class);
                startActivity(i);
            }
        });
        return view;
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
                    Intent i=new Intent(getActivity(), com.rajaryan.analytica.ImageView.class);
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
                    mDialog=new Dialog(getActivity());
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
                    mDialog=new Dialog(getActivity());
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

}