package com.android.contractorshare.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.adapters.TaskAdapter;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.JobTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskListFragment extends Fragment {

    private static final String userId = "userId";
    private final String API = "http://contractorshare.apphb.com/ContractorShare/";
    private OnListFragmentInteractionListener mListener;
    private ArrayList<JobTask> mtasks;
    private ListView mListView;
    private TextView mTextView;
    private ProgressDialog mProgressDialog;
    private int mJobId;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TaskListFragment() {
    }

    public static TaskListFragment newInstance(int jobId) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("jobId", jobId);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Pass clientId from Activity
        Bundle bundle = this.getArguments();
        mJobId = bundle.getInt("jobId", -1);
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                handleClick("createTask");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void handleClick(String createTask) {
        switch (createTask) {
            case "createTask":
                JobTask task = new JobTask();
                task.setServiceId(mJobId);
                mListener.onListFragmentInteraction(task, "CreateTask");
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_task_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        mListView = (ListView) view.findViewById(android.R.id.list);
        mListener = (OnListFragmentInteractionListener) getActivity();
        mTextView = (TextView) view.findViewById(android.R.id.empty);
        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading Tasks...");
        GetJobTasks(mJobId);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                JobTask task = mtasks.get(position);
                mListener.onListFragmentInteraction(task, "taskDetails");
            }
        });
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // Set the adapter
        super.onActivityCreated(savedInstanceState);
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

    private void GetJobTasks(int jobTasks) {
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<ArrayList<JobTask>> call = service.getJobTasks(String.valueOf(mJobId));
        call.enqueue(new Callback<ArrayList<JobTask>>() {
            @Override
            public void onResponse(Call<ArrayList<JobTask>> call, Response<ArrayList<JobTask>> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mtasks = response.body();
                    mListView.setAdapter(new TaskAdapter(getActivity(), mtasks));
                    if (mtasks.size() == 0) mTextView.setText(R.string.empty_text);
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getActivity(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<JobTask>> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getActivity(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    //TODO: Afegir options a dalt, amb una opcio: crear una task.

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(JobTask task, String next);
    }
}