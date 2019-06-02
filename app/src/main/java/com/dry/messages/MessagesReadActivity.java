package com.dry.messages;

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import top.gpg2.messages.R;

public class MessagesReadActivity extends AppCompatActivity {
    private final String SMS_URI_ALL = "content://sms/";
    private final String SMS_URI_SENT = "content://sms/sent";
    private final String SMS_URI_INBOX = "content://sms/inbox";

    private Uri uri = Uri.parse(SMS_URI_ALL);
    private Uri uri_sent = Uri.parse(SMS_URI_SENT);
    private Uri uri_inbox = Uri.parse(SMS_URI_INBOX);

    private ContentResolver allResolver;
    private ContentResolver sentResolver;
    private ContentResolver inboxResolver;

    private String goalAddress;
    private String contactsName;
    private String message;
    private Cursor cursor = null;

    private ImageButton leftButton;
    private ImageButton sendButton;
    private EditText txtMessage;
    private TextView centerTitle;

    private final int CONTACTS_CODE = 1;
    private final int SEND_SMS_REQUEST_CODE = 2;
    private boolean MESSAGE_IS_SENT = false;
    private MessagesDisplayer messagesDisplayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_read);

        centerTitle = (TextView) findViewById(R.id.center_title);
        leftButton = (ImageButton) findViewById(R.id.left_button);
        txtMessage = (EditText) findViewById(R.id.txt_message);
        sendButton = (ImageButton) findViewById(R.id.send_button);

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
        goalAddress = intent.getStringExtra("ADDRESS").replaceAll("\\s", "");
        contactsName = getContactsName(goalAddress);
        centerTitle.setText(contactsName);
        leftButton.setOnClickListener(mOnClickListener);
        txtMessage.addTextChangedListener(mTextWatcher);
        sendButton.setOnClickListener(mOnClickListener);
        messagesDisplayer = new MessagesDisplayer(this, goalAddress);


        allResolver = getContentResolver();
        allResolver.registerContentObserver(uri, true, new SmsObsever(new Handler()));
    }

    @Override
    protected void onPause() {
        Log.d("Life", "[NewMessageActivity]: onPause()");
        if (MESSAGE_IS_SENT) {
            try {
                SMSSender.getInstance(this).unregisterReceiver();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        super.onPause();
    }

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

    public void sendShortMessage(String phoneNumber, String message) {
        SMSSender.getInstance(this).SendMessage(phoneNumber, message);
    }

    public void sendMultipartMessage(String phoneNumber, String message) {
        SMSSender.getInstance(this).SendMessage2(phoneNumber, message);
    }

    public void send() {
        message = txtMessage.getText().toString();

        if (message.length() <= 70) {
            Log.d("110", "Length <= 70");
            sendShortMessage(goalAddress, message);
            MESSAGE_IS_SENT = true;
            Log.d("110", "Send a general message.");
            sendButton.setEnabled(false);
        } else {
            Log.d("110", "Length > 70");
            sendMultipartMessage(goalAddress, message);
            MESSAGE_IS_SENT = true;
            Log.d("110", "Send a long message.");
            sendButton.setEnabled(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("110", "Request is successful, and I'll read contacts.");
                    send();
                } else {
                    Log.d("110", "Request is failed.");
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            sendButton.setEnabled(txtMessage.getText().length() > 0);
        }

        @Override
        public void afterTextChanged(Editable s) {
            sendButton.setEnabled(txtMessage.getText().length() > 0);
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_button:
                    finish();
                    break;

                case R.id.send_button:
                    try {
                        /* Check `SEND_SMS` permission, if have it, read contacts, if not, request it. */
                        if (ContextCompat.checkSelfPermission(MessagesReadActivity.this, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MessagesReadActivity.this,
                                    new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUEST_CODE);
                            Log.d("110", "No permission, and I'll request it.");
                        } else {
                            Log.d("110", "Have permission, and I'll send message.");
                            send();
                        }
                    } catch (Exception e) {
                        Log.d("110", e.getMessage());
                    }
                    break;
            }
        }
    };

    private class SmsObsever extends ContentObserver {
        public SmsObsever(Handler handler) {
            super(handler);
        }

        public void onChange(boolean selfChange) {
            Log.v("911", "I know sent or inbox changed.");
            messagesDisplayer.refresh();
        }
    }

}
