package com.android.contractorshare.api;

import com.android.contractorshare.models.Email;
import com.android.contractorshare.models.GenericResponse;
import com.android.contractorshare.models.Job;
import com.android.contractorshare.models.Login;
import com.android.contractorshare.models.LoginResponse;
import com.android.contractorshare.models.Proposal;
import com.android.contractorshare.models.Register;
import com.android.contractorshare.models.ResetPasswordResponse;
import com.android.contractorshare.models.UpdateStatusInfo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FindMyHandyManAPI {

    @POST("sessions")
    Call<LoginResponse> Login(@Body Login login);

    @POST("users")
    Call<LoginResponse> Register(@Body Register register);

    @GET("users/{userId}/jobs")
    Call<ArrayList<Job>> getJobs(@Path("userId") String userId);

    @GET("users/{userId}/activeproposals")
    Call<ArrayList<Proposal>> getProposals(@Path("userId") String userId);

    @POST("resetPassword")
    Call<ResetPasswordResponse> ResetPassword(@Body Email email);

    @PUT("jobs/{jobId}/status/{statusId}")
    Call<GenericResponse> updateJobStatus(@Path("jobId") String jobId, @Path("statusId") String statusId);

    @PUT("jobs/{jobId}")
    Call<GenericResponse> updateJob(@Path("jobId") String jobId, @Body Job job);

    @PUT("proposals/{proposalId}/status")
    Call<GenericResponse> updateProposal(@Path("proposalId") String proposalId, @Body UpdateStatusInfo statusId);

}
