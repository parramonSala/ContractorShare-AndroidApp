package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.adapters.MessageAdapter;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.GenericResponse;
import com.android.contractorshare.models.Message;
import com.android.contractorshare.models.Proposal;
import com.android.contractorshare.session.SessionManager;
import com.android.contractorshare.utils.DateHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MessageFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private ProgressDialog mProgressDialog;
    private int mProposalId;
    private ArrayList<Message> mProposalMessages;
    private ListView mListView;
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private MessageAdapter adapter;
    private int fromdUserId;
    private int toUserId;

    public static MessageFragment newInstance(int proposalId, int toUserId) {
        MessageFragment fragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("proposalId", proposalId);
        bundle.putInt("toUserId", toUserId);
        fragment.setArguments(bundle);

        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //TODO: Update with fragment_manage_proposals
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        mListView = (ListView) view.findViewById(R.id.messagesContainer);

        mProgressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);

        messagesContainer = (ListView) view.findViewById(R.id.messagesContainer);
        messageET = (EditText) view.findViewById(R.id.messageEdit);
        sendBtn = (Button) view.findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) view.findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) view.findViewById(R.id.friendLabel);
        RelativeLayout containeLayout = (RelativeLayout) view.findViewById(R.id.container);
        companionLabel.setText("Olga");// Hard Coded

        SessionManager sessionMmanager = new SessionManager(getActivity());
        HashMap<String, String> userDetails = sessionMmanager.getUserDetails();
        fromdUserId = Integer.parseInt(userDetails.get("userId"));
        Bundle b = getArguments();
        toUserId = b.getInt("toUserId");
        mProposalId = b.getInt("proposalId");

        initControls();

        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        // Set the adapter
        super.onActivityCreated(savedInstanceState);
        getProposalMessages();

    }

    private void initControls() {

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                addMessage(messageText);
            }
        });
    }

    private void addMessage(String messageText) {

        Message message = new Message();
        message.setMessage(messageText);
        message.setCreated(DateHandler.fromAndroidtoWCFDateConverter(new Date()));
        message.setFromUserId(fromdUserId);
        message.setToUserId(toUserId);
        message.setProposalId(mProposalId);

        messageET.setText(messageText);

        //Call api and add message.
        replyWebServiceCall(message);
        displayMessage(message, false);
    }

    private void replyWebServiceCall(final Message message) {
        mProgressDialog.setMessage("Sending Message...");
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<GenericResponse> call = service.reply(String.valueOf(mProposalId), message);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mProgressDialog.dismiss();
                    addMessage(message);
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

    public void addMessage(Message message) {
        displayMessage(message, true);
    }

    public void displayMessage(Message message, boolean addMessage) {
        if (addMessage) adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void getProposalMessages() {
        mProgressDialog.setMessage("Getting Messages...");
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<ArrayList<Message>> call = service.getMessages(String.valueOf(mProposalId));
        call.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mProgressDialog.dismiss();
                    mProposalMessages = response.body();
                    if (mProposalMessages.size() > 0) setupProposalMessages();
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getActivity(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getActivity(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void setupProposalMessages() {
        adapter = new MessageAdapter(getActivity(), mProposalMessages, fromdUserId);
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < mProposalMessages.size(); i++) {
            Message message = mProposalMessages.get(i);
            displayMessage(message, false);
        }
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