package com.example.crimescene.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimescene.PojoModels.EmergencyModel;
import com.example.crimescene.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class EmergenciesAdapter extends FirestoreRecyclerAdapter<EmergencyModel,EmergenciesAdapter.EmergenciesViewHolder> {
    private Context theContext;
    private EmergenciesAdapter.onEmergencyClickListener listener;


    public EmergenciesAdapter(@NonNull FirestoreRecyclerOptions<EmergencyModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EmergenciesAdapter.EmergenciesViewHolder holder, int position, @NonNull final EmergencyModel model) {
        holder.one.setText(model.getEmail());
        holder.two.setText(model.getLeadOfficerId());
        holder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().getSharedPreferences("s",Context.MODE_PRIVATE).edit().putString("email",model.getEmail()).commit();
            }
        });


    }

    @NonNull
    @Override
    public EmergenciesAdapter.EmergenciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_card,parent,false);
        return new EmergenciesViewHolder(view);
    }

    class EmergenciesViewHolder extends RecyclerView.ViewHolder {

        TextView one,two;
        Button but;


        public EmergenciesViewHolder(@NonNull View v) {
            super(v);
            one= v.findViewById(R.id.textView4);
            two=v.findViewById(R.id.textView5);
            but= v.findViewById(R.id.accept);

            but.getRootView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        listener.onEmergencyClick(getSnapshots().getSnapshot(getAdapterPosition()),getAdapterPosition());
                }
            });






        }

    }
    public interface onEmergencyClickListener {
        void onEmergencyClick(DocumentSnapshot snapshot, int position);
    }
    public void setOnEmergencyClickListener(EmergenciesAdapter.onEmergencyClickListener listener) {
        this.listener = listener;

    }

}