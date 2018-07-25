package hariharan.theroboticlabs.com.wirechat;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import hariharan.theroboticlabs.com.wirechat.Utils.ChatMessage;
import hariharan.theroboticlabs.com.wirechat.Utils.FirebaseUtils;
import hariharan.theroboticlabs.com.wirechat.Viewmodels.ChatViewModel;

public class ChatActivity extends AppCompatActivity {

    private FirebaseUtils firebaseUtils;
    private EditText messageField;
    private RecyclerView chatRecycler;
    private String toUid, toName;
    private ChatViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toUid = getIntent().getStringExtra("TO_UID");
        toName = getIntent().getStringExtra("TO_NAME");

        firebaseUtils = new FirebaseUtils();
        TextView talkingTo = (TextView) findViewById(R.id.talking_to);
        talkingTo.setText(toName);

        messageField = (EditText) findViewById(R.id.message_window);
        chatRecycler = (RecyclerView) findViewById(R.id.chat_recycler);
        chatRecycler.setHasFixedSize(true);
        chatRecycler.setLayoutManager(new LinearLayoutManager(this));
        chatRecycler.setAdapter(new ChatRecycler(new ArrayList<ChatMessage>()));
        chatRecycler.setItemAnimator(new DefaultItemAnimator());

        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        chatViewModel.setChatPair(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                toUid);
        chatViewModel.getMessages().observe(this, new Observer<List<ChatMessage>>() {
            @Override
            public void onChanged(@Nullable List<ChatMessage> chatMessages) {
                ChatRecycler adapter = new ChatRecycler(chatMessages);
                chatRecycler.setAdapter(adapter);
            }
        });

        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageField.getText().toString();
                if(!TextUtils.isEmpty(message))
                    chatViewModel.sendMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            toUid, message);
            }
        });


    }

//    private void sendMessage(String message) {
//        firebaseUtils.sendMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
//                toUid, message);
//    }

    class ChatRecycler extends RecyclerView.Adapter<ChatRecycler.ChatViewHolder>{

        private List<ChatMessage> allMessages;
        public ChatRecycler(List<ChatMessage> messages) {
            this.allMessages = messages;
        }

        @Override
        public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(ChatActivity.this).inflate(R.layout.chat_bubble, parent, false);
            ChatViewHolder viewHolder = new ChatViewHolder(mView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ChatViewHolder holder, int position) {
            holder.message = allMessages.get(position).getMessage();
            holder.from = allMessages.get(position).getFrom();
            holder.to = allMessages.get(position).getTo();
            holder.messageView.setText(holder.message);
            if(holder.from.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                //Left align messages received and right align messages sent
                holder.messageView.setGravity(Gravity.RIGHT);
                holder.messageView.setTextAlignment(View.TEXT_ALIGNMENT_GRAVITY);
            }
        }

        @Override
        public int getItemCount() {
            return allMessages.size();
        }

        class ChatViewHolder extends RecyclerView.ViewHolder {
            public String from, to, message;
            public TextView messageView;
            public ChatViewHolder(View itemView) {
                super(itemView);
                messageView = itemView.findViewById(R.id.chat);
            }
        }
    }
}
