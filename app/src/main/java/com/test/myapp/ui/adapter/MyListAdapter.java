package com.test.myapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.myapp.R;
import com.test.myapp.callback.OnItemClickListener;
import com.test.myapp.data.model.Datum;
import com.test.myapp.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private List<Datum> userList;
    private OnItemClickListener clickListener;

    public MyListAdapter(){
        userList = new ArrayList<>();
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_user,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum datum = userList.get(position);
        holder.username.setText(datum.getFirstName()+" "+datum.getLastName());
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClickListener(position,datum);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<Datum> userList){
        this.userList = userList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public LinearLayout rootLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.rootLayout = itemView.findViewById(R.id.rootLayout);
            this.username = (TextView) itemView.findViewById(R.id.username);
        }
    }
}
