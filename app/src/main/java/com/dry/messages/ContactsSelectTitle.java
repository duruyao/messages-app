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
 * A class that do sth for title layout of ContactsSelectActivity.
 *
 * @author DuRuyao
 * Create 19/03/10
 */
public class ContactsSelectTitle extends LinearLayout {

    private Button leftButton;
    private Button rightButton;
    private TextView centerTitle;

    public ContactsSelectTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        leftButton = (Button) findViewById(R.id.left_button);
        rightButton = (Button) findViewById(R.id.right_button);
        centerTitle = (TextView) findViewById(R.id.center_title);

        centerTitle.setText(R.string.contacts);
        rightButton.setText(R.string.cancel);

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "You cancel to select", Toast.LENGTH_SHORT).show();

                ((Activity) getContext()).finish();
            }
        });

    }
}
