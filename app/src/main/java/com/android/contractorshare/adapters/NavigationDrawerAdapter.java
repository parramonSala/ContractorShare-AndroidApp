package com.android.contractorshare.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.models.NavDrawerItem;
import com.android.contractorshare.utils.TypeFaces;

import java.util.Collections;
import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    private final Typeface mtypeFace;
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.mtypeFace = TypeFaces.get(context, "fontawesome-webfont.ttf");
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.icon.setTypeface(this.mtypeFace);
        switch (holder.title.getText().toString()) {
            case "Create Job":
                holder.icon.setText(R.string.icon_create);
                break;
            case "Logout":
                holder.icon.setText(R.string.icon_logout);
                break;
            case "Manage Account":
                holder.icon.setText(R.string.icon_options);
                break;
            case "View My Jobs":
                holder.icon.setText(R.string.icon_list_jobs);
                break;
            case "Home":
                holder.icon.setText(R.string.icon_home);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (TextView) itemView.findViewById(R.id.icon);
        }
    }
}