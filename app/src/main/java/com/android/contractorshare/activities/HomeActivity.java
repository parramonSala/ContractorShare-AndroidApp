package com.android.contractorshare.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.contractorshare.R;
import com.android.contractorshare.adapters.MainMenuAdapter;
import com.android.contractorshare.session.SessionManager;


public class HomeActivity extends AppCompatActivity {

    static final String[] Menu_Items_Client =
            new String[]{"Create Job", "View My Jobs", "Manage Account", "Logout"};
    static final String[] Menu_Items_Professional =
            new String[]{"Find Jobs ", "View My Appointments", "Manage Account", "Logout"};
    public static int mScreenHeight;
    public static int mScreenWidth;
    private GridView mGridView;
    private int userId;
    private SessionManager mSessionManager;

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
        mScreenHeight = metrics.heightPixels;

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        String selectedValue = (String) parent.getItemAtPosition(position);
//        HandleClick(selectedValue);
//    }

    private void HandleClick(String selectedValue) {
        switch (selectedValue) {
            case "Create Job":
                navigateToActivity("CreateJob");
                break;
            case "View My Jobs":
                navigateToActivity("ViewJobsActivity");
                break;
            case "Manage Account":
                navigateToActivity("ManageAccount");
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

    private void navigateToActivity(String viewMyAppointments) {
        Intent intent = new Intent(this, ViewJobsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
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
