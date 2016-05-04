package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.adapters.ProposalsAdapter;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.Proposal;
import com.android.contractorshare.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProposalsListFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private List<Proposal> mProposals;
    private ProgressDialog mProgressDialog;
    private RecyclerView mGridView;
    private int mUserId;

    public ProposalsListFragment() {

    }

    public static ProposalsListFragment newInstance() {
        ProposalsListFragment fragment = new ProposalsListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);

        return fragment;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //TODO: Update with fragment_manage_proposals
        View view = inflater.inflate(R.layout.fragment_proposal_list, container, false);

        mGridView = (RecyclerView) view.findViewById(R.id.gridview);
        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading Proposals...");
        Bundle bundle = this.getArguments();
        mUserId = bundle.getInt("userId", -1);
        //Get proposals, use array to send it to the adapter.
        GetProposals(mUserId);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        // Set the adapter
        super.onActivityCreated(savedInstanceState);
        //TODO: Save mjobs in session.
        if (mProposals == null) GetProposals(mUserId);
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

    private void GetProposals(int userId) {
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<ArrayList<Proposal>> call = service.getProposals(String.valueOf(mUserId));
        call.enqueue(new Callback<ArrayList<Proposal>>() {
            @Override
            public void onResponse(Call<ArrayList<Proposal>> call, Response<ArrayList<Proposal>> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mProposals = response.body();
                    mGridView.setAdapter(new ProposalsAdapter(mProposals, new ProposalsAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Proposal proposal) {
                            Log.v("oh", "Recycler view click");
                            navigateToProposalDetailsFragments(proposal);
                        }
                    }, getActivity()));
                    mGridView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                    mGridView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    if (mProposals.size() == 0) mTextView.setText(R.string.empty_text);
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getActivity(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
                mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<Proposal>> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getActivity(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void navigateToProposalDetailsFragments(Proposal proposal) {
        mListener.onListFragmentInteraction(proposal, "proposalDetails");
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
