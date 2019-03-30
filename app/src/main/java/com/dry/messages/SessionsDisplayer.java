package com.dry.messages;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.gpg2.messages.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class that could be called to display sessions.
 *
 * @author DuRuyao
 * Create 19/03/30
 */
public class SessionsDisplayer {

    final private int READ_SMS_REQUEST_CODE = 121;
    final String SMS_URI_ALL = "content://sms/";
    private List<Messages> allMessagesList = new ArrayList<>();
    private List<List<Messages>> sessionsList = new ArrayList<List<Messages>>();
    private Context context;
    private Activity activity;

    public SessionsDisplayer(Context context) {
        this.context = context;
        this.activity = getActivity(this.context);

        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity,
                    new String[]{Manifest.permission.READ_SMS}, READ_SMS_REQUEST_CODE);
            Log.d("110", "No permission, and I'll request it.");
        } else {
            Log.d("110", "Have permission, and I'll read SMS.");
            getSessions();
        }

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        SessionsAdapter adapter = new SessionsAdapter(this.context, sessionsList);
        recyclerView.setAdapter(adapter);
    }

    public void getSessions() {
        allMessagesList = getAllSMSList();
        Iterator<Messages> mIterator = allMessagesList.iterator();
        while (mIterator.hasNext()) {
            List<Messages> mList = new ArrayList<>();

            Messages messages1 = (Messages) mIterator.next();
            mList.add(messages1);
            String ADDRESS = messages1.getAddress();
            allMessagesList.remove(mIterator.next());
            while (mIterator.hasNext()) {
                Messages messages2 = (Messages) mIterator.next();
                if (messages2.getAddress() == ADDRESS) {
                    mList.add(messages2);
                    allMessagesList.remove(mIterator.next());
                }
            }
            sessionsList.add(mList);
        }


    }

    public List<Messages> getAllSMSList() {
        List<Messages> allMessagesList = new ArrayList<>();
        Log.d("110", "Call function readSMS().");
        Cursor cursor = null;
        try {
            cursor = activity.getContentResolver().query(Uri.parse(SMS_URI_ALL), null, null, null, "date desc");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String messagesAddress =
                            cursor.getString(cursor.getColumnIndex("address"));
                    String messagesBody =
                            cursor.getString(cursor.getColumnIndex("body"));

                    allMessagesList.add(new Messages(messagesAddress, messagesBody));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return allMessagesList;
    }

    private Activity getActivity(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        if (context instanceof Activity) {
            return (Activity) context;
        } else {
            return null;
        }
    }
}
