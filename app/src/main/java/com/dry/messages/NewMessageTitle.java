package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import top.gpg2.messages.R;

/**
 * A class that do sth for title layout of NewMessageActivity.
 *
 * @author DuRuyao
 * Create 19/3/10
 */
public class NewMessageTitle extends LinearLayout {

    private ImageButton leftButton;
    private ImageButton rightButton;
    private TextView centerTitle;

    public NewMessageTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        leftButton = (ImageButton) findViewById(R.id.left_button);
        rightButton = (ImageButton) findViewById(R.id.right_button);
        centerTitle = (TextView) findViewById(R.id.center_title);

        centerTitle.setText(R.string.new_message);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

    }
}
