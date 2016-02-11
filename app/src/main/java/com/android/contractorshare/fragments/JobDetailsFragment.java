package com.android.contractorshare.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.contractorshare.R;

/**
 * Created by Roger on 03/02/2016.
 */
public class JobDetailsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_job_details, container, false);
    }

    public void updateJobDetails(String Job) {

        //TODO: Either pass an object, or as many strings as views will be updated.
        //TODO: Set all the textView values here:
    }
}
