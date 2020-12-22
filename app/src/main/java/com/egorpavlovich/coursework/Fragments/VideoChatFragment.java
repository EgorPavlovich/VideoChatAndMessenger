package com.egorpavlovich.coursework.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import android.app.Fragment;//import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.egorpavlovich.coursework.MainActivity;
import com.egorpavlovich.coursework.R;
import com.egorpavlovich.coursework.StartActivity;
import com.egorpavlovich.coursework.video_chat.main.AppRTCMainActivity;

public class VideoChatFragment extends Fragment {

    Button GetStartedButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_chat, container, false);
        GetStartedButton = view.findViewById(R.id.getstarted_button);
        GetStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AppRTCMainActivity.class));
            }
        });
        return view;
    }
}