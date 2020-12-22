package com.egorpavlovich.coursework;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Gallery;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.egorpavlovich.coursework.Fragments.ChatsFragment;
import com.egorpavlovich.coursework.Fragments.ProfileFragment;
import com.egorpavlovich.coursework.Fragments.UsersFragment;
import com.egorpavlovich.coursework.Fragments.VideoChatFragment;
import com.egorpavlovich.coursework.Model.Chat;
import com.egorpavlovich.coursework.Model.User;
import com.egorpavlovich.coursework.video_chat.main.AppRTCMainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private androidx.appcompat.widget.Toolbar toolbar;
    private NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    TextView drawerFullName, drawerEMail;
    CircleImageView drawerImage;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private TextView mMessagesTextView;

    private void initializeCountDrawer(TextView textView) {
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(18);
        textView.setTextColor(getResources().getColor(R.color.Orange));
        textView.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        View navHeader = navigationView.getHeaderView(0);
        drawerImage = navHeader.findViewById(R.id.drawer_imageView);
        drawerFullName = navHeader.findViewById(R.id.drawer_full_name);
        drawerEMail = navHeader.findViewById(R.id.drawer_email);

        mMessagesTextView = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_messages));
        // метод для счетчиков
        initializeCountDrawer(mMessagesTextView);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                drawerFullName.setText(user.getFullName());
                drawerEMail.setText(user.geteMail());

                if (user.getImageURL().equals("default")){
                    drawerImage.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(drawerImage);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int unread = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(firebaseUser.getUid()) && !chat.isIsseen()){
                        unread++;
                    }
                }

                if (unread == 0){
                    mMessagesTextView.setText("");
                } else {
                    mMessagesTextView.setText(String.valueOf(unread));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // default
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new UsersFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case  R.id.menu_settings:
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.menu_profile: {
                toolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new ProfileFragment());
                fragmentTransaction.commit();
            }
            return true;
            case R.id.menu_users: {
                toolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new UsersFragment());
                fragmentTransaction.commit();
            }
            return true;
            case R.id.menu_messages: {
                toolbar.setBackgroundColor(Color.TRANSPARENT);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new ChatsFragment());
                fragmentTransaction.commit();
            }
            return true;
            case R.id.menu_video_chat: {
                toolbar.setBackgroundColor(Color.TRANSPARENT);
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new VideoChatFragment());
                fragmentTransaction.commit();
            }
            return true;
            case R.id.menu_logout: {
                toolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

}