package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.GenericResponse;
import com.android.contractorshare.models.Proposal;
import com.android.contractorshare.models.UpdateStatusInfo;
import com.android.contractorshare.utils.CalendarHandler;
import com.android.contractorshare.utils.DateHandler;
import com.android.contractorshare.utils.ProfileHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProposalDetailsFragment extends Fragment {

    private static final int ACCEPT_PROSOSAL = 5;
    private static final int REJCET_PROPOSAL = 6;
    private OnListFragmentInteractionListener mListener;
    private Proposal mProposal;
    private int mUserId;
    private TextView mFromUser;
    private TextView mDate;
    private TextView mDuration;
    private TextView mPrice;
    private TextView mMessage;
    private TextView mJobName;
    private ImageView mFromUserImage;
    private ImageView mAcceptProposal;
    private ImageView mRejectProposal;
    private ImageView mProposalMessages;
    private FragmentTabHost mTabHost;
    private ProgressDialog mProgressDialog;

    public static ProposalDetailsFragment newInstance(Proposal proposal) {
        ProposalDetailsFragment fragment = new ProposalDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("proposal", proposal);
        fragment.setArguments(bundle);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //TODO: Update with fragment_manage_proposals
        View view = inflater.inflate(R.layout.fragment_proposal_details, container, false);

        mProgressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);

        mProposal = getArguments().getParcelable("proposal");
        mFromUser = (TextView) view.findViewById(R.id.name);
        mFromUser.setText(mProposal.getFromUsername());

        mDate = (TextView) view.findViewById(R.id.date);

        mDate.setText(DateHandler.fromWCFToAndroidDateConverter(mProposal.getProposedTime()));

        mDuration = (TextView) view.findViewById(R.id.duration);
        mDuration.setText(mProposal.getAproxDuration().toString());


        mPrice = (TextView) view.findViewById(R.id.price);
        mPrice.setText(mProposal.getProposedPrice().toString());


        mMessage = (TextView) view.findViewById(R.id.proposal_message);
        mMessage.setText(mProposal.getMessage());

        mJobName = (TextView) view.findViewById(R.id.job_name_label);
        mJobName.setText(mProposal.getJobName());

        mFromUserImage = (ImageView) view.findViewById(R.id.image);
        mFromUserImage.setImageDrawable(ProfileHandler.get(mProposal.getFromUserId(), getActivity()));

        mAcceptProposal = (ImageView) view.findViewById(R.id.accept_image);
        mAcceptProposal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AcceptProposal(ACCEPT_PROSOSAL);
            }
        });

        mRejectProposal = (ImageView) view.findViewById(R.id.reject_image);
        mRejectProposal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RejectProposal(REJCET_PROPOSAL);
            }
        });

        mProposalMessages = (ImageView) view.findViewById(R.id.message_image);
        mProposalMessages.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToProposalMessages();
            }
        });

//        mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
//        mTabHost.setup();
//
//        mTabHost.addTab(mTabHost.newTabSpec("proposal details").setIndicator("Proposal Details"), ProposalDetailsFragment.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("messages").setIndicator("Proposal Messages"), MessageFragment.class, null);

        return view;
    }

    private void AcceptProposal(int statusId) {
        mProgressDialog.setMessage("Accepting Proposal...");
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UpdateStatusInfo statusInfo = new UpdateStatusInfo();
        statusInfo.setStatusId(statusId);
        statusInfo.setUpdatedByUserId(mProposal.getToUserId());
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<GenericResponse> call = service.updateProposal(String.valueOf(mProposal.getProposalId()), statusInfo);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    String result = response.body().getMessage();
                    if (result.equals("OK")) {
                        createAppointmentGoogleCalendar();
                        navigatetoViewAppointment();
                    }

                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getActivity(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getActivity(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void createAppointmentGoogleCalendar() {
        Intent addEventIntent = CalendarHandler.createEvent(getActivity());
        startActivity(addEventIntent);
    }

    private void navigatetoViewAppointment() {

    }

    private void RejectProposal(int statusId) {
        mProgressDialog.setMessage("Rejecting Proposal...");
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UpdateStatusInfo statusInfo = new UpdateStatusInfo();
        statusInfo.setStatusId(statusId);
        statusInfo.setUpdatedByUserId(mProposal.getToUserId());
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<GenericResponse> call = service.updateProposal(String.valueOf(mProposal.getProposalId()), statusInfo);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    String result = response.body().getMessage();
                    if (result.equals("OK")) {
                        Toast.makeText(getActivity(), "Proposal has been succesfully rejected", Toast.LENGTH_SHORT);
                        navigateToProposalList();
                    }

                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getActivity(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getActivity(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void navigateToProposalList() {
        mListener.onListFragmentInteraction(null, "proposalList");
    }

    private void navigateToProposalMessages() {
        mListener.onListFragmentInteraction(mProposal, "proposalMessages");
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // Set the adapter
        super.onActivityCreated(savedInstanceState);
        //TODO: Save mjobs in session.
//        if (mProposals == null) GetProposals(mUserId);
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

    public void onBackPressed() {
        getFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Proposal proposal, String next);
    }
}
