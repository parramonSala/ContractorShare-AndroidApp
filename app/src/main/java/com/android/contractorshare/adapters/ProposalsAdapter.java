package com.android.contractorshare.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.models.Proposal;
import com.android.contractorshare.utils.ProfileHandler;

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
        //        private TextView job;
        private TextView jobLabel;
        private TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
            price = (TextView) itemView.findViewById(R.id.price);
            jobLabel = (TextView) itemView.findViewById(R.id.job_name_label);
        }

        public void bind(final Proposal proposal, final OnItemClickListener listener, Context context) {
            name.setText(proposal.getFromUsername());
            jobLabel.setText(proposal.getJobName());
            price.setText("£" + proposal.getProposedPrice().toString());

            image.setImageDrawable(ProfileHandler.get(proposal.getFromUserId(), context));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(proposal);
                }
            });
        }
    }
}