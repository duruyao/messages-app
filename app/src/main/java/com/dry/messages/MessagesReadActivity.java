package com.dry.messages;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import top.gpg2.messages.R;

public class MessagesReadActivity extends AppCompatActivity {
    private String goalAddress;

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        goalAddress = intent.getStringExtra("addressKey");
        new MessagesDisplayer(this, goalAddress);
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
    }
}
