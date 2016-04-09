package com.android.contractorshare.fragments;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.adapters.JobsAdapter;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.Job;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobListFragment extends ListFragment {

    // TODO: Customize parameter argument names
    private static final String userId = "userId";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Job> mjobs;
    private ListView mListView;
    private TextView mTextView;
    private ProgressDialog mProgressDialog;

    private int mUserId;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Pass clientId from Activity
        Bundle bundle = this.getArguments();
        mUserId = bundle.getInt("userId", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_list, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        mListener = (OnListFragmentInteractionListener) getActivity();
        mTextView = (TextView) view.findViewById(android.R.id.empty);
        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading Jobs...");
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // Set the adapter
        super.onActivityCreated(savedInstanceState);
        //TODO: Save mjobs in session.
        if (mjobs == null) GetClientJobs(mUserId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    private void GetClientJobs(int clientId) {
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<ArrayList<Job>> call = service.getJobs(String.valueOf(mUserId));
        call.enqueue(new Callback<ArrayList<Job>>() {
            @Override
            public void onResponse(Call<ArrayList<Job>> call, Response<ArrayList<Job>> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mjobs = response.body();
                    mListView.setAdapter(new JobsAdapter(getActivity(), mjobs));
                    if (mjobs.size() == 0) mTextView.setText(R.string.empty_text);
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getActivity(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Job>> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getActivity(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        Job job = mjobs.get(position);
        mListener.onListFragmentInteraction(job, "jobDetails");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Job job, String next);
    }
}
