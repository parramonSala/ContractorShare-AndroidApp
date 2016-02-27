package com.android.contractorshare.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.Email;
import com.android.contractorshare.models.ResetPasswordResponse;
import com.android.contractorshare.utils.TypeFaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResetPasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResetPasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordFragment extends Fragment {

    private final String API_KEY = "http://contractorshare.apphb.com/ContractorShare/";
    private EditText mEmailView;
    private View mView;
    private View mProgressView;
    private View mLoginFormView;
    private OnFragmentInteractionListener mListener;
    private TextView mTitle;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ResetPasswordFragment newInstance(String param1, String param2) {
        ResetPasswordFragment fragment = new ResetPasswordFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reset_password, container, false);
        mEmailView = (EditText) mView.findViewById(R.id.email);

        Button mResetPasswordButton = (Button) mView.findViewById(R.id.reset_password_button);
        mResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptResetPassword();
            }
        });

        mLoginFormView = mView.findViewById(R.id.login_form);
        mProgressView = mView.findViewById(R.id.login_progress);

        Typeface font = TypeFaces.get(getActivity(), "Ruthie-Regular-OTF.otf");
        mTitle = (TextView) mView.findViewById(R.id.title);
        mTitle.setTypeface(font);

        return mView;
    }

    private void attemptResetPassword() {
        // Reset errors.
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            performResetPassword(email);
        }
    }

    private void performResetPassword(String email) {
        String API = API_KEY;
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Email wrapper = new Email();
        wrapper.setEmail(email);
        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Call<ResetPasswordResponse> call = service.ResetPassword(wrapper);
        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                ResetPasswordResponse resetPasswordResponse = response.body();
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    try {
                        Toast.makeText(getActivity(), "You password has been reset successfully!", Toast.LENGTH_LONG).show();
                        // Navigate to Home screen
                        finishResetPassword(true, "");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error Occured!", Toast.LENGTH_LONG).show();
                        finishResetPassword(false, e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    finishResetPassword(false, resetPasswordResponse.getResult());
                }

            }

            @Override
            public void onFailure(Call<ResetPasswordResponse> call, Throwable t) {
                finishResetPassword(false, "Connection failed. No response from server");
            }
        });
    }

    private void finishResetPassword(boolean success, String error) {
        showProgress(false);
        if (success) mListener.onFragmentInteraction("Login");
        else {
            mEmailView.setError(error);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String next) {
        if (mListener != null) {
            mListener.onFragmentInteraction(next);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnFragmentInteractionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String next);
    }
}
