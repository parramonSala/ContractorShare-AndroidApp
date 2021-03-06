package com.android.contractorshare.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.fragments.DrawerFragment;
import com.android.contractorshare.fragments.MessageFragment;
import com.android.contractorshare.fragments.ProposalDetailsFragment;
import com.android.contractorshare.fragments.ProposalsListFragment;
import com.android.contractorshare.models.Proposal;
import com.android.contractorshare.session.SessionManager;

import java.util.HashMap;


public class ManageProposalsActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener, ProposalsListFragment.OnListFragmentInteractionListener, ProposalDetailsFragment.OnListFragmentInteractionListener, MessageFragment.OnListFragmentInteractionListener {

    private RecyclerView mGridView;
    private int mUserId;
    private SessionManager mSessionManager;
    private DrawerFragment fragmentDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_proposals);

        //Setting toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Setting navigation drawer
        fragmentDrawer = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), myToolbar);
        fragmentDrawer.setDrawerListener(this);

        mSessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = mSessionManager.getUserDetails();
        mUserId = Integer.parseInt(userDetails.get("userId"));

        if (savedInstanceState == null) {
            Fragment proposalsListFragment = new ProposalsListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", mUserId);
            proposalsListFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.fragmentContainer, proposalsListFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        TextView text = (TextView) view.findViewById(R.id.title);
        HandleClick(text.getText().toString());
    }

    private void HandleClick(String selectedValue) {
        switch (selectedValue) {
//            case "Create Job":
//                navigateToActivity("CreateJob");
//                break;
//            case "View My Jobs":
//                navigateToActivity("ViewJobsActivity");
//                break;
//            case "Manage Proposals":
//                navigateToActivity("ManageProposals");
//                break;
//            case "Logout":
//                mSessionManager.logoutUser();
//                break;
//            case "Find Jobs":
//                navigateToActivity("FindJobs");
//                break;
//            case "View My Appointments":
//                navigateToActivity("ViewMyAppointments");
//                break;
        }

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }

    @Override
    public void onListFragmentInteraction(Proposal proposal, String next) {
        switch (next) {
            case "proposalList":
                Fragment proposalsListFragment = new ProposalsListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("userId", mUserId);
                proposalsListFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, proposalsListFragment).addToBackStack(null).commit();
                break;
            case "proposalDetails":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, ProposalDetailsFragment.newInstance(proposal)).addToBackStack(null).commit();
                break;

            case "proposalMessages":
                int otherUserId;
                if (mUserId == proposal.getFromUserId()) otherUserId = proposal.getToUserId();
                else otherUserId = proposal.getFromUserId();
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, MessageFragment.newInstance(proposal.getProposalId(), otherUserId)).addToBackStack(null).commit();
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Proposal proposal, String next);
    }

}
