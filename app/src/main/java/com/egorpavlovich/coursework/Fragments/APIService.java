package com.egorpavlovich.coursework.Fragments;

import com.egorpavlovich.coursework.Notifications.MyResponse;
import com.egorpavlovich.coursework.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AIzaSyAvSK2f2EPA-RbXeXJYqPC150VUJ4LdYZo"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
