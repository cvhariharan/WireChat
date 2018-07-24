package hariharan.theroboticlabs.com.wirechat.Utils;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hariharan on 7/21/18.
 */

public class FirebaseUtils {
    private static final String TAG = "FirebaseUtils";
    boolean exists;
    FirebaseDatabase database;
    DatabaseReference databaseRef;

    public FirebaseUtils() {
        database = FirebaseDatabase.getInstance();
        databaseRef = database.getReference("users");
    }

    public void addUser(User user) {
        databaseRef.child(user.getUid()).child("joined_at").setValue(ServerValue.TIMESTAMP);
        databaseRef.child(user.getUid()).child("name").setValue(user.getName());
    }

    public void addFriend(final String uid, final User friend) {
        //Only insert if the friend exists
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(friend.getUid()))
                    databaseRef.child(uid).child(friend.getUid()).setValue(friend);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    public boolean checkUid(final String uid) {
//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                exists = dataSnapshot.hasChild(uid);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        Log.d(TAG, "checkUid: "+exists);
//        return exists;
//    }
//        Query query = databaseRef.child("users").child(uid).equalTo(uid);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d(TAG, "onDataChange: "+dataSnapshot.toString());
//                exists = uid.equals(dataSnapshot.getKey());
//                Log.d(TAG, "onDataChange: "+exists);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        return exists;
//    }
}
