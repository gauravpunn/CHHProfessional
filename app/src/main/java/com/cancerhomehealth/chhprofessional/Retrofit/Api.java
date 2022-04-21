package com.cancerhomehealth.chhprofessional.Retrofit;

import com.cancerhomehealth.chhprofessional.ApiResponse.ChangePasswordResponse;
import com.cancerhomehealth.chhprofessional.ApiResponse.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @Headers({"Accept:application/json",
            "Content-Type:application/json",
            "AppVersion:1",
            "Language:EN",
            "DeviceType:Android"})
    @POST(ApiConstants.USER_LOGIN)
    Call<UserResponse> operationUserLogin(@Body Object request);

    @Headers({"Accept:application/json",
            "Content-Type:application/json",
            "AppVersion:1",
            "Language:EN",
            "DeviceType:Android"})
    @POST(ApiConstants.CHANGE_PASSWORD)
    Call<ChangePasswordResponse> operationChangePassword(@Body Object request);

}
