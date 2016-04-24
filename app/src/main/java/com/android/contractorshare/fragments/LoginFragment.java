package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.activities.HomeActivity;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.Login;
import com.android.contractorshare.models.LoginResponse;
import com.android.contractorshare.session.SessionManager;
import com.android.contractorshare.utils.TypeFaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {

    private final String API_KEY = "http://contractorshare.apphb.com/ContractorShare/";
    public SessionManager mSessionManager;
    private View mView;
    private EditText mEmailView;
    private TextInputLayout mEmailLayout;
    private EditText mPasswordView;
    private TextInputLayout mPasswordLayout;
    private String mEmail;
    private TextView mResetPassword;
    private TextView mTitle;
    private OnFragmentInteractionListener mListener;
    private ProgressDialog mProgressDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSessionManager = new SessionManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_login, container, false);
        mEmailView = (EditText) mView.findViewById(R.id.email);
       /* populateAutoComplete();*/
        mEmailLayout = (TextInputLayout) mView.findViewById(R.id.email_input_layout);

        mPasswordView = (EditText) mView.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mPasswordLayout = (TextInputLayout) mView.findViewById(R.id.password_input_layout);


        mResetPassword = (TextView) mView.findViewById(R.id.forgotPassword);
        mResetPassword.setClickable(true);
        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onFragmentInteraction("ResetPassword");
                }
            }
        });

        Typeface font = TypeFaces.get(getActivity(), "Ruthie-Regular-OTF.otf");
        mTitle = (TextView) mView.findViewById(R.id.title);
        mTitle.setTypeface(font);


        Button mEmailRegisterButton = (Button) mView.findViewById(R.id.navigate_register_button);
        mEmailRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onFragmentInteraction("Register");
                }
            }
        });

        Button mEmailSignInButton = (Button) mView.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Signing In...");

        return mView;
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

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        mEmailLayout.setError(null);
        mPasswordLayout.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordLayout.setError(getString(R.string.error_invalid_password));
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailLayout.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailLayout.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mProgressDialog.show();
            performLogin(email, password);
        }

    }

    private void performLogin(String email, String password) {
        String API = API_KEY;
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Login login = new Login();
        login.setEmail(email);
        mEmail = email;
        login.setPassword(password);
        Call<LoginResponse> call = service.Login(login);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    try {
                        // Navigate to Home screen
                        int userId = loginResponse.getUserId();
                        if (userId != -1) {
                            navigateToHomeActivity(userId, loginResponse.getUserType(), mEmail);
                            Toast.makeText(getActivity(), "You have been successfully logged in!", Toast.LENGTH_LONG).show();
                            finishLogin(true, "");
                        } else
                            finishLogin(false, "User doesn't exist or incorrect password");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error Occured!", Toast.LENGTH_LONG).show();
                        finishLogin(false, e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    finishLogin(false, loginResponse.getError());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                finishLogin(false, "Connection failed. No response from server");
            }
        });
    }

    private void finishLogin(boolean success, String error) {
        mProgressDialog.dismiss();

        if (success) {
            getActivity().finish();
        } else {
            mPasswordLayout.setError(error);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void navigateToHomeActivity(int userId, int userTypeId, String email) {
        mSessionManager.createLoginSession(email, userId, userTypeId);
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("UserTypeId", userTypeId);
        intent.putExtra("UserId", userId);
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String activity);
    }
}
