package com.egorpavlovich.coursework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextView fullName, dateOfBirth, eMail, password;
    Spinner gender;
    Button btn_register;
    private TextView signInBtn;
    private ProgressBar progressBar;

    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.full_name);
        gender = findViewById(R.id.gender);
        dateOfBirth = findViewById(R.id.date_of_birth);
        eMail = findViewById(R.id.e_mail);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.create_account_progress_bar);
        btn_register = findViewById(R.id.create_account_button);
        signInBtn = findViewById(R.id.link_sign_in);

        String tag_email = getIntent().getStringExtra("#e-mail");
        String tag_password = getIntent().getStringExtra("#password");

        eMail.setText(tag_email);
        password.setText(tag_password);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (v.getId() == R.id.link_sign_in) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("#e-mail", eMail.getText().toString().trim());
                    intent.putExtra("#password", password.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_fullName = fullName.getText().toString().trim();
                String txt_gender_selected = gender.getSelectedItem().toString();
                String txt_dateOfBirth  = dateOfBirth.getText().toString().trim();
                String txt_eMail = eMail.getText().toString().trim();
                String txt_password = password.getText().toString().trim();

                if(TextUtils.isEmpty(txt_eMail)){
                    eMail.setError("Поле 'E-mail' не должно быть пустым!");
                    return;
                }

                if(!txt_eMail.contains("@")){
                    eMail.setError("Поле 'E-mail' должно содержать знак '@'!");
                    return;
                }

                if(TextUtils.isEmpty(txt_password)){
                    password.setError("Поле 'Пароль' не должно быть пустым!");
                    return;
                }

                if(txt_password.length() < 6){
                    password.setError("Пароль должен содержать минимум 6 символов!");
                    return;
                }

                if(txt_dateOfBirth.length() != 10){
                    dateOfBirth.setError("Дата рождения должна содержать строго 10 символов!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth = FirebaseAuth.getInstance();

                register(txt_fullName, txt_gender_selected, txt_dateOfBirth, txt_eMail, txt_password);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void register(final String fullName, String gender, String dateOfBirth, String eMail, String password){

        auth.createUserWithEmailAndPassword(eMail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("fullName", fullName);
                            hashMap.put("gender", gender);
                            hashMap.put("dateOfBirth", dateOfBirth);
                            hashMap.put("eMail", eMail);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", fullName.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Вы не можете зарегистрировать этот адрес электронной почты или пароль!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}