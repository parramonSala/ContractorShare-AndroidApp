package com.android.contractorshare.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.contractorshare.R;
import com.android.contractorshare.fragments.LoginFragment;
import com.android.contractorshare.fragments.RegisterFragment;
import com.android.contractorshare.fragments.ResetPasswordFragment;

public class AuthenticationActivity extends FragmentActivity implements LoginFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener, ResetPasswordFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO: Navigate to home activity if already logged.

        //Create login fragment by default
        if (savedInstanceState == null) {
            Fragment loginFragment = new LoginFragment();
            getFragmentManager().beginTransaction().add(R.id.fragmentContainer, loginFragment).addToBackStack(null).commit();
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
    public void onFragmentInteraction(String next) {
        Fragment fragment;
        //Handling Navigation
        if (next == "Register") {
            fragment = new RegisterFragment();
        } else if (next == "Login") {
            fragment = new LoginFragment();
        } else if (next == "ResetPassword") {
            fragment = new ResetPasswordFragment();
        } else {
            fragment = new LoginFragment();
        }

        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).addToBackStack(null).commit();
    }
}



