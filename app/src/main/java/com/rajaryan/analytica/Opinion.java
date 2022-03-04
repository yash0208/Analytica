package com.rajaryan.analytica;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Opinion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Opinion extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rec;
    Adapter adepter;
    EditText text;
    ImageButton send;
    String id;
    public Opinion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Opinion.
     */
    // TODO: Rename and change types and number of parameters
    public static Opinion newInstance(String param1, String param2) {
        Opinion fragment = new Opinion();
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
        View view=inflater.inflate(R.layout.fragment_opinion, container, false);
        rec=view.findViewById(R.id.opinion);
        id=getArguments().getString("opinion_id");
        Query query= FirebaseDatabase.getInstance().getReference().child("News").child(id).child("Opinion");
        FirebaseRecyclerOptions<OpinionData> option =
                new FirebaseRecyclerOptions.Builder<OpinionData>()
                        .setQuery(query,OpinionData.class)
                        .setLifecycleOwner(this)
                        .build();
        adepter=new Adapter(option);
        adepter.startListening();
        rec.setAdapter(adepter);
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        text=view.findViewById(R.id.text);
        send=view.findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(text.getText())){
                    Toast.makeText(getActivity(),"Enter Valid Message",Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put("User", FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
                    String personName = account.getDisplayName();
                    String personGivenName = account.getGivenName();
                    String personFamilyName = account.getFamilyName();
                    String personEmail = account.getEmail();
                    String photo=account.getPhotoUrl().toString();
                    String personId = account.getId();
                    hashMap.put("Name",personName);
                    hashMap.put("Image",photo);
                    hashMap.put("Id",personFamilyName);
                    hashMap.put("Opinion",text.getText().toString());
                    DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("News").child(id).child("Opinion");
                    databaseReference1.push().setValue(hashMap);
                    text.setText("");
                }
            }
        });
        return view;
    }
    public class Adapter extends FirebaseRecyclerAdapter<OpinionData, Adapter.viewholder> {
        /**
         * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
         * {@link FirebaseRecyclerOptions} for configuration options.
         *
         * @param options
         */
        public Adapter(@NonNull FirebaseRecyclerOptions<OpinionData> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull  Adapter.viewholder viewholder, int i, @NonNull OpinionData tracker) {
            viewholder.opinion.setText(tracker.Opinion);
            Glide.with(getContext())
                    .load(tracker.Image)
                    .into(viewholder.image);
            String key=getRef(i).getKey();
            Log.e("sad",key);
            viewholder.user.setText(tracker.Name);
            viewholder.opinion.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(tracker.User.equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())){
                        Dialog mDialog;
                        mDialog=new Dialog(getContext());
                        mDialog.setContentView(R.layout.delete);
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialog.show();
                        TextView delete=mDialog.findViewById(R.id.delete);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("News").child(id).child("Opinion").child(key);
                                    ref.removeValue();
                                    mDialog.dismiss();
                            }
                        });
                    }
                    else {
                        Dialog mDialog;
                        mDialog=new Dialog(getContext());
                        mDialog.setContentView(R.layout.report);
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mDialog.show();
                        TextView delete=mDialog.findViewById(R.id.report);
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("User",FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                                hashMap.put("OpinionId",id);
                                DatabaseReference medicines = FirebaseDatabase.getInstance().getReference("Report");
                                medicines.push().setValue(hashMap);
                                Toast.makeText(getActivity(),"Reported",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    return true;
                }
            });
        }
        @Override
        public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.opinion_layout, parent, false);
            return new Adapter.viewholder(view);
        }

        public class viewholder extends RecyclerView.ViewHolder{
            TextView user,opinion;
            CircleImageView image;
            public viewholder(@NonNull View itemView) {
                super(itemView);
                image=itemView.findViewById(R.id.profile_image);
                user=itemView.findViewById(R.id.user);
                opinion=itemView.findViewById(R.id.opinion);
            }
        }
    }
}