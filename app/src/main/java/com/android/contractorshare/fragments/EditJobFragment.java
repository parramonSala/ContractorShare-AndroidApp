package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
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
import com.android.contractorshare.models.Job;
import com.android.contractorshare.utils.StatusHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EditJobFragment extends Fragment {

    private final String API = "http://contractorshare.apphb.com/ContractorShare/";
    private OnListFragmentInteractionListener mListener;
    private Job mJob;
    private View mView;
    private EditText mStatus;
    private EditText mName;
    private EditText mCity;
    private EditText mAddress;
    private EditText mDescription;
    private Button mButton;

    public EditJobFragment() {
        // Required empty public constructor
    }

    public static EditJobFragment newInstance(Job job) {
        EditJobFragment fragment = new EditJobFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("job", job);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static EditJobFragment newInstance(String param1, String param2) {
        EditJobFragment fragment = new EditJobFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mJob = getArguments().getParcelable("job");
        mView = inflater.inflate(R.layout.fragment_edit_job, container, false);

        mName = (EditText) mView.findViewById(R.id.name);
        mName.setText(mJob.getName());

        mCity = (EditText) mView.findViewById(R.id.city);
        mCity.setText(mJob.getCity());

        mAddress = (EditText) mView.findViewById(R.id.address);
        mAddress.setText(mJob.getAddress());

        mDescription = (EditText) mView.findViewById(R.id.description);
        mDescription.setText(mJob.getDescription());

        mStatus = (EditText) mView.findViewById(R.id.status);
        mStatus.setText(StatusHandler.getStatusText(mJob.getStatusID()));

        mButton = (Button) mView.findViewById(R.id.update_job);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed();
            }
        });

        return mView;
    }

    public void onButtonPressed() {
        mJob.setName(mName.getText().toString());
        mJob.setCity(mCity.getText().toString());
        mJob.setAddress(mAddress.getText().toString());
        mJob.setDescription(mDescription.getText().toString());

        UpdateJob(mJob);
    }

    private void UpdateJob(Job mJob) {
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<GenericResponse> call = service.updateJob(String.valueOf(mJob.getId()), mJob);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    navigatetoViewJob();
                    Toast.makeText(getActivity(), "Job has been updated", Toast.LENGTH_SHORT);
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

    private void navigatetoViewJob() {
        mListener.onListFragmentInteraction(mJob, "jobDetails");
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Job job, String next);
    }
}
