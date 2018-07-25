package hariharan.theroboticlabs.com.wirechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import hariharan.theroboticlabs.com.wirechat.Utils.FirebaseUtils;

public class ChatActivity extends AppCompatActivity {

    private FirebaseUtils firebaseUtils;
    private EditText messageField;
    private RecyclerView chatRecycler;
    private String toUid, toName;
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
        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageField.getText().toString();
                if(!TextUtils.isEmpty(message))
                    sendMessage(message);
            }
        });
    }

    private void sendMessage(String message) {
        firebaseUtils.sendMessage(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                toUid, message);
    }
}
