package hariharan.theroboticlabs.com.wirechat.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import hariharan.theroboticlabs.com.wirechat.ChatActivity;
import hariharan.theroboticlabs.com.wirechat.R;
import hariharan.theroboticlabs.com.wirechat.Utils.User;
import hariharan.theroboticlabs.com.wirechat.Viewmodels.FriendsList;

/**
 * Created by hariharan on 7/21/18.
 */

public class ChatsList extends Fragment {

    private static final String TAG = "ChatsList";
    public ArrayList<String> users = new ArrayList<>();
    private ChatsListAdapter adapter;
    private RecyclerView chatsList;
    private FriendsList friendsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.chats_list, container, false);
        chatsList = (RecyclerView) mView.findViewById(R.id.chats_list);
        chatsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatsList.setHasFixedSize(true);
        chatsList.setAdapter(new ChatsListAdapter(getContext(), new ArrayList<User>()));

        friendsList = ViewModelProviders.of(this).get(FriendsList.class);
        //Get the current users friends list
        friendsList.setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());


        friendsList.getFriends().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                Log.d(TAG, "onChanged: Got new users");
                adapter = new ChatsListAdapter(getContext(), users);
                chatsList.setAdapter(adapter);
            }
        });
        return mView;
    }

    class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatsListViewholder> {

        private static final String TAG = "ChatsListAdapter";
        private List<User> users;
        private Context mContext;

        ChatsListAdapter(Context context, List<User> users) {
            Log.d(TAG, "ChatsListAdapter: Constructor ");
            this.users = users;
            this.mContext = context;
            Log.d(TAG, "t"+users.size());
        }

        @Override
        public ChatsListAdapter.ChatsListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_head, parent, false);
            ChatsListAdapter.ChatsListViewholder chatsListViewholder = new ChatsListAdapter.ChatsListViewholder(view);
            //Toast.makeText(mContext, ""+users.size(), Toast.LENGTH_SHORT).show();
            return chatsListViewholder;
        }

        @Override
        public void onBindViewHolder(final ChatsListAdapter.ChatsListViewholder holder, int position) {
            holder.name.setText(users.get(position).getName());
            holder.profilePicture.setImageResource(R.mipmap.profile);
            holder.setUid(users.get(position).getUid());
            holder.setUserName(users.get(position).getName());
            holder.mParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent openChat = new Intent(getActivity(), ChatActivity.class);
                    openChat.putExtra("TO_UID", holder.getUid());
                    openChat.putExtra("TO_NAME", holder.getUserName());
                    startActivity(openChat);
                }
            });
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        class ChatsListViewholder extends RecyclerView.ViewHolder {
            public TextView name;
            public ImageView profilePicture;
            public LinearLayout mParent;
            private String uid;
            private String userName;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public ChatsListViewholder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                profilePicture = itemView.findViewById(R.id.profile_picture);
                mParent = itemView.findViewById(R.id.linear_layout);
            }

        }
    }

}
