package com.dry.messagestest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import top.gpg2.messages.R;

public class NewMessageActivity extends AppCompatActivity {

    private EditText txtPhoneNumber;
    private EditText txtMessage;
    private Button addButton;
    private Button sendButton;

    private String contactsPhone;
    private String contactsName;
    public String message;

    final private int CONTACTS_CODE = 1;
    final private int SEND_SMS_REQUEST_CODE = 2;
    private boolean MESSAGE_IS_SENT = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data /*@Nullable Intent data*/) {
        Log.d("110", "I'm function onActivityResult.");
        switch (requestCode) {
            case CONTACTS_CODE:
                if (resultCode == RESULT_OK) {
                    contactsPhone = data.getStringExtra("phoneKey");
                    contactsName = data.getStringExtra("nameKey");
                    String text = contactsPhone;
                    Log.d("110", "Get: " + text);
                    txtPhoneNumber.setText(text);
                }
                break;
            default:
        }
        // super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        Log.d("Life", "[NewMessageActivity]: onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d("Life", "[NewMessageActivity]: onRestart()");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.d("Life", "[NewMessageActivity]: onStop()");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d("Life", "[NewMessageActivity]: onPause()");
        if (MESSAGE_IS_SENT) {
            SMSMethod.getInstance(this).unregisterReceiver();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        MESSAGE_IS_SENT = false;
        Log.d("Life", "[NewMessageActivity]: onResume()");
        super.onResume();

        // Current min API is 21, but the messages management can't be changed until API 22(Android 5.1.x).
//        SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
//        List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
//        for (SubscriptionInfo subscriptionInfo : subscriptionInfoList) {
//            int subscriptionId = subscriptionInfo.getSubscriptionId();
//            Log.d("110","subscriptionId:"+subscriptionId);
//        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_1 = new Intent(NewMessageActivity.this, ContactsSelectActivity.class);
                startActivityForResult(intent_1, CONTACTS_CODE);
            }
        });

        txtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendButton.setEnabled(txtPhoneNumber.getText().length() > 0 && txtMessage.getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
                sendButton.setEnabled(txtPhoneNumber.getText().length() > 0 && txtMessage.getText().length() > 0);
            }
        });

        txtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sendButton.setEnabled(txtPhoneNumber.getText().length() > 0 && txtMessage.getText().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
                sendButton.setEnabled(txtPhoneNumber.getText().length() > 0 && txtMessage.getText().length() > 0);
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Check `SEND_SMS` permission, if have it, read contacts, if not, request it.
                    if (ContextCompat.checkSelfPermission(NewMessageActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(NewMessageActivity.this,
                                new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUEST_CODE);
                        Log.d("110", "No permission, and I'll request it.");
                    } else {
                        Log.d("110", "Have permission, and I'll send message.");
                        send();
                    }
                } catch (Exception e) {
                    Log.d("110", e.getMessage());
                }
            }
        });

    }

    @Override
    protected void onStart() {
        Log.d("Life", "[NewMessageActivity]: onStart()");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Life", "[NewMessageActivity]: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_message);

        // Make time as content of message for test.
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("[yy/MM/dd HH:mm:ss]");
        Date date = new Date();
        String txtTimeForTest = sdf.format(date) + " i miss u.";

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        txtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
        txtMessage = (EditText) findViewById(R.id.txt_message);
        addButton = (Button) findViewById(R.id.add_button);
        sendButton = (Button) findViewById(R.id.send_button);

        txtPhoneNumber.requestFocus();
        txtMessage.setText(txtTimeForTest);
        sendButton.setEnabled(false);
        KeyboardControl.showKeyboardDelay(txtPhoneNumber, 300);
    }

    public void sendMessageByApp(String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    public void sendShortMessage(String phoneNumber, String message) {
        SMSMethod.getInstance(this).SendMessage(phoneNumber, message);
    }

    public void sendMultipartMessage(String phoneNumber, String message) {
        SMSMethod.getInstance(this).SendMessage2(phoneNumber, message);
    }

    public void send() {
        contactsPhone = txtPhoneNumber.getText().toString();
        message = txtMessage.getText().toString();
        // sendMessageByApp(contactsPhone, message);

        if (message.length() <= 70) {
            Log.d("110", "Length <= 70");
            sendShortMessage(contactsPhone, message);
            MESSAGE_IS_SENT = true;
            Log.d("110", "Send a general message.");
        } else {
            Log.d("110", "Length > 70");
            sendMultipartMessage(contactsPhone, message);
            MESSAGE_IS_SENT = true;
            Log.d("110", "Send a long message.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
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
}
