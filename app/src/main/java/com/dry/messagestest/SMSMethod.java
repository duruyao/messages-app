package com.dry.messagestest;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

public class SMSMethod {
    private static SMSMethod mSMSmsMethod;
    public static String SMS_SEND_ACTION = "SMS_SEND_ACTION";
    public static String SMS_DELIVERED_ACTION = "SMS_DELIVERED_ACTION";

    private SMSReceiver sendSMSReceiver, deliveredSMSReceiver;

    private Context context;

    private SMSMethod(Context context) {
        this.context = context;
        registerReceiver();
    }

    public static SMSMethod getInstance(Context context) {
        if (mSMSmsMethod == null) {
            synchronized (SMSMethod.class) {
                if (mSMSmsMethod == null) {
                    mSMSmsMethod = new SMSMethod(context);
                }
            }
        }
        return mSMSmsMethod;
    }


    public void SendMessage(String strDestAddress, String strMessage) {
        /* 建立SmsManager对象 */
        SmsManager smsManager = SmsManager.getDefault();
        // SmsManager smsManager1 = SmsManager.getSmsManagerForSubscriptionId(subId);
        try {
            /* 建立自定义Action常数的Intent(给PendingIntent参数之用) */
            Intent itSend = new Intent(SMS_SEND_ACTION);
            Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);

            /* sentIntent参数为传送后接受的广播信息PendingIntent */
            PendingIntent sendPI = PendingIntent.getBroadcast(context, 0, itSend, 0);

            /* deliveryIntent参数为送达后接受的广播信息PendingIntent */
            PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, itDeliver, 0);
            List<String> divideContents = smsManager.divideMessage(strMessage);
            for (String text : divideContents) {
                /* 发送SMS短信，注意倒数的两个PendingIntent参数 */
                smsManager.sendTextMessage(strDestAddress, null, text, sendPI, deliverPI);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendMessage2(String strDestAddress, String strMessage) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();


        /* 建立SmsManager对象 */
        SmsManager smsManager = SmsManager.getDefault();
        // SmsManager smsManager1 = SmsManager.getSmsManagerForSubscriptionId(subId);
        try {
            /* 建立自定义Action常数的Intent(给PendingIntent参数之用) */
            Intent itSend = new Intent(SMS_SEND_ACTION);
            Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);

            /* sentIntent参数为传送后接受的广播信息PendingIntent */
            PendingIntent sendPI = PendingIntent.getBroadcast(context, 0, itSend, 0);

            /* deliveryIntent参数为送达后接受的广播信息PendingIntent */
            PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, itDeliver, 0);
            ArrayList<String> messageArray = smsManager.divideMessage(strMessage);

            for (int i = 0; i < messageArray.size(); i++) {
                sentPendingIntents.add(i, sendPI);
                deliveredPendingIntents.add(i, deliverPI);
            }
            /* 发送SMS短信，注意倒数的两个PendingIntent参数 */
            smsManager.sendMultipartTextMessage(strDestAddress, null, messageArray,
                    sentPendingIntents, deliveredPendingIntents);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void registerReceiver() {
        IntentFilter intentFilter;
        intentFilter = new IntentFilter(SMS_SEND_ACTION);
        sendSMSReceiver = new SMSReceiver();
        context.registerReceiver(sendSMSReceiver, intentFilter);

        intentFilter = new IntentFilter(SMS_DELIVERED_ACTION);
        deliveredSMSReceiver = new SMSReceiver();
        context.registerReceiver(deliveredSMSReceiver, intentFilter);
    }

    public void unregisterReceiver() {
        if (sendSMSReceiver != null) {
            context.unregisterReceiver(sendSMSReceiver);
        }
        if (deliveredSMSReceiver != null) {
            context.unregisterReceiver(deliveredSMSReceiver);
        }
    }
}
