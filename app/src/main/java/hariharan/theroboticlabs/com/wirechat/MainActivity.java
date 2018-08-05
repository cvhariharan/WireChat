package hariharan.theroboticlabs.com.wirechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hariharan.theroboticlabs.com.wirechat.Jobs.SyncJob;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
        databaseRef.keepSynced(true);
        setUpSyncJob();
        Intent i = new Intent(this, SigninActivity.class);
        startActivity(i);
    }

    private void setUpSyncJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        Job sync = dispatcher.newJobBuilder()
                .setService(SyncJob.class) // the JobService that will be called
                .setTag("sync")        // uniquely identifies the job
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.executionWindow(0, 60))
                .build();

        dispatcher.mustSchedule(sync);
    }
}
