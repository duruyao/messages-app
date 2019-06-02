package com.dry.messages;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import top.gpg2.messages.R;

/**
 * An activity to make a new message.
 *
 * @author DuRuyao
 * Create 19/03/05
 */
public class NewMessageActivity extends AppCompatActivity {
    private EditText txtPhoneNumber;
    private EditText txtMessage;
    private ImageButton addContactsButton;
    private ImageButton sendButton;

    private String contactsPhone;
    private String contactsName;
    public String message;

    final private int CONTACTS_CODE = 1;
    final private int SEND_SMS_REQUEST_CODE = 2;
    private boolean MESSAGE_IS_SENT = false;

    private IntentFilter intentFilter;
    private SimpleSMSReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Life", "[NewMessageActivity]: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_messages);

        /* Make time as content of message for test. */
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("[yyyy/MM/dd HH:mm:ss]");
        Date date = new Date();
        String txtTimeForTest = sdf.format(date) + " hello";

        if (Build.VERSION.SDK_INT >= 23) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(getResources().getColor(R.color.WhiteSmoke));
        }

        txtPhoneNumber = (EditText) findViewById(R.id.txt_phone_number);
        txtMessage = (EditText) findViewById(R.id.txt_message);
        addContactsButton = (ImageButton) findViewById(R.id.add_contacts_button);
        sendButton = (ImageButton) findViewById(R.id.send_button);
        intentFilter = new IntentFilter();
        smsReceiver = new SimpleSMSReceiver();

        txtPhoneNumber.requestFocus();
        sendButton.setEnabled(false);
        KeyboardController.showKeyboardDelay(txtPhoneNumber, 300);
    }

    @Override
    protected void onResume() {
        MESSAGE_IS_SENT = false;
        Log.d("Life", "[NewMessageActivity]: onResume()");
        super.onResume();

        /* Current min API is 21, but the messages management can't be changed until API 22(Android 5.1.x). */
        /* SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
        List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
        for (SubscriptionInfo subscriptionInfo : subscriptionInfoList) {
            int subscriptionId = subscriptionInfo.getSubscriptionId();
            Log.d("110","subscriptionId:"+subscriptionId);
        } */

        addContactsButton.setOnClickListener(mOnClickListener);
        txtPhoneNumber.addTextChangedListener(mTextWatcher);
        txtMessage.addTextChangedListener(mTextWatcher);
        sendButton.setOnClickListener(mOnClickListener);

        intentFilter.addAction("MESSAGES_ARRIVED");
        registerReceiver(smsReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        Log.d("Life", "[NewMessageActivity]: onPause()");
        if (MESSAGE_IS_SENT) {
             SMSSender.getInstance(this).unregisterReceiver();
            unregisterReceiver(smsReceiver);
        }
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data /*@Nullable Intent data*/) {
        Log.d("110", "I'm function onActivityResult.");
        switch (requestCode) {
            case CONTACTS_CODE:
                if (resultCode == RESULT_OK) {
                    contactsPhone = data.getStringExtra("PHONE");
                    contactsName = data.getStringExtra("NAME");
                    String text = contactsPhone;
                    Log.d("110", "Get: " + text);
                    txtPhoneNumber.setText(text);
                }
                break;
            default:
        }
    }

    public void sendMessageByApp(String phoneNumber, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    public void sendShortMessage(String phoneNumber, String message) {
         SMSSender.getInstance(this).SendMessage(phoneNumber, message);
    }

    public void sendMultipartMessage(String phoneNumber, String message) {
         SMSSender.getInstance(this).SendMessage2(phoneNumber, message);
    }

    public void send() {
        contactsPhone = txtPhoneNumber.getText().toString();
        message = txtMessage.getText().toString();

        if (message.length() <= 70) {
            Log.d("110", "Length <= 70");
            sendShortMessage(contactsPhone, message);
            MESSAGE_IS_SENT = true;
            Log.d("110", "Send a general message.");
            sendButton.setEnabled(false);
        } else {
            Log.d("110", "Length > 70");
            sendMultipartMessage(contactsPhone, message);
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
            sendButton.setEnabled(txtPhoneNumber.getText().length() > 0 && txtMessage.getText().length() > 0);
        }

        @Override
        public void afterTextChanged(Editable s) {
            sendButton.setEnabled(txtPhoneNumber.getText().length() > 0 && txtMessage.getText().length() > 0);
        }
    };

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_contacts_button:
                    Intent intent1 = new Intent(NewMessageActivity.this, ContactsSelectActivity.class);
                    startActivityForResult(intent1, CONTACTS_CODE);
                    break;
                case R.id.send_button:
                    try {
                        /* Check `SEND_SMS` permission, if have it, read contacts, if not, request it. */
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
                    break;
            }
        }
    };

    class SimpleSMSReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent1 = new Intent(NewMessageActivity.this, MessagesReadActivity.class);
            intent1.putExtra("ADDRESS", new RegexManager(contactsPhone, " ").replaceAll(""));
            startActivity(intent1);
        }
    }
}
