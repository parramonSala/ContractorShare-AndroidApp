package com.android.contractorshare.api;

import com.android.contractorshare.models.Job;
import com.android.contractorshare.models.Login;
import com.android.contractorshare.models.LoginResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Roger on 06/02/2016.
 */
public interface FindMyHandyManAPI {
    @GET("users/{userId}/jobs")
    Call<ArrayList<Job>> getJobs(@Path("userId") String userId);

    @POST("sessions")
    Call<LoginResponse> Login(@Body Login login);
}