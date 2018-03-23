package com.test.socketchat.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.socketchat.R;
import com.test.socketchat.activity.model.ModelUser;
import com.test.socketchat.activity.utility.CircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lcom151-two on 3/19/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> implements Filterable {

    private List<ModelUser> usersFilteredList=new ArrayList<>();
    private List<ModelUser> users;
    private Context context;
    private UserClickListner clickListner;
    private final int EMPTY_RESULT=0;
    private final int HAS_RESULT=1;

    public UserAdapter(List<ModelUser> users, Context context, UserClickListner clickListner) {
        this.users = users;
        this.usersFilteredList = users;
        this.context = context;
        this.clickListner = clickListner;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, null, false));
    }

    @Override
    public void onBindViewHolder(UserHolder holder, final int position) {
        holder.tvDisplayName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        if(TextUtils.isEmpty(usersFilteredList.get(position).getSpannableDisplayName())){
            holder.tvDisplayName.setText(usersFilteredList.get(position).getDisplayName());
        }else{
            holder.tvDisplayName.setText(usersFilteredList.get(position).getSpannableDisplayName());
        }

        if(TextUtils.isEmpty(usersFilteredList.get(position).getSpannableUserName())){
            holder.tvUserName.setText(usersFilteredList.get(position).getUserName().toString());
        }else{
            holder.tvUserName.setText(usersFilteredList.get(position).getSpannableUserName());
        }

        if(TextUtils.isEmpty(usersFilteredList.get(position).getSpannableEmail())){
            holder.tvEmail.setText(usersFilteredList.get(position).getEmail());
        }else{
            holder.tvEmail.setText(usersFilteredList.get(position).getSpannableEmail());
        }

        if(TextUtils.isEmpty(usersFilteredList.get(position).getSpannableId())){
            holder.tvId.setText(usersFilteredList.get(position).getUserId());
        }else{
            holder.tvId.setText(usersFilteredList.get(position).getSpannableId());
        }

        String urlUserImgae = context.getResources().getString(R.string.host) + "profile/" + usersFilteredList.get(position).getUserProfilePhoto();
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
        return usersFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if (TextUtils.isEmpty(query)) {
                    usersFilteredList.clear();
                } else {
                    List<ModelUser> filterd = new ArrayList<>();
                    for (ModelUser user : users) {
                        if(user.getDisplayName().toLowerCase().contains(query.toLowerCase()) ||
                                user.getUserName().toString().toLowerCase().contains(query.toLowerCase())||
                                user.getEmail().toLowerCase().contains(query.toLowerCase())||
                                user.getUserId().toLowerCase().contains(query.toLowerCase())){

                            // match DisplayName
                            if(user.getDisplayName().toLowerCase().contains(query.toLowerCase())){
                                SpannableString str=new SpannableString(user.getDisplayName());
                                str.setSpan(new ForegroundColorSpan(Color.BLUE),user.getDisplayName().toLowerCase().indexOf(query.toLowerCase()),user.getDisplayName().toLowerCase().indexOf(query.toLowerCase())+query.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                user.setSpannableDisplayName(str);
                            }else{
                                user.setSpannableDisplayName(null);
                            }

                            // match UserName
                            if(user.getUserName().toString().toLowerCase().contains(query.toLowerCase())){
                                SpannableString str=new SpannableString(user.getUserName().toString());
                                str.setSpan(new ForegroundColorSpan(Color.BLUE),user.getUserName().toString().toLowerCase().indexOf(query.toLowerCase()),user.getUserName().toString().toLowerCase().indexOf(query.toLowerCase())+query.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                user.setSpannableUserName(str);
                            }else{
                                user.setSpannableUserName(null);
                            }

                            // match Email
                            if(user.getEmail().toLowerCase().contains(query.toLowerCase())){
                                SpannableString str=new SpannableString(user.getEmail());
                                str.setSpan(new ForegroundColorSpan(Color.BLUE),user.getEmail().toLowerCase().indexOf(query.toLowerCase()),user.getEmail().toLowerCase().indexOf(query.toLowerCase())+query.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                user.setSpannableEmail(str);
                            }else{
                                user.setSpannableEmail(null);
                            }

                            // match uId
                            if(user.getUserId().toLowerCase().contains(query.toLowerCase())){
                                SpannableString str=new SpannableString(user.getUserId());
                                str.setSpan(new ForegroundColorSpan(Color.BLUE),user.getUserId().toLowerCase().indexOf(query.toLowerCase()),user.getUserId().toLowerCase().indexOf(query.toLowerCase())+query.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                user.setSpannableId(str);
                            }else{
                                user.setSpannableId(null);
                            }

                            filterd.add(user);
                        }
                    }
                    usersFilteredList=filterd;
                }
                FilterResults results=new FilterResults();
                results.values=usersFilteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                usersFilteredList= (List<ModelUser>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class UserHolder extends RecyclerView.ViewHolder {

        ImageView ivProfile;
        TextView tvUserName, tvDisplayName,tvEmail,tvId;

        public UserHolder(View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.iv_profile);
            tvUserName = itemView.findViewById(R.id.tv_username);
            tvDisplayName = itemView.findViewById(R.id.tv_display_name);
            tvEmail=itemView.findViewById(R.id.tv_email);
            tvId=itemView.findViewById(R.id.tv_id);
        }
    }

    public interface UserClickListner {
        void onSelectUser(int position);
    }
}
