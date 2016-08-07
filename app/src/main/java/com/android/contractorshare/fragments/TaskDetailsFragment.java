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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.GenericResponse;
import com.android.contractorshare.models.JobTask;
import com.android.contractorshare.utils.DateHandler;
import com.android.contractorshare.utils.StatusHandler;
import com.android.contractorshare.utils.TypeFaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaskDetailsFragment extends Fragment {

    private final String API = "http://contractorshare.apphb.com/ContractorShare/";
    private final String closedStatus = "7";
    private final String completedStatus = "3";
    private final String cancelledStatus = "4";
    private JobTask mTask;
    private View mView;
    private OnListFragmentInteractionListener mListener;
    private TextView mStatus;
    private Toolbar mToolbar;

    public static TaskDetailsFragment newInstance(JobTask task) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        fragment.setArguments(bundle);

        return fragment;
    }

    //TODO: Create task layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask = getArguments().getParcelable("task");
        mView = inflater.inflate(R.layout.fragment_task_details, container, false);

        TextView name = (TextView) mView.findViewById(R.id.name);
        name.setText(mTask.getName());

        TextView description = (TextView) mView.findViewById(R.id.description);
        description.setText(mTask.getDescription());

        TextView createdOn = (TextView) mView.findViewById(R.id.createdOn);
        createdOn.setText(DateHandler.fromWCFToAndroidDateConverter(mTask.getCreated()));

        mStatus = (TextView) mView.findViewById(R.id.status);
        mStatus.setText(StatusHandler.getStatusText(mTask.getStatusId()));

//       mToolbar = (Toolbar) mView.findViewById(R.id.my_toolbar);
//       ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        Typeface font = TypeFaces.get(getActivity(), "fontawesome-webfont.ttf");
        TextView edit = (TextView) mView.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick("editTask");
            }
        });
        edit.setTypeface(font);
        TextView close = (TextView) mView.findViewById(R.id.close);
        close.setTypeface(font);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick("deleteTask");
            }
        });
        TextView complete = (TextView) mView.findViewById(R.id.complete);
        complete.setTypeface(font);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick("completeTask");
            }
        });

        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_task_details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void handleClick(String next) {
        switch (next) {
            case "editTask":
                mListener.onListFragmentInteraction(mTask, "editTask");
                break;
            case "completeTask":
                UpdateTaskStatus(mTask.getTaskId().toString(), completedStatus);
                break;
            case "deleteTask":
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to delete the task?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //Refresh activity
                                UpdateTaskStatus(mTask.getTaskId().toString(), cancelledStatus);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
        }
    }

    private void UpdateTaskStatus(String jobId, final String statusId) {
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<GenericResponse> call = service.updateTaskStatus(jobId, mTask.getTaskId().toString(), closedStatus);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    int toStatus = Integer.parseInt(statusId);
                    mTask.setStatusId(toStatus);
                    mStatus.setText(String.valueOf(toStatus));
                    Toast.makeText(getActivity(), "Task has been updated", Toast.LENGTH_SHORT);
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getActivity(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getActivity(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
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
        void onListFragmentInteraction(JobTask task, String next);
    }

}
