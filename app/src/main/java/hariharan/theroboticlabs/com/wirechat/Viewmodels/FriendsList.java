package hariharan.theroboticlabs.com.wirechat.Viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hariharan.theroboticlabs.com.wirechat.Utils.FirebaseUtils;
import hariharan.theroboticlabs.com.wirechat.Utils.User;

/**
 * Created by hariharan on 7/24/18.
 */

public class FriendsList extends ViewModel{

    private static final String TAG = "FriendsList";
    MutableLiveData<List<User>> allUsers;
    private DatabaseReference databaseRef;
    private String uid;
    private List<User> users;

    public FriendsList() {
        databaseRef = FirebaseUtils.getDatabaseRef();
        allUsers = new MutableLiveData<>();
        users = new ArrayList<>();

    }

    public void setUid(final String uid) {
        this.uid = uid;
        databaseRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!users.isEmpty())
                    users.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.hasChild("uid")) {
                        String fkey = snapshot.getKey();
                        String fName = snapshot.child("name").getValue().toString();
                        Log.d(TAG, "onDataChange: "+ "Test");
                        User user = new User(fName, fkey);
                        add(user);
                        Log.d(TAG, "onDataChange: "+"Test"+
                                " ... "+snapshot.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public LiveData<List<User>> getFriends() {
        return allUsers;
    }

    public void add(User user) {
        users.add(user);
        allUsers.postValue(users);
    }
}
