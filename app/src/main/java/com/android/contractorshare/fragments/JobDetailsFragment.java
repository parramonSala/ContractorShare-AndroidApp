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
import android.view.View;
import android.view.ViewGroup;
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


//       mToolbar = (Toolbar) mView.findViewById(R.id.my_toolbar);
//       ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        Typeface font = TypeFaces.get(getActivity(), "fontawesome-webfont.ttf");
        TextView edit = (TextView) mView.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick("edit");
            }
        });
        edit.setTypeface(font);
        TextView close = (TextView) mView.findViewById(R.id.close);
        close.setTypeface(font);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick("close");
            }
        });

        return mView;
    }

    private void handleClick(String next) {
        switch (next) {
            case "edit":
                mListener.onListFragmentInteraction(mJob, "editJob");
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