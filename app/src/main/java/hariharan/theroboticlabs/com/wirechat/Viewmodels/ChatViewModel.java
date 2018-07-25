package hariharan.theroboticlabs.com.wirechat.Viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hariharan.theroboticlabs.com.wirechat.Utils.ChatMessage;
import hariharan.theroboticlabs.com.wirechat.Utils.FirebaseUtils;

/**
 * Created by hariharan on 7/25/18.
 */

public class ChatViewModel extends ViewModel{

    private DatabaseReference databaseRef;
    private FirebaseUtils firebaseUtils;
    private String uid, fuid;
    private List<ChatMessage> allMessages;
    private MutableLiveData<List<ChatMessage>> messagesLiveData;

    public ChatViewModel() {
        allMessages = new ArrayList<>();
        messagesLiveData = new MutableLiveData<>();
        firebaseUtils = new FirebaseUtils();
        databaseRef = firebaseUtils.getDatabaseRef();
    }

    public String getUid() {
        return uid;
    }

    public String getFuid() {
        return fuid;
    }

    public void setChatPair(String uid, String fuid) {
        this.uid = uid;
        this.fuid = fuid;
        databaseRef.child(this.uid).child(this.fuid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!allMessages.isEmpty())
                            allMessages.clear();

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.hasChild("from")) {
                                ChatMessage message = snapshot.getValue(ChatMessage.class);
                                add(message);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void add(ChatMessage message) {
        allMessages.add(message);
        messagesLiveData.postValue(allMessages);
    }

    public MutableLiveData<List<ChatMessage>> getMessages() {
        return messagesLiveData;
    }
}
