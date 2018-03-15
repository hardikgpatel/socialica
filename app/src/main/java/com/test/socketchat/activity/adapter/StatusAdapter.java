package com.test.socketchat.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.socketchat.R;

import java.util.ArrayList;

/**
 * Created by lcom151-one on 3/1/2018.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusHolder> {

    private ArrayList<String> arStatus;
    private StatusSelect statusSelect;

    public StatusAdapter(ArrayList<String> arStatus, StatusSelect statusSelect) {
        this.arStatus = arStatus;
        this.statusSelect = statusSelect;
    }

    @Override
    public StatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StatusHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_status,null,false));
    }

    @Override
    public void onBindViewHolder(StatusHolder holder, final int position) {
        holder.tvStatus.setText(arStatus.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusSelect.onSelectStatus(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arStatus.size();
    }

    public class StatusHolder extends RecyclerView.ViewHolder {
        TextView tvStatus;
        public StatusHolder(View itemView) {
            super(itemView);

            tvStatus=itemView.findViewById(R.id.tv_status);
        }
    }

    public interface StatusSelect{
        void onSelectStatus(int position);

    }
}
