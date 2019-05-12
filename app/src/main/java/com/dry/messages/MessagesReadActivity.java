package com.dry.messages;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import top.gpg2.messages.R;

public class MessagesReadActivity extends AppCompatActivity {
    private String goalAddress;
    private ImageButton leftButton;
    private TextView centerTitle;

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        goalAddress = intent.getStringExtra("addressKey");
        new MessagesDisplayer(this, goalAddress);

        centerTitle.setText(goalAddress);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You cancel to read", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.messages_read);

        /* Hide default title. */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        centerTitle = (TextView) findViewById(R.id.center_title);
        leftButton = (ImageButton) findViewById(R.id.left_button);

        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.WhiteSmoke));
        }

    }

    public void setStatueTextColor(boolean isGray) {
        if (isGray) {
            // 灰色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 白色
            getWindow().getDecorView().setSystemUiVisibility(0);
        }
    }
}
