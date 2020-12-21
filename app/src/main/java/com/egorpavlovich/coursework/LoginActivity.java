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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView email, password;
    private ProgressBar progressBar;
    Button btn_login;
    private TextView signUpBtn;

    FirebaseAuth auth;
    //TextView forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Login");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.e_mail_sign_in);
        password = findViewById(R.id.password_sign_in);
        btn_login = findViewById(R.id.sign_in_button);
        signUpBtn = findViewById(R.id.link_sign_up);
        progressBar = findViewById(R.id.sign_in__progress_bar);

        String tag_email = getIntent().getStringExtra("#e-mail");
        String tag_password = getIntent().getStringExtra("#password");

        email.setText(tag_email);
        password.setText(tag_password);

        //forgot_password = findViewById(R.id.forgot_password);

//        forgot_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
//            }
//        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (v.getId() == R.id.link_sign_up) {
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    intent.putExtra("#e-mail", email.getText().toString().trim());
                    intent.putExtra("#password", password.getText().toString().trim());
                    startActivity(intent);
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString().trim();
                String txt_password = password.getText().toString().trim();

                if(TextUtils.isEmpty(txt_email)){
                    email.setError("Поле 'E-mail' не должно быть пустым!");
                    return;
                }

                if(!txt_email.contains("@")){
                    email.setError("Поле 'E-mail' должно содержать знак '@'!");
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

                progressBar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this,"Авторизация прошла успешно!",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Не удалось выполнить авторизацию!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}