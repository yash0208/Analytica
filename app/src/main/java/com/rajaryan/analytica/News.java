package com.rajaryan.analytica;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link News#newInstance} factory method to
 * create an instance of this fragment.
 */
public class News extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ViewPager2 viewPager2;
    Adapter5 adepter5;
    public News() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment News.
     */
    // TODO: Rename and change types and number of parameters
    public static News newInstance(String param1, String param2) {
        News fragment = new News();
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
        View view=inflater.inflate(R.layout.fragment_news, container, false);
        Query query= FirebaseDatabase.getInstance().getReference().child("News");
        FirebaseRecyclerOptions<NewsData> option =
                new FirebaseRecyclerOptions.Builder<NewsData>()
                        .setQuery(query,NewsData.class)
                        .setLifecycleOwner(this)
                        .build();
        adepter5=new Adapter5(option);
        viewPager2=view.findViewById(R.id.newspagger);
        viewPager2.setAdapter(adepter5);
        return view;

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
            Glide.with(getContext())
                    .load(newsData.Image)
                    .into(viewholder.img);
            viewholder.Opinion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BottomSheetDialogFragment bottomSheet=new Opinion();
                    Bundle b = new Bundle();
                    b.putString("opinion_id",newsData.Id);
                    bottomSheet.setArguments(b);
                    bottomSheet.show(getActivity().getSupportFragmentManager(), bottomSheet.getTag());

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
}