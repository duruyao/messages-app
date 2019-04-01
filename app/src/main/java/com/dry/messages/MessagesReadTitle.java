package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import top.gpg2.messages.R;

/**
 * @author DuRuyao
 * Create 19/03/30
 */
public class MessagesReadTitle extends LinearLayout {
    private Button leftButton;
    private Button rightButton;
    private TextView centerTitle;

    public MessagesReadTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        leftButton = (Button) findViewById(R.id.left_button);
        rightButton = (Button) findViewById(R.id.right_button);
        centerTitle = (TextView) findViewById(R.id.center_title);

        centerTitle.setText(R.string.session);
        rightButton.setText(R.string.cancel);

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You cancel to read", Toast.LENGTH_SHORT).show();

                ((Activity) getContext()).finish();
            }
        });

    }
}
