package hariharan.theroboticlabs.com.wirechat.Jobs;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hariharan on 7/29/18.
 */

public class SyncJob extends JobService {
    @Override
    public boolean onStartJob(JobParameters job) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        databaseRef.keepSynced(true);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
