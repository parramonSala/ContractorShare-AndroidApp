package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.GenericResponse;
import com.android.contractorshare.models.JobTask;
import com.android.contractorshare.utils.DateHandler;
import com.android.contractorshare.utils.StatusHandler;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateEditTaskFragment extends Fragment {

    private final String API = "http://contractorshare.apphb.com/ContractorShare/";
    private JobTask mTask;
    private View mView;
    private String mFragmentType;
    private EditText mName;
    private EditText mDescription;
    private EditText mCreatedOn;
    private EditText mStatus;
    private ProgressDialog mProgressDialog;

    private OnListFragmentInteractionListener mListener;

    public static CreateEditTaskFragment newInstance(JobTask task, String fragmentType) {
        CreateEditTaskFragment fragment = new CreateEditTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("task", task);
        bundle.putString("fragmentType", fragmentType);
        fragment.setArguments(bundle);

        return fragment;
    }

    //TODO: Create task layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTask = getArguments().getParcelable("task");
        mFragmentType = getArguments().getString("fragmentType");
        mView = inflater.inflate(R.layout.fragment_task_edit_create, container, false);

        mName = (EditText) mView.findViewById(R.id.name);
        mDescription = (EditText) mView.findViewById(R.id.description);
        mCreatedOn = (EditText) mView.findViewById(R.id.createdOn);
        mStatus = (EditText) mView.findViewById(R.id.status);

        if (mFragmentType.equals("EditTask")) {

            mName.setText(mTask.getName());
            mDescription.setText(mTask.getDescription());
            mCreatedOn.setText(DateHandler.fromWCFToAndroidDateConverter(mTask.getCreated()));
            mStatus.setText(StatusHandler.getStatusText(mTask.getStatusId()));
        } else {
            mCreatedOn.setText(new Date().toString());
            mStatus.setText(StatusHandler.getStatusText(1));
        }

        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Updating task ...");

        Button mButton = (Button) mView.findViewById(R.id.update_task);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed();
            }
        });

        return mView;
    }

    public void onButtonPressed() {
//        if (mFragmentType.equals("CreateTask")) {
//            mTask.setName(mName.getText().toString());
//            mTask.setDescription(mDescription.getText().toString());
//            mTask.setCreated(DateHandler.fromAndroidtoWCFDateConverter(new Date()));
//            mTask.setStatusId(StatusHandler.getStatusInt(mStatus.getText().toString()));
//        }
        mTask.setName(mName.getText().toString());
        mTask.setDescription(mDescription.getText().toString());
        mTask.setCreated(DateHandler.fromAndroidtoWCFDateConverter(new Date()));
        mTask.setStatusId(StatusHandler.getStatusInt(mStatus.getText().toString()));

        UpdateCreateTask();
    }


    private void UpdateCreateTask() {
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);

        Call<GenericResponse> call;
        if (mFragmentType.equals("EditTask"))
            call = service.updateTask(mTask.getServiceId().toString(), mTask.getTaskId().toString(), mTask);
        else call = service.createTask(mTask.getServiceId().toString(), mTask);

        mProgressDialog.show();
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    NavigateToTaskDetails();
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

    private void NavigateToTaskDetails() {
        mListener.onListFragmentInteraction(mTask, "taskDetails");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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
