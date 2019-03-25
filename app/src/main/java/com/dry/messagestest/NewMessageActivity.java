package com.dry.messagestest;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import top.gpg2.messages.R;

public class NewMessageActivity extends AppCompatActivity {

    private EditText txtPhoneNumber;
    private EditText txtMessage;
    private Button addButton;
    private Button sendButton;

    private String contactsPhone;
    private String contactsName;
    public String message;

    final private int CONTACTS_REQUEST_CODE = 1;
    private boolean MESSAGE_IS_SENT = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data /*@Nullable Intent data*/) {
        Log.d("110", "I'm function onActivityResult.");
        switch (requestCode) {
            case CONTACTS_REQUEST_CODE:
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
//        if (MESSAGE_IS_SENT) {
//            SMSMethod.getInstance(this).unregisterReceiver();
//        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        MESSAGE_IS_SENT = false;
        Log.d("Life", "[NewMessageActivity]: onResume()");
        super.onResume();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_1 = new Intent(NewMessageActivity.this, ContactsSelectActivity.class);
                startActivityForResult(intent_1, CONTACTS_REQUEST_CODE);
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
                    contactsPhone = txtPhoneNumber.getText().toString();
                    message = txtMessage.getText().toString();
                    // sendMessageByApp(contactsPhone, message);
                    if (message.length() <= 70) {
                        Log.d("110", "Length <= 70");
                        sendMessage(contactsPhone, message);
                        MESSAGE_IS_SENT = true;
                        Log.d("110", "Send a general message.");
                    } else {
                        Log.d("110", "Length > 70");
                        sendMultipartMessage(contactsPhone, message);
                        MESSAGE_IS_SENT = true;
                        Log.d("110", "Send a long message.");
                    }
                } catch (Exception e) {
                    Log.d("110", e.getMessage());
                }
            }
        });

        if (MESSAGE_IS_SENT) {
            SMSMethod.getInstance(this).unregisterReceiver();
        }
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
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String txtTimeForTest = sdf.format(date) + " I miss you again.";

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

    public void sendMessage(String phoneNumber, String message) {
        SMSMethod.getInstance(this).SendMessage(phoneNumber, message);
    }

    public void sendMultipartMessage(String phoneNumber, String message) {
        SMSMethod.getInstance(this).SendMessage2(phoneNumber, message);
    }
}
