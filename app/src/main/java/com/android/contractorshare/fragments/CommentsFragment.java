package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.android.contractorshare.adapters.CommentAdapter;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.Comment;
import com.android.contractorshare.models.GenericResponse;
import com.android.contractorshare.models.Job;
import com.android.contractorshare.models.JobTask;
import com.android.contractorshare.session.SessionManager;
import com.android.contractorshare.utils.DateHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CommentsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private ProgressDialog mProgressDialog;
    private int mTaskId;
    private int mJobId;
    private int mUserId;
    private ArrayList<Comment> mComments;
    private ListView mListView;
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private Button uploadImageBtn;
    private CommentAdapter adapter;
    private int PICK_IMAGE_REQUEST = 1;


    public static CommentsFragment newInstance(int taskId, int jobId) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("taskId", taskId);
        bundle.putInt("jobId", jobId);
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
        uploadImageBtn = (Button) view.findViewById(R.id.chatUploadImage);

        TextView meLabel = (TextView) view.findViewById(R.id.meLbl);
        RelativeLayout containeLayout = (RelativeLayout) view.findViewById(R.id.container);

        SessionManager sessionMmanager = new SessionManager(getActivity());
        HashMap<String, String> userDetails = sessionMmanager.getUserDetails();
        mUserId = Integer.parseInt(userDetails.get("userId"));
        Bundle b = getArguments();
        mTaskId = b.getInt("taskId");
        mJobId = b.getInt("jobId");
        mComments = new ArrayList<Comment>();

        initControls();

        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        // Set the adapter
        super.onActivityCreated(savedInstanceState);
        getComments();
    }

    private void initControls() {

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                addComment(messageText, null);
            }
        });

        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickpUpGalleryImage();
            }
        });


    }

    private void pickpUpGalleryImage() {

        Intent intent = new Intent();
        // Show only images, no videos or anything else
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // Always show the chooser (if there are multiple options available)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                addComment("", scaled);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addComment(String messageText, Bitmap bitmap) {

        Comment comment = new Comment();
        comment.setMessage(messageText);
        comment.setCreated(DateHandler.fromAndroidtoWCFDateConverter(new Date()));
        comment.setCreatedByUserId(mUserId);

        if (mTaskId != -1) comment.setTaskId(mTaskId);
        if (mJobId != -1) comment.setJobId(mJobId);

        messageET.setText(messageText);

        //Call api and add the comment.
        postComment(comment);
        if (bitmap != null) {
            comment.setBitmap(bitmap);
            comment.setImage("custom");
        }
        displayComment(comment, false);
    }

    private void postComment(final Comment comment) {
        mProgressDialog.setMessage("Sending Comment...");
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<GenericResponse> call = service.addComment(String.valueOf(mJobId), comment);
        call.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mProgressDialog.dismiss();
                    addComment(comment);
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

    public void addComment(Comment comment) {
        //If we are showing all job comments, or if the comments is related to the task comments we are currently showng
        if (mTaskId == -1 || comment.getTaskId() == mTaskId) displayComment(comment, true);
    }

    public void displayComment(Comment comment, boolean addComment) {
        if (addComment) adapter.add(comment);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void getComments() {
        mProgressDialog.setMessage("Getting Messages...");
        mProgressDialog.show();
        String API = "http://contractorshare.apphb.com/ContractorShare/";
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<ArrayList<Comment>> call = service.getJobComments(String.valueOf(mJobId));
        call.enqueue(new Callback<ArrayList<Comment>>() {
            @Override
            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    mProgressDialog.dismiss();
                    setupComments(response.body());
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    Toast.makeText(getActivity(), "There was an error: " + response.message(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                //TODO: There is an error
                Toast.makeText(getActivity(), "There was an error: " + t.toString(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void setupComments(ArrayList<Comment> comments) {
        DiscardOtherTaskComments(comments);
        adapter = new CommentAdapter(getActivity(), mComments, mUserId);
        messagesContainer.setAdapter(adapter);

    }

    private void DiscardOtherTaskComments(ArrayList<Comment> comments) {
        Integer commentTaskId;
        if (mTaskId != -1) {
            for (int j = 0; j < comments.size(); j++) {
                commentTaskId = comments.get(j).getTaskId();
                if (commentTaskId != null && commentTaskId == mTaskId) {
                    mComments.add(comments.get(j));
                }
            }
        } else {
            mComments = comments;
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
        void onListFragmentInteraction(Job job, String next);

        void onListFragmentInteraction(JobTask task, String next);
    }


}