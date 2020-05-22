package com.example.crimescene.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimescene.PojoModels.Case;
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
    protected void onBindViewHolder(@NonNull EmergenciesAdapter.EmergenciesViewHolder holder, int position, @NonNull EmergencyModel model) {










    }

    @NonNull
    @Override
    public EmergenciesAdapter.EmergenciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_file,parent,false);
        return new EmergenciesViewHolder(view);
    }

    class EmergenciesViewHolder extends RecyclerView.ViewHolder {


        public EmergenciesViewHolder(@NonNull View itemView) {
            super(itemView);






        }

    }
    public interface  onEmergencyClickListener {
        void onEmergencyClick(DocumentSnapshot snapshot, int position);
    }
    public void setOnEmergencyClickListener(EmergenciesAdapter.onEmergencyClickListener listener) {
        this.listener = listener;

    }

}