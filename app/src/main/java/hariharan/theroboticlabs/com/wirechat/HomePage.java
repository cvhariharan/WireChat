package hariharan.theroboticlabs.com.wirechat;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import hariharan.theroboticlabs.com.wirechat.Fragments.ChatsList;
import hariharan.theroboticlabs.com.wirechat.Fragments.ScanAndShare;
import hariharan.theroboticlabs.com.wirechat.Jobs.SyncJob;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SectionsPageAdapter sectionsPageAdapter;
    public ArrayList<String> users = new ArrayList<>();
    private ChatsList chatsList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if the user has signed in and appropriately route to signinactivity.
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Log.d(TAG, "onCreate: SignIn");
            openSigninActivity();
        }
        setContentView(R.layout.activity_home_page);

        chatsList = new ChatsList();
//            chatsList.add("John Doe");
//            chatsList.add("uayfi aofih");
//            chatsList.add("HOia oalkfj");
//            chatsList.add("haius afjl");
//            chatsList.add("Joasjf hafkl");

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.add(chatsList, "Home");
        sectionsPageAdapter.add(new ScanAndShare(), "Scan and Share");
        ViewPager viewPager = (ViewPager) findViewById(R.id.home_page_viewpager);
        viewPager.setAdapter(sectionsPageAdapter);
        Log.d(TAG, "onCreate: "+users.size());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.logout :
                FirebaseAuth.getInstance().signOut();
                openSigninActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openSigninActivity() {
        Intent i = new Intent(this, SigninActivity.class);
        startActivity(i);
        Log.d(TAG, "openSigninActivity: ");
    }
}
