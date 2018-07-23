package hariharan.theroboticlabs.com.wirechat;

import android.content.Context;
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

import java.util.ArrayList;

import hariharan.theroboticlabs.com.wirechat.Fragments.ChatsList;
import hariharan.theroboticlabs.com.wirechat.Fragments.ScanAndShare;

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
            chatsList.add("John Doe");
            chatsList.add("uayfi aofih");
            chatsList.add("HOia oalkfj");
            chatsList.add("haius afjl");
            chatsList.add("Joasjf hafkl");

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.add(chatsList, "Home");
        sectionsPageAdapter.add(new ScanAndShare(), "Scan and Share");
        ViewPager viewPager = (ViewPager) findViewById(R.id.home_page_viewpager);
        viewPager.setAdapter(sectionsPageAdapter);
        Log.d(TAG, "onCreate: "+users.size());

    }

//    class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatsListViewholder> {
//
//        private static final String TAG = "ChatsListAdapter";
//        public ArrayList<String> users;
//        private Context mContext;
//
//        ChatsListAdapter(Context context, ArrayList<String> users) {
//            Log.d(TAG, "ChatsListAdapter: Constructor ");
//            this.users = users;
//            this.mContext = context;
//            Log.d(TAG, "t"+users.size());
//        }
//
//        @Override
//        public ChatsListAdapter.ChatsListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_head, parent, false);
//            ChatsListAdapter.ChatsListViewholder chatsListViewholder = new ChatsListAdapter.ChatsListViewholder(view);
//            //Toast.makeText(mContext, ""+users.size(), Toast.LENGTH_SHORT).show();
//            return chatsListViewholder;
//        }
//
//        @Override
//        public void onBindViewHolder(ChatsListAdapter.ChatsListViewholder holder, int position) {
//            holder.name.setText(users.get(position));
//            holder.profilePicture.setImageResource(R.drawable.ic_launcher_background);
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return users.size();
//        }
//
//        class ChatsListViewholder extends RecyclerView.ViewHolder {
//            public TextView name;
//            public ImageView profilePicture;
//
//            public ChatsListViewholder(View itemView) {
//                super(itemView);
//                name = itemView.findViewById(R.id.name);
//                profilePicture = itemView.findViewById(R.id.profile_picture);
//            }
//        }
//    }
}
