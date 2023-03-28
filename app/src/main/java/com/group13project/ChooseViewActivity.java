package com.group13project;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseViewActivity extends AppCompatActivity{

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_view_activity);
        setTitle("Choose View");

        Button employer_view=(Button) findViewById(R.id.employer_view);
        employer_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseViewActivity.this,EmployerHomeActivity.class));
            }
        });

        Button employee_view=(Button) findViewById(R.id.employee_view);
        employee_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseViewActivity.this,EmployeeHomeActivity.class));
            }
        });

        Button my_profile_view = (Button) findViewById(R.id.myProfile_view);

        my_profile_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseViewActivity.this,ProfilePage.class));
            }
        });
    }

}
