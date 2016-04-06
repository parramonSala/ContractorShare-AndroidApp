package com.android.contractorshare.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.activities.HomeActivity;
import com.android.contractorshare.utils.TypeFaces;

public class MainMenuAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final Typeface mtypeFace;

    public MainMenuAdapter(Context context, String[] values) {
        super(context, R.layout.list_home_activity, values);
        this.context = context;
        this.values = values;
        this.mtypeFace = TypeFaces.get(context, "fontawesome-webfont.ttf");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_home_activity, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        TextView imageView = (TextView) rowView.findViewById(R.id.logo);
        imageView.setTypeface(this.mtypeFace);
        textView.setText(values[position]);
        // Change icon based on name
        String s = values[position];
        rowView.setMinimumHeight(HomeActivity.mScreenHeight / 2);
        rowView.setMinimumWidth(HomeActivity.mScreenWidth / 2);
        System.out.println(s);
        int color = ContextCompat.getColor(context, R.color.primary);
        rowView.setBackgroundColor(color);
        switch (s) {
            case "Create Job":
                imageView.setText(R.string.icon_create);
                break;
            case "Logout":
                imageView.setText(R.string.icon_logout);
                break;
            case "Manage Account":
                imageView.setText(R.string.icon_options);
                break;
            case "View My Jobs":
                imageView.setText(R.string.icon_list_jobs);
                break;
        }

        return rowView;
    }
}