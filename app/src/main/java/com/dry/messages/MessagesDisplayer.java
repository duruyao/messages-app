package com.dry.messages;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.gpg2.messages.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that could be called to display messages.
 *
 * @author DuRuyao
 * Create 19/03/28
 */
public class MessagesDisplayer {
    final private int READ_SMS_REQUEST_CODE = 121;
    final String SMS_URI_ALL = "content://sms/";

    private List<Messages> messagesList = new ArrayList<>();
    private Context context;
    private Activity activity;
    private int id;
    private String goalAddress;
    private String address;
    private int person;
    private String body;
    private long date;
    private int type;
    private int read;

    public MessagesDisplayer(Context context, String goalAddress) {
        this.context = context;
        this.goalAddress = goalAddress;
        this.activity = ActivityHelper.getActivity(this.context);

        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity,
                    new String[]{Manifest.permission.READ_SMS}, READ_SMS_REQUEST_CODE);
            Log.d("110", "No permission, and I'll request it.");
        } else {
            Log.d("110", "Have permission, and I'll read SMS.");
            readSMS();
        }

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.messages_recyclerView);
        /* Set layout manager for an instance of RecyclerView. */
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        /* Instance an Adapter who contains of list of messages, and import it to the instance of RecyclerView. */
        MessagesAdapter adapter = new MessagesAdapter(this.context, messagesList);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void readSMS() {
        Log.d("110", "Call function readSMS().");
        Cursor cursor = null;
        try {
            cursor = activity.getContentResolver().query(Uri.parse(SMS_URI_ALL), null, null, null, "date asc");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    id = cursor.getInt(cursor.getColumnIndex("_id"));
                    address = cursor.getString(cursor.getColumnIndex("address"));
                    person = cursor.getInt(cursor.getColumnIndex("person"));
                    body = cursor.getString(cursor.getColumnIndex("body"));
                    date = cursor.getLong(cursor.getColumnIndex("date"));
                    type = cursor.getInt(cursor.getColumnIndex("type"));
                    read = cursor.getInt(cursor.getColumnIndex("read"));
                    if (address.equals(goalAddress)) {
                        messagesList.add(new Messages(id, address, person, body, date, type, read));
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
