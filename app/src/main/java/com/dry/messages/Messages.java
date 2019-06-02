package com.dry.messages;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.text.format.DateFormat;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Date;

/**
 * A class of messages.
 *
 * @author DuRuyao
 * @version 1.0
 * Create 19/03/26 10:36 AM
 * Update [1] [yy/mm/dd hh:mm] [name] [description]
 */
public class Messages {
    final String SMS_URI_ALL = "content://sms/";
    private final int TYPE_RECEIVED = 1;
    private final int TYPE_SENT = 2;

    private final boolean BE_READ = true;
    private final boolean BE_NOT_READ = false;

    public static final int TYPE_INFO = 1;
    public static final int TYPE_TICKET = 2;
    public static final int TYPE_PIN = 3;
    public static final int TYPE_NOTICE = 4;

    private int id;
    private String contactName;
    private Context context;
    private Activity activity;

    private String address;
    private String body;
    private String longDate;
    private String shortDate;
    private int person;
    private int type;
    private int read;
    private int contentType = TYPE_INFO;

    /**
     * Constructor of Messages class.
     *
     * @param address The phone number of contract.
     * @param body    The text of message.
     */


    public Messages(int id, String address, int person, String body, long date, int type, int read, Context context) {
        this.id = id;
        this.address = address;
        this.person = person;
        this.body = body;
        this.longDate = (String) DateFormat.format("yyyy/MM/dd HH:mm:ss", new Date(date));
        this.shortDate = new RegexManager(this.longDate, "\\s.*").replaceAll("");
        this.type = type;
        this.read = read;
        this.context = context;
        this.activity = ActivityHelper.getActivity(this.context);
    }

    public int getID() {
        return this.id;
    }

    public String getAddress() {
        return this.address;
    }

    public int getPerson() {
        return this.person;
    }

    public String getBody() {
        return this.body;
    }

    public String getLongDate() {
        return this.longDate;
    }

    public String getShortDate() {
        return this.shortDate;
    }

    public int getType() {
        return this.type;
    }

    public int getRead() {
        return this.read;
    }

    public String getContactName() {
        setContactName();
        return this.contactName;
    }

    public String getShortDateAgain() {
        long date;
        Cursor cursor = null;
        try {
            cursor = activity.getContentResolver().query(Uri.parse(SMS_URI_ALL), null, "_id = ?",
                    new String[]{String.valueOf(id)}, "date asc");
            if (cursor != null && cursor.moveToFirst()) {
                date = cursor.getLong(cursor.getColumnIndex("date"));
                return (String) DateFormat.format("yyyy/MM/dd", new Date(date));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return getShortDate();
    }


    private void setContactName() {
        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity,
                    new String[]{Manifest.permission.READ_CONTACTS}, 1);
            Log.d("110", "No permission to get name of contacts.");
            this.contactName = getAddress();
        } else {
            Log.d("110", "Have permission, and I'll read name of contacts.");
            Cursor cursor = null;
            try {
                cursor = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, null, null, null);
                this.contactName = getAddress();
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        /* Delete all white spaces from phone number(e.g. `133 5719 2542` -> `13357192542`). */
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).
                                replaceAll("\\s", "");
                        if (number.equals(getAddress())) {
                            this.contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        } else {
                            Log.d("110", "Failed to find, the number: " + number);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

    }

    public static int judgeContentType(String body) {
        if (new RegexManager(body, "12306").isFind()) {
            return TYPE_TICKET;
        } else if (new RegexManager(body, "[验证|校验]").isFind()) {
            return TYPE_PIN;
        } else if (new RegexManager(body, "[客户|用户|账户|支付宝|京东|支付宝|淘宝|快递|外卖|银行]").isFind()) {
            return TYPE_NOTICE;
        } else {
            return TYPE_INFO;
        }
    }
}
