package hariharan.theroboticlabs.com.wirechat.Jobs;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by hariharan on 7/29/18.
 */

public class SyncJob extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        databaseRef.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
