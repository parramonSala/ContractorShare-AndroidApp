package com.android.contractorshare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.contractorshare.R;

public class MainMenuAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public MainMenuAdapter(Context context, String[] values) {
        super(context, R.layout.list_home_activity, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_home_activity, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        textView.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        System.out.println(s);

        switch (s) {
            case "CreateJob":
                imageView.setImageResource(R.drawable.create_job_icon);
                break;
            case "Logout":
                imageView.setImageResource(R.drawable.logout_icon);
                break;
            case "ManageAccount":
                imageView.setImageResource(R.drawable.options_icon);
                break;
            default:
                imageView.setImageResource(R.drawable.tools_icons);
                break;
        }

        return rowView;
    }
}