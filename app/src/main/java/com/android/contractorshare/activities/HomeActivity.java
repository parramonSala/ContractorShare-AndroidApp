package com.android.contractorshare.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.adapters.MainMenuAdapter;
import com.android.contractorshare.session.SessionManager;


public class HomeActivity extends ListActivity {

    static final String[] Menu_Items_Client =
            new String[]{"Create Job", "View My Jobs", "Manage Account", "Logout"};
    static final String[] Menu_Items_Professional =
            new String[]{"Find Jobs ", "View My Appointments", "Manage Account", "Logout"};
    private ListView listView;
    private int userId;
    private SessionManager mSessionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSessionManager = new SessionManager(this);

        int userTypeId = getIntent().getExtras().getInt("UserTypeId");
        userId = getIntent().getExtras().getInt("UserId");
        listView = (ListView) findViewById(android.R.id.list);

        if (UserTypes.Client.getValue() == userTypeId) {
            setListAdapter(new MainMenuAdapter(this, Menu_Items_Client));
        } else {
            setListAdapter(new MainMenuAdapter(this, Menu_Items_Professional));
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        ListAdapter adapter = getListAdapter();
        if (adapter != null) {
            String selectedValue = (String) adapter.getItem(position);
            Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
            HandleClick(selectedValue);
        }
    }

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
        intent.putExtra("UserId", userId);
        startActivity(intent);
    }

    public enum UserTypes {
        Professional(1),
        Client(2);

        private int value;

        UserTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
