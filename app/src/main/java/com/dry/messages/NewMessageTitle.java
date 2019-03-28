package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

    private Button leftButton;
    private Button rightButton;
    private TextView centerTitle;
    // final private String CENTER_TITLE_TEXT = "New Messages";
    // final private String LEFT_BUTTON_TEXT = "";
    // final private String RIGHT_BUTTON_TEXT = "Cancel";

    public NewMessageTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        leftButton = (Button) findViewById(R.id.left_button);
        rightButton = (Button) findViewById(R.id.right_button);
        centerTitle = (TextView) findViewById(R.id.center_title);

        centerTitle.setText(R.string.new_message);
        // leftButton.setText(LEFT_BUTTON_TEXT);
        rightButton.setText(R.string.cancel);

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });

    }
}
