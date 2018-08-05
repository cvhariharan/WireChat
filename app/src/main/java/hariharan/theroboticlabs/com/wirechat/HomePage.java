package hariharan.theroboticlabs.com.wirechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import hariharan.theroboticlabs.com.wirechat.Fragments.ChatsList;
import hariharan.theroboticlabs.com.wirechat.Fragments.ScanAndShare;
import hariharan.theroboticlabs.com.wirechat.Utils.FirebaseUtils;

public class HomePage extends AppCompatActivity {

    private static final String TAG = "HomePage";
    private SectionsPageAdapter sectionsPageAdapter;
    public ArrayList<String> users = new ArrayList<>();
    private ChatsList chatsList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_page);

        chatsList = new ChatsList();
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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        Log.d(TAG, "openSigninActivity: ");
    }
}
