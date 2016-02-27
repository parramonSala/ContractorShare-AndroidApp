package com.android.contractorshare.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.models.Job;
import com.android.contractorshare.utils.TypeFaces;

/**
 * Created by Roger on 03/02/2016.
 */
public class JobDetailsFragment extends Fragment {

    private Job mJob;
    private View mView;

    public static JobDetailsFragment newInstance(Job job) {
        JobDetailsFragment fragment = new JobDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("job", job);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJob = getArguments().getParcelable("job");
        mView = inflater.inflate(R.layout.fragment_job_details, container, false);

        TextView name = (TextView) mView.findViewById(R.id.name);
        name.setText(mJob.getName());

        TextView city = (TextView) mView.findViewById(R.id.city);
        city.setText(mJob.getCity());

        TextView address = (TextView) mView.findViewById(R.id.address);
        address.setText(mJob.getAddress());

        TextView description = (TextView) mView.findViewById(R.id.description);
        address.setText(mJob.getDescription());

        Typeface font = TypeFaces.get(getActivity(), "fontawesome-webfont.ttf");
        TextView edit = (TextView) mView.findViewById(R.id.edit);
        edit.setTypeface(font);
        TextView close = (TextView) mView.findViewById(R.id.close);
        close.setTypeface(font);

        return mView;
    }
}
