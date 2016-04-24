package com.android.contractorshare.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.models.Proposal;

import java.util.List;

public class ProposalsAdapter extends RecyclerView.Adapter<ProposalsAdapter.ViewHolder> {

    private final List<Proposal> items;
    private final OnItemClickListener listener;
    private final Context context;

    public ProposalsAdapter(List<Proposal> items, OnItemClickListener listener, Context context) {
        this.items = items;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.proposal_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items.get(position), listener, this.context);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Proposal proposal);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;
        private TextView job;
        private TextView price;
        private TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            job = (TextView) itemView.findViewById(R.id.job);
            price = (TextView) itemView.findViewById(R.id.price);
            date = (TextView) itemView.findViewById(R.id.date);
        }

        public void bind(final Proposal proposal, final OnItemClickListener listener, Context context) {
            name.setText(proposal.getFromUserId().toString());
            job.setText(proposal.getJobId().toString());
            price.setText(proposal.getProposedPrice().toString());
            date.setText(proposal.getProposedTime().toString());
            image.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_profile_roger));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(proposal);
                }
            });
        }
    }
}