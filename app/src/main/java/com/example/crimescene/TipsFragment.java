package com.example.crimescene;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class TipsFragment extends Fragment  {

    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private CollectionReference bangaloreNorthReference = database.collection("BangaloreNorth");
    private ChatAdapter adapter;
    private RecyclerView recyclerView;
    private EditText newChatEditText;
    private Button postButton;
    private Switch anonymousSwitch;
    Message message1;
    private CircularImageView dp;
            private ImageView replyButton;

   // Uri displayPic = UserInfo.getInstance().getDisplayPicture();
    public TipsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tips_layout,container,false);
        recyclerView = view.findViewById(R.id.chat_recycler);

        newChatEditText = view.findViewById(R.id.new_message_ET);
        postButton = view.findViewById(R.id.post_button);
        anonymousSwitch = view.findViewById(R.id.go_anonymous);
      //  dp = view.findViewById(R.id.dp);
        final UserInfo userInfo = UserInfo.getInstance();


      //  Glide.with(getActivity()).load(userInfo.getDisplayPicture()).into(dp);
        setUpChat();





        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageContent = newChatEditText.getText().toString();
                if (messageContent.equals("")) {
                    Toast.makeText(getActivity(), "Message cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                messageContent = messageContent.trim();
                if (messageContent.equals("")) {
                    newChatEditText.setText("");
                    Toast.makeText(getActivity(), "Enter a Message", Toast.LENGTH_SHORT).show();
                    return;
                }
                newChatEditText.setText("");
                final String fromWhom;

                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
                String time1 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String order = date.concat(time1);
                // boolean anon = false;

                if (anonymousSwitch.isChecked() && !messageContent.isEmpty()) {
                    fromWhom = "Anonymous";
                }else {
                    fromWhom = userInfo.getFullName();

                }
                    if(message1 == null) {


                        Message message = new Message(messageContent, date, time, fromWhom, order,"");
                        CollectionReference reference = FirebaseFirestore.getInstance().collection("BangaloreNorth");
                        reference.add(message).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(getActivity(), "Posted as " + fromWhom, Toast.LENGTH_SHORT).show();
                                newChatEditText.getText().clear();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to post. Check internet connection.", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else{
                        message1.setMessageContent(messageContent);
                        message1.setMessageDate(date);
                        message1.setMessageTime(time);
                        message1.setFrom(fromWhom);
                        message1.setTimestamp(order);
                        CollectionReference reference = FirebaseFirestore.getInstance().collection("BangaloreNorth");
                        reference.add(message1).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(getActivity(), "Posted as " + fromWhom, Toast.LENGTH_SHORT).show();
                                newChatEditText.getText().clear();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Failed to post. Check internet connection.", Toast.LENGTH_SHORT).show();

                            }
                        });
                         message1 = null;
                    }


                }

        });

        adapter.setOnMessageClickListener(new ChatAdapter.onMessageClickListener() {
            @Override
            public void onMessageClick(DocumentSnapshot snapshot, int position) {
              //  Toast.makeText(getActivity(),"position:"+position,Toast.LENGTH_SHORT).show();
                String replyToThisMessage =snapshot.getString("messageContent");
                String replyingTo = snapshot.getString("from");
                String t = snapshot.getString("messageTime");
               message1 = new Message();
                message1.setReplyingTo("Replying to " + replyingTo + " from " + t);
                newChatEditText.setText("Reply:");

//                newChatEditText.setText("Replying to "+replyingTo+ ":\n" + replyToThisMessage + "\n\n" +"Reply:");
               newChatEditText.setSelection(newChatEditText.getText().length());

            }
        });
        return view;
    }

    private void setUpChat(){
       Query query = bangaloreNorthReference.orderBy("timestamp", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Message> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Message>().setQuery(query,Message.class).build();
        adapter = new ChatAdapter(firestoreRecyclerOptions,getContext());
       // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
      //  manager.setReverseLayout(true);

        recyclerView.setLayoutManager(manager);

    //    recyclerView.scrollToPosition(adapter.getItemCount()-1);
        recyclerView.setHasFixedSize(true);


        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
    adapter.stopListening();
    }
}
