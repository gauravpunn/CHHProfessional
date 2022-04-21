package com.cancerhomehealth.chhprofessional.ApiResponse;

import com.cancerhomehealth.chhprofessional.Model.UserData;
import com.google.gson.annotations.SerializedName;

public class UserResponse {

    private int success;
    @SerializedName("data")
    private UserData userData;
    private String message;
    private int status;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
