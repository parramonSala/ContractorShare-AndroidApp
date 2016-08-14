package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.GenericResponse;
import com.android.contractorshare.models.Job;
import com.android.contractorshare.utils.StatusHandler;
import com.android.contractorshare.utils.TypeFaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobDetailsFragment extends Fragment {

    private final String API = "http://contractorshare.apphb.com/ContractorShare/";
    private final String closedStatus = "7";
    private Job mJob;
    private View mView;
    private OnListFragmentInteractionListener mListener;
    private TextView mStatus;
    private Toolbar mToolbar;
    private ImageView mCommentImage;

    public static JobDetailsFragment newInstance(Job job) {
        JobDetailsFragment fragment = new JobDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("job", job);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJob = getArguments().getParcelable("job");
        mView = inflater.inflate(R.layout.fragment_job_details, container, false);

        TextView name = (TextView) mView.findViewById(R.id.name);
        name.setText(mJob.getName());

        TextView city = (TextView) mView.findViewById(R.id.city);
        city.setText(mJob.getCity());

        TextView address = (TextView) mView.findViewById(R.id.address);
        address.setText(mJob.getAddress());

        TextView description = (TextView) mView.findViewById(R.id.description);
        description.setText(mJob.getDescription());

        mStatus = (TextView) mView.findViewById(R.id.status);
        mStatus.setText(StatusHandler.getStatusText(mJob.getStatusID()));

        mCommentImage = (ImageView) mView.findViewById(R.id.message_image);
        mCommentImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToComments();
            }
        });

        Typeface font = TypeFaces.get(getActivity(), "fontawesome-webfont.ttf");

        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_job_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                handleClick("edit");
                return true;

            case R.id.action_close:
                handleClick("close");
                return true;

            case R.id.action_tasks:
                handleClick("tasks");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void navigateToComments() {
        mListener.onListFragmentInteraction(mJob, "JobComments");
    }

    private void handleClick(String next) {
        switch (next) {
            case "edit":
                mListener.onListFragmentInteraction(mJob, "editJob");
                break;
            case "tasks":
                mListener.onListFragmentInteraction(mJob, "jobTasks");
                break;
            case "close":
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to close the job?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Refresh activity
                                CloseJob(mJob.getId().toString());
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
        }
    }

    private void CloseJob(String jobId) {
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<GenericResponse> call = service.updateJobStatus(jobId, closedStatus);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    int toStatus = Integer.parseInt(closedStatus);
                    mJob.setStatusID(toStatus);
                    mStatus.setText(String.valueOf(toStatus));
                    Toast.makeText(getContext(), "Job has been closed", Toast.LENGTH_SHORT);
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getContext(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getContext(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

//    public void onActivityCreated (Context context) {
//        mToolbar.setSubtitle("View Job");
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Job job, String next);
    }

}