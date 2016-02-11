package com.android.contractorshare.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.fragments.JobListFragment;

public class ViewJobsActivity extends FragmentActivity implements JobListFragment.OnListFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);
    }


//    protected void onListItemClick(ListView l, View v, int position, long id) {
//
//        //get selected items
//        ListAdapter adapter = getListAdapter();
//        if (adapter != null) {
//            String selectedValue = (String) adapter.getItem(position);
//            Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
//            /*NavigateToJobDetails(selectedValue);*/
//        }
//    }

    @Override
    public void onListFragmentInteraction() {
        Toast.makeText(ViewJobsActivity.this, "Test", Toast.LENGTH_SHORT).show();
    }
}
