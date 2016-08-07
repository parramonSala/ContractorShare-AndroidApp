package com.android.contractorshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.models.JobTask;

import java.util.ArrayList;

/**
 * Created by Roger on 06/02/2016.
 */
public class TaskAdapter extends ArrayAdapter<JobTask> {

    private final Context context;
    private final ArrayList<JobTask> tasks;

    public TaskAdapter(Context context, ArrayList<JobTask> tasks) {
        super(context, R.layout.task_list_item, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        JobTask task = getItem(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.task_list_item, null);

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(task.getName());

        return convertView;
    }
}
