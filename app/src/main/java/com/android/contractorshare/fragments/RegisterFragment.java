package com.android.contractorshare.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.contractorshare.R;
import com.android.contractorshare.api.FindMyHandyManAPI;
import com.android.contractorshare.models.LoginResponse;
import com.android.contractorshare.models.Register;
import com.android.contractorshare.utils.TypeFaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {

    private final String API_KEY = "http://contractorshare.apphb.com/ContractorShare/";
    private final String LOGIN_KEY = "Login";
    private final String[] muserTypeDropDown = new String[]{"Professional", "Client"};
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private View mProgressView;
    private View mRegisterFormView;
    private View mView;
    private OnFragmentInteractionListener mListener;
    private Spinner mUserTypeDropdown;
    private TextView mTitle;
    private ProgressDialog mProgressDialog;

    public RegisterFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_register, container, false);

        // Set up the register form.
        mEmailView = (AutoCompleteTextView) mView.findViewById(R.id.email);

        mPasswordView = (EditText) mView.findViewById(R.id.password);
        mConfirmPasswordView = (EditText) mView.findViewById(R.id.confirmPassword);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        mUserTypeDropdown = (Spinner) mView.findViewById(R.id.userTypeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, muserTypeDropDown);
        mUserTypeDropdown.setAdapter(adapter);

        Button mEmailRegisterButton = (Button) mView.findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mRegisterFormView = mView.findViewById(R.id.email_register_form);
        mProgressView = mView.findViewById(R.id.login_progress);

        Typeface font = TypeFaces.get(getActivity(), "Ruthie-Regular-OTF.otf");
        mTitle = (TextView) mView.findViewById(R.id.title);
        mTitle.setTypeface(font);

        mProgressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Register user...");
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

    public void attemptRegister() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        // Store values at the time of the register attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();
        int userTypeId = mUserTypeDropdown.getSelectedItemPosition();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(confirmPassword) && !isPasswordValid(confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

        if (!confirmPassword.equals(password)) {
            mConfirmPasswordView.setError(getString(R.string.error_passwords_dont_match));
            focusView = mConfirmPasswordView;
            cancel = true;
        }

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
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mProgressDialog.show();
            performRegister(email, password, userTypeId);
        }
    }

    private void performRegister(String email, String password, int userTypeId) {
        Retrofit Client = new Retrofit.Builder()
                .baseUrl(API_KEY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyHandyManAPI service = Client.create(FindMyHandyManAPI.class);
        Register register = new Register();
        register.setEmail(email);
        register.setPassword(password);
        register.setUserType(userTypeId);
        Call<LoginResponse> call = service.Register(register);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse registerResponse = response.body();
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    try {
                        Toast.makeText(getActivity(), "You have been successfully logged in!", Toast.LENGTH_LONG).show();
                        // Navigate to Home screen
                        navigateToLoginActivity();
                        finishRegister(true, "");
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error Occured!", Toast.LENGTH_LONG).show();
                        finishRegister(false, e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    //request not successful (like 400,401,403 etc)
                    //Handle errors
                    finishRegister(false, registerResponse.getError());
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                finishRegister(false, "Connection failed. No response from server");
            }
        });
    }

    private void navigateToLoginActivity() {
        if (mListener != null) {
            mListener.onFragmentInteraction(LOGIN_KEY);
        }
    }

    private void finishRegister(boolean success, String error) {
        mProgressDialog.dismiss();

        if (success) {
            getActivity().finish();
        } else {
            mPasswordView.setError(error);
            mPasswordView.requestFocus();
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    public enum UserTypes {
        Client(1),
        Professional(2);

        private int value;

        UserTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String activity);
    }
}
