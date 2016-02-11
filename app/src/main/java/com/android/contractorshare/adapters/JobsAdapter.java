package com.android.contractorshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.models.Job;

import java.util.ArrayList;

/**
 * Created by Roger on 06/02/2016.
 */
public class JobsAdapter extends ArrayAdapter<Job> {

    private final Context context;
    private final ArrayList<Job> jobs;

    public JobsAdapter(Context context, ArrayList<Job> jobs) {
        super(context, R.layout.job_list_item, jobs);
        this.context = context;
        this.jobs = jobs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Job job = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.job_list_item, null);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(job.getName());

        TextView city = (TextView) convertView.findViewById(R.id.city);
        city.setText(job.getCity());

        TextView address = (TextView) convertView.findViewById(R.id.address);
        address.setText(job.getAddress());

        return convertView;
    }
}
