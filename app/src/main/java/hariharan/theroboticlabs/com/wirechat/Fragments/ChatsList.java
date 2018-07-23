package hariharan.theroboticlabs.com.wirechat.Fragments;

import android.content.Context;
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
import android.widget.TextView;

import java.util.ArrayList;

import hariharan.theroboticlabs.com.wirechat.R;

/**
 * Created by hariharan on 7/21/18.
 */

public class ChatsList extends Fragment {

    public ArrayList<String> users = new ArrayList<>();
    private ChatsListAdapter adapter;
    private RecyclerView chatsList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.chats_list, container, false);
        chatsList = (RecyclerView) mView.findViewById(R.id.chats_list);
        chatsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatsList.setHasFixedSize(true);
        adapter = new ChatsListAdapter(getActivity(), users);
        chatsList.setAdapter(adapter);
        return mView;
    }

    public void add(String name) {
        users.add(name);
    }

    public void refreshAdapter() {
//        chatsList.setAdapter(new ChatsListAdapter(users));
        chatsList.setAdapter(new ChatsListAdapter(getActivity(), users));
    }

    class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ChatsListViewholder> {

        private static final String TAG = "ChatsListAdapter";
        private ArrayList<String> users;
        private Context mContext;

        ChatsListAdapter(Context context, ArrayList<String> users) {
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
        public void onBindViewHolder(ChatsListAdapter.ChatsListViewholder holder, int position) {
            holder.name.setText(users.get(position));
            holder.profilePicture.setImageResource(R.drawable.ic_launcher_background);

        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        class ChatsListViewholder extends RecyclerView.ViewHolder {
            public TextView name;
            public ImageView profilePicture;

            public ChatsListViewholder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                profilePicture = itemView.findViewById(R.id.profile_picture);
            }
        }
    }

}
