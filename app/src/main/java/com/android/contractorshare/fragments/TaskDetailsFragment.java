package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
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
    private ProgressDialog mProgressDialog;
    private Toolbar mToolbar;
    private ImageView mCommentImage;

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

        mCommentImage = (ImageView) mView.findViewById(R.id.message_image);
        mCommentImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToComments();
            }
        });

        Typeface font = TypeFaces.get(getActivity(), "fontawesome-webfont.ttf");

        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Updating task status...");

        return mView;
    }

    private void navigateToComments() {
        mListener.onListFragmentInteraction(mTask, "taskComments");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                handleClick("editTask");
                return true;

            case R.id.action_close:
                handleClick("deleteTask");
                return true;

            case R.id.action_complete:
                handleClick("completeTask");
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
                mListener.onListFragmentInteraction(mTask, "EditTask");
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
        Call<GenericResponse> call = service.updateTaskStatus(jobId, mTask.getTaskId().toString(), statusId);
        mProgressDialog.show();
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    int toStatus = Integer.parseInt(statusId);
                    mTask.setStatusId(toStatus);
                    mStatus.setText(StatusHandler.getStatusText(toStatus));
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
        mProgressDialog.dismiss();
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
