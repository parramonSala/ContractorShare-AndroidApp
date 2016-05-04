package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.contractorshare.R;
import com.android.contractorshare.models.Proposal;
import com.android.contractorshare.utils.DateHandler;
import com.android.contractorshare.utils.ProfileHandler;


public class ProposalDetailsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private Proposal mProposal;
    //    private ProgressDialog mProgressDialog;
//    private RecyclerView mGridView;
    private int mUserId;
    private TextView mFromUser;
    private TextView mDate;
    private TextView mDuration;
    private TextView mPrice;
    private TextView mMessage;
    private TextView mJobName;
    private ImageView mFromUserImage;


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

//        mProgressDialog = new ProgressDialog(getActivity(),
//        R.style.AppTheme_Dark_Dialog);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Loading Proposals...");

        mProposal = getArguments().getParcelable("proposal");
        mFromUser = (TextView) view.findViewById(R.id.name);
        mFromUser.setText(mProposal.getFromUsername());

        mDate = (TextView) view.findViewById(R.id.date);

        mDate.setText(DateHandler.dateConverter(mProposal.getProposedTime()));

        mDuration = (TextView) view.findViewById(R.id.duration);
        mDuration.setText(mProposal.getAproxDuration().toString());


        mPrice = (TextView) view.findViewById(R.id.price);
        mPrice.setText(mProposal.getProposedPrice().toString());


        mMessage = (TextView) view.findViewById(R.id.proposal_message);
        mMessage.setText(mProposal.getMessage());

        mJobName = (TextView) view.findViewById(R.id.job_name_label);
        mJobName.setText(mProposal.getJobName());

        mFromUserImage = (ImageView) view.findViewById(R.id.image);
        mFromUserImage.setImageDrawable(ProfileHandler.get(mProposal.getToUserId(), getActivity()));

        return view;
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


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Proposal proposal, String next);
    }
}
