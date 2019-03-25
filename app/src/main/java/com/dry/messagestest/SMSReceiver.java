package com.dry.messagestest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import android.telephony.SmsManager;


public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMSMethod.SMS_SEND_ACTION)) {
            try {
                /* android.content.BroadcastReceiver.getResultCode()方法 */
                //Retrieve the current result code, as set by the previous receiver.
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "Success to send", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(context, "Failed to send", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                }
            } catch (Exception e) {

            }
        } else if (intent.getAction().equals(SMSMethod.SMS_DELIVERED_ACTION)) {
            /* android.content.BroadcastReceiver.getResultCode()方法 */
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "Message has arrived", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    /* 短信未送达 */
                    Toast.makeText(context, "Message hasn't arrived", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    break;
            }
        }
    }
}

