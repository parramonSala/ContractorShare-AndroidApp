package com.android.contractorshare.activities;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;

import com.android.contractorshare.R;
import com.android.contractorshare.fragments.JobDetailsFragment;
import com.android.contractorshare.fragments.JobListFragment;
import com.android.contractorshare.models.Job;

public class ViewJobsActivity extends FragmentActivity implements JobListFragment.OnListFragmentInteractionListener {

    protected int muserId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        muserId = settings.getInt("userId", -1);

        if (savedInstanceState == null) {
            Fragment jobListFragment = new JobListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", muserId);
            jobListFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.fragmentContainer, jobListFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public void onListFragmentInteraction(Job job) {
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, JobDetailsFragment.newInstance(job)).addToBackStack(null).commit();
    }
}
