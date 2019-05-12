package com.dry.messages;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
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

    private final int TYPE_RECEIVED = 1;
    private final int TYPE_SENT = 2;
    private final boolean BE_READ = true;
    private final boolean BE_NOT_READ = false;

    private String contactName;
    private Context context;
    private Activity activity;

    private String address;
    private int person;
    private String body;
    private String date;
    private String dateWithoutTime;
    private int type;
    private int read;

    /**
     * Constructor of Messages class.
     *
     * @param address The phone number of contract.
     * @param body    The text of message.
     */
    public Messages(String address, int person, String body, long date, int type, int read) {
        this.address = address;
        this.person = person;
        this.body = body;
        this.date = (String) DateFormat.format("yyyy/MM/dd HH:mm:ss", new Date(date));
        this.dateWithoutTime = (String) DateFormat.format("yyyy/MM/dd", new Date(date));
        this.type = type;
        this.read = read;

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

    public String getDate() {
        return this.date;
    }

    public String getDateWithoutTime() {
        return this.dateWithoutTime;
    }

    public int getType() {
        return this.type;
    }

    public int getRead() {
        return this.read;
    }

    /**
     * @param context Current context.
     * @return The contact's name, if the number belong with one contact, else return the number.
     */
    public String getContactName(Context context) {
        this.context = context;
        this.activity = ActivityHelper.getActivity(this.context);

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
                    Log.d("110", "Find number: " + this.address);
                    while (cursor.moveToNext()) {
                        /* Delete all white spaces from phone number(e.g. `133 5719 2542` -> `13357192542`). */
                        String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).
                                replaceAll("\\s", "");
                        if (number.equals(getAddress())) {
                            Log.d("110", "Success to find, the number: " + number);
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
        return this.contactName;
    }
}
