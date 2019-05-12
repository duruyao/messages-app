package com.dry.messages;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A class as sender of SMS, and it contains of some methods about sending messages.
 *
 * @author DuRuyao
 * Create 19/03/20
 */
public class SMSSender {
    private static SMSSender smsSender;
    public static String SMS_SEND_ACTION = "SMS_SEND_ACTION";
    public static String SMS_DELIVERED_ACTION = "SMS_DELIVERED_ACTION";

    private SMSBroadcastReceiver sendSMSReceiver, deliveredSMSReceiver;

    private Context context;

    private SMSSender(Context context) {
        this.context = context;
        registerReceiver();
    }

    public static SMSSender getInstance(Context context) {
        if (smsSender == null) {
            synchronized (SMSSender.class) {
                if (smsSender == null) {
                    smsSender = new SMSSender(context);
                }
            }
        }
        return smsSender;
    }

    public void SendMessage(String strAddress, String strMessage) {
        /* Make instance of SmsManager. */
        SmsManager smsManager = SmsManager.getDefault();

        /* The following line is suitable of API 22(Android 5.1.x) or higher. */
        // SmsManager smsManager1 = SmsManager.getSmsManagerForSubscriptionId(subId);
        try {
            /* Make instance of Intent that is imported self-defined Action. */
            Intent itSend = new Intent(SMS_SEND_ACTION);
            Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);

            /* Make instance of PendingIntent to start two broadcasts while sending message. */
            PendingIntent sendPI = PendingIntent.getBroadcast(context, 0, itSend, 0);
            PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, itDeliver, 0);
            /* Get content of message.*/
            List<String> divideContents = smsManager.divideMessage(strMessage);
            for (String text : divideContents) {
                /* Send SMS. */
                smsManager.sendTextMessage(strAddress, null, text, sendPI, deliverPI);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendMessage2(String strAddress, String strMessage) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();

        SmsManager smsManager = SmsManager.getDefault();
        /* The following line is suitable of API 22(Android 5.1.x) or higher. */
        // SmsManager smsManager1 = SmsManager.getSmsManagerForSubscriptionId(subId);
        try {
            /* Make instance of Intent that is imported self-defined Action. */
            Intent itSend = new Intent(SMS_SEND_ACTION);
            Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);

            /* Make instance of PendingIntent to start two broadcasts while sending message. */
            PendingIntent sendPI = PendingIntent.getBroadcast(context, 0, itSend, 0);
            PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, itDeliver, 0);
            ArrayList<String> messageArray = smsManager.divideMessage(strMessage);

            for (int i = 0; i < messageArray.size(); i++) {
                sentPendingIntents.add(i, sendPI);
                deliveredPendingIntents.add(i, deliverPI);
            }
            /* Send SMS. */
            smsManager.sendMultipartTextMessage(strAddress, null, messageArray,
                    sentPendingIntents, deliveredPendingIntents);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendMessage2(List<String> strAddressList, String strMessage) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();

        SmsManager smsManager = SmsManager.getDefault();
        /* The following line is suitable of API 22(Android 5.1.x) or higher. */
        // SmsManager smsManager1 = SmsManager.getSmsManagerForSubscriptionId(subId);
        try {
            /* Make instance of Intent that is imported self-defined Action. */
            Intent itSend = new Intent(SMS_SEND_ACTION);
            Intent itDeliver = new Intent(SMS_DELIVERED_ACTION);

            /* Make instance of PendingIntent to start two broadcasts while sending message. */
            PendingIntent sendPI = PendingIntent.getBroadcast(context, 0, itSend, 0);
            PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0, itDeliver, 0);
            ArrayList<String> messageArray = smsManager.divideMessage(strMessage);

            for (int i = 0; i < messageArray.size(); i++) {
                sentPendingIntents.add(i, sendPI);
                deliveredPendingIntents.add(i, deliverPI);
            }
            /* Send SMS. */
            for (int i = 0; i < strAddressList.size(); i++) {
                smsManager.sendMultipartTextMessage(strAddressList.get(i), null, messageArray,
                        sentPendingIntents, deliveredPendingIntents);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Register a receiver that listening to broadcast.
     */
    public void registerReceiver() {
        IntentFilter intentFilter;
        intentFilter = new IntentFilter(SMS_SEND_ACTION);
        sendSMSReceiver = new SMSBroadcastReceiver();
        context.registerReceiver(sendSMSReceiver, intentFilter);

        intentFilter = new IntentFilter(SMS_DELIVERED_ACTION);
        deliveredSMSReceiver = new SMSBroadcastReceiver();
        context.registerReceiver(deliveredSMSReceiver, intentFilter);
    }

    /**
     * Unregister a receiver that listening to broadcast.
     */
    public void unregisterReceiver() {
        if (sendSMSReceiver != null) {
            context.unregisterReceiver(sendSMSReceiver);
        }
        if (deliveredSMSReceiver != null) {
            context.unregisterReceiver(deliveredSMSReceiver);
        }
    }
}
