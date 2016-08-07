package com.android.contractorshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.adapters.MainMenuAdapter;
import com.android.contractorshare.fragments.DrawerFragment;
import com.android.contractorshare.session.SessionManager;


public class HomeActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener {

    static final String[] Menu_Items_Client =
            new String[]{"Create Job", "View My Jobs", "Manage Proposals", "Logout"};
    static final String[] Menu_Items_Professional =
            new String[]{"Find Jobs ", "View My Appointments", "Manage Proposals", "Logout"};
    public static int mScreenHeight;
    public static int mScreenWidth;
    private GridView mGridView;
    private int userId;
    private SessionManager mSessionManager;
    private DrawerFragment fragmentDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSessionManager = new SessionManager(this);

        int userTypeId = getIntent().getExtras().getInt("UserTypeId");
        userId = getIntent().getExtras().getInt("UserId");
        mGridView = (GridView) findViewById(R.id.gridview);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String selectedValue = (String) parent.getItemAtPosition(position);
                HandleClick(selectedValue);
            }
        });

        if (UserTypes.Client.getValue() == userTypeId) {
            mGridView.setAdapter(new MainMenuAdapter(this, Menu_Items_Client));
        } else {
            mGridView.setAdapter(new MainMenuAdapter(this, Menu_Items_Professional));
        }

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels - 180;

        //Setting toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Setting navigation drawer
        fragmentDrawer = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), myToolbar);
        fragmentDrawer.setDrawerListener(this);
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
            case "Create Job":
                navigateToActivity("CreateJob");
                break;
            case "View My Jobs":
                navigateToActivity("ManageJobsActivity");
                break;
            case "Manage Proposals":
                navigateToActivity("ManageProposals");
                break;
            case "Logout":
                mSessionManager.logoutUser();
                break;
            case "Find Jobs":
                navigateToActivity("FindJobs");
                break;
            case "View My Appointments":
                navigateToActivity("ViewMyAppointments");
                break;
        }

    }


    private void navigateToActivity(String activity) {
        //Default
        Intent intent = null;
        switch (activity) {
            case "ManageJobsActivity":
                intent = new Intent(this, ManageJobsActivity.class);
                break;
            case "ManageProposals":
                intent = new Intent(this, ManageProposalsActivity.class);
                break;
        }
        if (intent != null) {
            intent.putExtra("userId", userId);
            startActivity(intent);
        }
    }

    public enum UserTypes {
        Client(1),
        Professional(2);

        private int value;

        UserTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}
