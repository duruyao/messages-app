package com.dry.messages;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.gpg2.messages.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that could be called by Activity to display sessions.
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

    private int id;
    private String address;
    private int person;
    private String body;
    private int date;
    private int type;
    private int read;

    private int lastPosition = 0;
    private int lastOffset = 0;

    public SessionsDisplayer(Context context) {
        this.context = context;
        this.activity = ActivityHelper.getActivity(this.context);

        if (ContextCompat.checkSelfPermission(this.context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.activity,
                    new String[]{Manifest.permission.READ_SMS}, READ_SMS_REQUEST_CODE);
            Log.d("110", "No permission, and I'll request it.");
        } else {
            Log.d("110", "Have permission, and I'll read SMS.");
            getSessions();
        }

        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView1);
        /* Set layout manager for an instance of RecyclerView. */
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        /* Instance an Adapter who contains of list of sessions, and import it to the instance of RecyclerView. */
        SessionsAdapter adapter = new SessionsAdapter(this.context, sessionsList);
        recyclerView.setAdapter(adapter);
        /* Go back the last position of RecyclerView. */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View topView = layoutManager.getChildAt(0);
                /* Get position when topView != null */
                if (topView != null) {
                    lastOffset = topView.getTop();
                    lastPosition = layoutManager.getPosition(topView);
                    Log.v("110", "last off set: " + lastOffset);
                    Log.v("110", "last position: " + lastPosition);
                }
            }
        });
        try {
            ((LinearLayoutManager) layoutManager).scrollToPositionWithOffset(lastPosition, lastOffset);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public void getSessions() {
        Log.d("110", "Call function getSessions().");
        allMessagesList = getAllSMSList();
        Log.d("110", "Have get all messages list.");

        for (int i = 0; i < allMessagesList.size(); i++) {
            List<Messages> mList = new ArrayList<>();
            Messages messages1 = allMessagesList.get(i);
            mList.add(messages1);
            String ADDRESS = messages1.getAddress();

            for (int j = i + 1; j < allMessagesList.size(); j++) {
                Messages messages2 = allMessagesList.get(j);
                if (messages2.getAddress().equals(ADDRESS)) {
                    mList.add(messages2);
                    allMessagesList.remove(j);
                    j--;
                }
            }
            allMessagesList.remove(i);
            i--;
            sessionsList.add(mList);
        }
        /* Meeting an Error: `Messages messages1 = (Messages) mIterator.next();` */
        /* Iterator<Messages> mIterator = allMessagesList.iterator();
        while (mIterator.hasNext()) {
            List<Messages> mList = new ArrayList<>();
            Messages messages1 = (Messages) mIterator.next();
            mList.add(mIterator.next());
            String ADDRESS = messages1.getAddress();
            mIterator.remove();
            while (mIterator.hasNext()) {
                Messages messages2 = (Messages) mIterator.next();
                if (messages2.getAddress().equals(ADDRESS)) {
                    mList.add(messages2);
                    mIterator.remove();
                }
            }
            sessionsList.add(mList);
        } */
    }

    public List<Messages> getAllSMSList() {
        List<Messages> messagesList = new ArrayList<>();
        Log.d("110", "Call function getAllSMSList().");
        Cursor cursor = null;
        try {
            cursor = activity.getContentResolver().query(Uri.parse(SMS_URI_ALL), null, null, null, "date desc");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    id = cursor.getInt(cursor.getColumnIndex("_id"));
                    address = cursor.getString(cursor.getColumnIndex("address"));
                    person = cursor.getInt(cursor.getColumnIndex("person"));
                    body = cursor.getString(cursor.getColumnIndex("body"));
                    date = cursor.getInt(cursor.getColumnIndex("date"));
                    type = cursor.getInt(cursor.getColumnIndex("type"));
                    read = cursor.getInt(cursor.getColumnIndex("read"));

                    messagesList.add(new Messages(id, address, person, body, date, type, read));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return messagesList;
    }

}
