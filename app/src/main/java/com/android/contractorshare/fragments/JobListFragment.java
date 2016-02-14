package com.android.contractorshare.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class JobListFragment extends ListFragment {

    // TODO: Customize parameter argument names
    private static final String userId = "userId";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Job> mjobs;
    private ListView mListView;

    private int mUserId;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobListFragment() {
    }

    @SuppressWarnings("unused")
    public static JobListFragment newInstance() {
        JobListFragment fragment = new JobListFragment();
        return fragment;
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
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<ArrayList<Job>> call = service.getJobs("36");
        call.enqueue(new Callback<ArrayList<Job>>() {
            @Override
            public void onResponse(Call<ArrayList<Job>> call, Response<ArrayList<Job>> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mjobs = response.body();
                    mListView.setAdapter(new JobsAdapter(getActivity(), mjobs));

                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    //TODO: Implement error handling.
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Job>> call, Throwable t) {
                //TODO: There is an error
            }
        });
    }

    @Override
    //TODO: Replace current fragment with details fragment to display clicked job.
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        Toast.makeText(getActivity(), "Item " + position + " was clicked", Toast.LENGTH_SHORT).show();
        Job job = mjobs.get(position);
        mListener.onListFragmentInteraction(job);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Job job);
    }
}
