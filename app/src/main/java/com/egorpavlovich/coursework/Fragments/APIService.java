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
                    "Authorization:key=AAAABXzoZ2Q:APA91bHBlQ6VS-eSvq4uxrRHS78cKAEb8uLWQOvLLWtzgeQw1rcvgngxhzC9jrooqDKaX2m4P4feqQjpVBONNHmYOVTSWNCbh_Q2v_u8s9M5V_ZtKux-wGQrk7JELg-zv_YSlPErUD97"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
