package com.android.contractorshare.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.contractorshare.R;
import com.android.contractorshare.fragments.CommentsFragment;
import com.android.contractorshare.fragments.CreateEditTaskFragment;
import com.android.contractorshare.fragments.DrawerFragment;
import com.android.contractorshare.fragments.EditJobFragment;
import com.android.contractorshare.fragments.JobDetailsFragment;
import com.android.contractorshare.fragments.JobListFragment;
import com.android.contractorshare.fragments.TaskDetailsFragment;
import com.android.contractorshare.fragments.TaskListFragment;
import com.android.contractorshare.models.Job;
import com.android.contractorshare.models.JobTask;

public class ManageJobsActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener, JobListFragment.OnListFragmentInteractionListener, EditJobFragment.OnListFragmentInteractionListener, JobDetailsFragment.OnListFragmentInteractionListener, TaskListFragment.OnListFragmentInteractionListener, TaskDetailsFragment.OnListFragmentInteractionListener, CreateEditTaskFragment.OnListFragmentInteractionListener, CommentsFragment.OnListFragmentInteractionListener {

    protected int muserId;
    private Toolbar mToolbar;
    private DrawerFragment fragmentDrawer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);
        muserId = getIntent().getExtras().getInt("userId");

        //Setting toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Setting navigation drawer
        fragmentDrawer = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), myToolbar);
        fragmentDrawer.setDrawerListener(this);

        if (savedInstanceState == null) {
            Fragment jobListFragment = new JobListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", muserId);
            jobListFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.fragmentContainer, jobListFragment).addToBackStack(null).commit();
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
    public void onListFragmentInteraction(Job job, String next) {
        switch (next) {
            case "jobDetails":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, JobDetailsFragment.newInstance(job)).addToBackStack(null).commit();
                break;
            case "jobTasks":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, TaskListFragment.newInstance(job.getId())).addToBackStack(null).commit();
                break;
            case "editJob":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, EditJobFragment.newInstance(job)).addToBackStack(null).commit();
            case "JobComments":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, CommentsFragment.newInstance(-1, job.getId())).addToBackStack(null).commit();
                break;
        }
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    @Override
    public void onListFragmentInteraction(JobTask task, String next) {
        switch (next) {
            case "taskDetails":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, TaskDetailsFragment.newInstance(task)).addToBackStack(null).commit();
                break;
            case "CreateTask":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, CreateEditTaskFragment.newInstance(task, "CreateTask")).addToBackStack(null).commit();
                break;
            case "EditTask":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, CreateEditTaskFragment.newInstance(task, "EditTask")).addToBackStack(null).commit();
                break;
            case "taskComments":
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, CommentsFragment.newInstance(task.getTaskId(), task.getServiceId())).addToBackStack(null).commit();
                break;
        }
    }
}
