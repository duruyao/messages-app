package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import top.gpg2.messages.R;

/**
 * @author DuRuyao
 * Create 19/03/30
 */
public class MessagesReadTitle extends LinearLayout {
    private ImageButton leftButton;
    private ImageButton rightButton;
    private TextView centerTitle;

    public MessagesReadTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        leftButton = (ImageButton) findViewById(R.id.left_button);
        rightButton = (ImageButton) findViewById(R.id.right_button);
//        centerTitle = (TextView) findViewById(R.id.center_title);

//        centerTitle.setText(R.string.session);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You cancel to read", Toast.LENGTH_SHORT).show();

                ((Activity) getContext()).finish();
            }
        });

    }
}
