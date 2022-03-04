package com.rajaryan.analytica;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import yahoofinance.YahooFinance;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Invest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Invest extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText search;
    String artical_link;
    Button open;
    TextView artical,type;
    String Sym;
    RecyclerView rec;
    ImageView image;
    Adapter adapter;
    public Invest() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Invest.
     */
    // TODO: Rename and change types and number of parameters
    public static Invest newInstance(String param1, String param2) {
        Invest fragment = new Invest();
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
        View view=inflater.inflate(R.layout.fragment_invest, container, false);
        type=view.findViewById(R.id.type);
        artical=view.findViewById(R.id.artical);
        image=view.findViewById(R.id.image);
        open=view.findViewById(R.id.open);
        search=view.findViewById(R.id.search);
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
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),WebLayout.class);
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
        rec=view.findViewById(R.id.rec);
        adapter=new Adapter(option);
        adapter.startListening();
        rec.setAdapter(adapter);
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
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

    @Override
    public void onResume() {
        super.onResume();

    }
}