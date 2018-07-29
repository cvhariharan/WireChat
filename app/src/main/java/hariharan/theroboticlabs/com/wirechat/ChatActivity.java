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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import hariharan.theroboticlabs.com.wirechat.Utils.ChatMessage;
import hariharan.theroboticlabs.com.wirechat.Utils.FirebaseUtils;
import hariharan.theroboticlabs.com.wirechat.Viewmodels.ChatViewModel;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";
    private FirebaseUtils firebaseUtils;
    private EditText messageField;
    private RecyclerView chatRecycler;
    private String toUid, toName;
    private ChatViewModel chatViewModel;
    private int SENT_TYPE = 1;
    private int RECEIVED_TYPE = 2;

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
                chatRecycler.scrollToPosition(chatRecycler.getAdapter().getItemCount()-1);
            }
        });

        ImageButton sendButton = (ImageButton) findViewById(R.id.send_button);
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

    class ChatRecycler extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private List<ChatMessage> allMessages;
        public ChatRecycler(List<ChatMessage> messages) {
            this.allMessages = messages;
        }



        @Override
        public int getItemViewType(int position) {
            if(allMessages.get(position).getFrom().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                return SENT_TYPE;
            else
                return RECEIVED_TYPE;
        }

        @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View mView;
                if(viewType == SENT_TYPE) {
                    mView = LayoutInflater.from(ChatActivity.this).inflate(R.layout.sent_chat_bubble, parent, false);
                    return new ChatViewHolderSent(mView);
                }
                else {
                    mView = LayoutInflater.from(ChatActivity.this).inflate(R.layout.received_chat_bubble, parent, false);
                    return new ChatViewHolderRecv(mView);
                }

            }


            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                if(holder.getItemViewType() == SENT_TYPE) {
                    ChatViewHolderSent sentHolder = (ChatViewHolderSent) holder;
                    sentHolder.message = allMessages.get(position).getMessage();
                    sentHolder.from = allMessages.get(position).getFrom();
                    sentHolder.to = allMessages.get(position).getTo();
                    sentHolder.messageView.setText(sentHolder.message);
                }
                else {
                    ChatViewHolderRecv recvHolder = (ChatViewHolderRecv) holder;
                    recvHolder.message = allMessages.get(position).getMessage();
                    recvHolder.from = allMessages.get(position).getFrom();
                    recvHolder.to = allMessages.get(position).getTo();
                    recvHolder.messageView.setText(recvHolder.message);
                }
            }

            @Override
            public int getItemCount() {
                return allMessages.size();
            }

            class ChatViewHolderSent extends RecyclerView.ViewHolder {
                public String from, to, message;
                public TextView messageView;
                public ChatViewHolderSent(View itemView) {
                    super(itemView);
                    messageView = itemView.findViewById(R.id.chat);
                }
            }

            class ChatViewHolderRecv extends RecyclerView.ViewHolder {
                public String from, to, message;
                public TextView messageView;
                public ChatViewHolderRecv(View itemView) {
                    super(itemView);
                    messageView = itemView.findViewById(R.id.chat);
                }
            }
        }
}
