package com.example.crimescene;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.zip.Inflater;

public class ChatAdapter extends FirestoreRecyclerAdapter<Message, ChatAdapter.ChatViewHolder> {
    private Context theContext;
    private onMessageClickListener listener;
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Message> options, Context theContext) {
        super(options);
        this.theContext = theContext;
    }




    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatViewHolder holder, int position, @NonNull Message model) {
        holder.messageContent.setText(model.getMessageContent());
        holder.messageDate.setText((model.getMessageDate()));
        holder.messageTime.setText((model.getMessageTime()));
        holder.from.setText(model.getFrom()+" :");
        holder.replyingTo.setText(model.getReplyingTo());
        //TipsFragment fragment = new TipsFragment();
        //TipsFragment tipsFragment = new TipsFragment();
       // Uri uri =tipsFragment.getDisplayPic();
        // Glide.with(theContext).load(uri).into(holder.displayPicture);


        // holder.displayPicture.setImageResource(UserInfo.getInstance().getDisplayPicture());





    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.civilian_chatbubble,parent,false);
        return new ChatViewHolder(view);
    }

        class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView messageContent;
        private TextView messageDate;
        private TextView messageTime;
        private TextView replyingTo;
        private ImageView replyButton;
     //   private CircularImageView displayPicture;
        private TextView from;


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContent = itemView.findViewById(R.id.message_content);
            messageDate = itemView.findViewById(R.id.message_date);
            messageTime = itemView.findViewById(R.id.message_time);
            replyingTo = itemView.findViewById(R.id.invis);
            replyButton = itemView.findViewById(R.id.reply_button);
           // displayPicture = itemView.findViewById(R.id.dp);
            from = itemView.findViewById(R.id.from_TV);

            replyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onMessageClick(getSnapshots().getSnapshot(position),position);

                    }

                }
            });


        }
    }
    public interface  onMessageClickListener {
        void onMessageClick(DocumentSnapshot snapshot,int position);
    }
    public void setOnMessageClickListener(onMessageClickListener listener) {
        this.listener = listener;

    }

}

