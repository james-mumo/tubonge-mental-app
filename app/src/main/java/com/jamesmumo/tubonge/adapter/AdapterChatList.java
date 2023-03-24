package com.jamesmumo.tubonge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jamesmumo.tubonge.R;
import com.jamesmumo.tubonge.model.ModelUser;
import com.jamesmumo.tubonge.shareChat.Chat;
import com.jamesmumo.tubonge.welcome.GetTimeAgo;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChatList extends RecyclerView.Adapter<AdapterChatList.MyHolder> {

    final Context context;
    final List<ModelUser> userList;
    private final HashMap<String, String> lastMessageMap;

    public AdapterChatList(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
        lastMessageMap = new HashMap<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatlist, parent, false);
        return new MyHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String hisUid = userList.get(position).getId();
        String dp = userList.get(position).getPhoto();
        String name = userList.get(position).getName();
        String lastMessage = lastMessageMap.get(hisUid);


        holder.mName.setText(name);
        if (lastMessage == null || lastMessage.equals("default")) {
            holder.message.setVisibility(View.GONE);
        } else {
            holder.message.setVisibility(View.VISIBLE);
            holder.message.setText(lastMessage);
        }
        try {
            Picasso.get().load(dp).placeholder(R.drawable.avatar).into(holder.mDp);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.avatar).into(holder.mDp);
        }

        if (userList.get(position).getStatus().equals("online")) {
            holder.status.setImageResource(R.drawable.online);
            holder.lastSeenText.setText(R.string.online);
        } else {
            String lastSeen = userList.get(position).getStatus();

            GetTimeAgo getTimeAgo = new GetTimeAgo();
            long lastTime = Long.parseLong(lastSeen);
            String lastSeenTime = GetTimeAgo.getTimeAgo(lastTime);
            holder.status.setImageResource(R.drawable.offline);
            holder.lastSeenText.setText("Seen " + lastSeenTime);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("hisUid", hisUid);
            context.startActivity(intent);
        });

    }

    public void setLastMessageMap(String userId, String lastMessage) {
        lastMessageMap.put(userId, lastMessage);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {

        final CircleImageView mDp;
        final ImageView status;
        final TextView mName;
        final TextView message;
        final TextView lastSeenText;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mDp = itemView.findViewById(R.id.circleImageView);
            status = itemView.findViewById(R.id.status);
            mName = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.username);
            lastSeenText = itemView.findViewById(R.id.lastSeenText);
        }
    }

}
