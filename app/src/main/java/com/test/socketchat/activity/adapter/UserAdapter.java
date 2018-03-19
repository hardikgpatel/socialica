package com.test.socketchat.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.socketchat.R;
import com.test.socketchat.activity.model.ModelUser;
import com.test.socketchat.activity.utility.CircleTransform;

import java.util.List;

/**
 * Created by lcom151-two on 3/19/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {

    private List<ModelUser> users;
    private Context context;
    private UserClickListner clickListner;

    public UserAdapter(List<ModelUser> users, Context context, UserClickListner clickListner) {
        this.users = users;
        this.context = context;
        this.clickListner = clickListner;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user,null,false));
    }

    @Override
    public void onBindViewHolder(UserHolder holder, final int position) {
        holder.tvDisplayName.setText(users.get(position).getDisplayName());
        holder.tvDisplayName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        holder.tvUserName.setText(users.get(position).getUserName().toString());
        String urlUserImgae = context.getResources().getString(R.string.host) + "profile/" + users.get(position).getUserProfilePhoto();
        Picasso.get().load(urlUserImgae).transform(new CircleTransform()).error(R.drawable.profile).into(holder.ivProfile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onSelectUser(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        ImageView ivProfile;
        TextView tvUserName,tvDisplayName;

        public UserHolder(View itemView) {
            super(itemView);
            ivProfile=itemView.findViewById(R.id.iv_profile);
            tvUserName=itemView.findViewById(R.id.tv_username);
            tvDisplayName=itemView.findViewById(R.id.tv_display_name);
        }
    }

    public interface UserClickListner{
        void onSelectUser(int position);
    }
}
