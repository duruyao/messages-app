package com.dry.messages;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import top.gpg2.messages.R;

import java.util.Set;

public class MessagesReadActivity extends AppCompatActivity {
    private String goalAddress;
    private ImageButton leftButton;
    private TextView centerTitle;
    private Cursor cursor = null;
    private String contactsName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_read);

        centerTitle = (TextView) findViewById(R.id.center_title);
        leftButton = (ImageButton) findViewById(R.id.left_button);

        /* Set color of StatusBar and color of font. */
        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.WhiteSmoke));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        goalAddress = intent.getStringExtra("ADDRESS").replaceAll("\\s", "");;
        contactsName = getContactsName(goalAddress);
        centerTitle.setText(contactsName);
        leftButton.setOnClickListener(mOnClickListener);
        new MessagesDisplayer(this, goalAddress);
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_button:
                    // Toast.makeText(v.getContext(), "You cancel to read", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case R.id.right_button:
                    break;
                default:
                    break;
            }
        }
    };

    public void setStatueTextColor(boolean setGray) {
        if (setGray) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(0);
        }
    }

    private String getContactsName(String address) {
        String name = address;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    if (address.equals(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s", "")))
                        name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return name;
    }
}
