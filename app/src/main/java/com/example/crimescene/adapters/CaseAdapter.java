package com.example.crimescene.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crimescene.Case;
import com.example.crimescene.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class CaseAdapter extends FirestoreRecyclerAdapter <Case,CaseAdapter.CaseViewHolder> {
    private Context theContext;
    private CaseAdapter.onCaseClickListener listener;




    public CaseAdapter(@NonNull FirestoreRecyclerOptions<Case> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CaseAdapter.CaseViewHolder holder, int position, @NonNull Case model) {
       holder.caseName.setText(model.getCaseName());


       if(model.getCaseType() == 1) {

           holder.layout2.setBackgroundColor(Color.parseColor("#ff6961"));
       }
        else if(model.getCaseType() == 2) {

            holder.layout2.setBackgroundColor(Color.parseColor("#77dd77"));
        }
       else if(model.getCaseType() == 3) {

           holder.layout2.setBackgroundColor(Color.parseColor("#EBEB75"));
       }
       else {

           holder.layout2.setBackgroundColor(Color.parseColor("#ffd1dc"));
       }









    }

    @NonNull
    @Override
    public CaseAdapter.CaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_file,parent,false);
        return new CaseViewHolder(view);
    }

    class CaseViewHolder extends RecyclerView.ViewHolder {
       private TextView caseName,caseNotes;
       private LinearLayout layout1,layout2,mainFileLL;
       private int caseType;

        public CaseViewHolder(@NonNull View itemView) {
            super(itemView);
           caseName = itemView.findViewById(R.id.case_name);
           layout1 = itemView.findViewById(R.id.case1);
           layout2 = itemView.findViewById(R.id.case2);
           mainFileLL = itemView.findViewById(R.id.main_file_ll);

           mainFileLL.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int position = getAdapterPosition();
                   if(position != RecyclerView.NO_POSITION && listener != null) {
                       listener.onCaseClick(getSnapshots().getSnapshot(position), position);
                   }
               }
           });




        }

    }
    public interface  onCaseClickListener {
        void onCaseClick(DocumentSnapshot snapshot, int position);
    }
    public void setOnCaseClickListener(CaseAdapter.onCaseClickListener listener) {
        this.listener = listener;

    }

}
