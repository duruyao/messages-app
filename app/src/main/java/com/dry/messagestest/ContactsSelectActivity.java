package com.dry.messagestest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import top.gpg2.messages.R;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity to show and allow user to select contact.
 *
 * @author DuRuyao
 * Create 19/03/15
 */
public class ContactsSelectActivity extends AppCompatActivity {
    final private int READ_CONTACTS_REQUEST_CODE = 1;
    private List<Contacts> contactsList = new ArrayList<>();

    @Override
    protected void onDestroy() {
        Log.d("Life", "[ContactsSelectActivity]: onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d("Life", "[ContactsSelectActivity]: onRestart()");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.d("Life", "[ContactsSelectActivity]: onStop()");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d("Life", "[ContactsSelectActivity]: onPause()");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("Life", "[ContactsSelectActivity]: onResume");
        super.onResume();

        // Check `READ_CONTACTS` permission, if have it, read contacts, if not, request it.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_REQUEST_CODE);
            Log.d("110", "No permission, and I'll request it.");
        } else {
            Log.d("110", "Have permission, and I'll read contacts.");
            readContacts();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contacts_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ContactsAdapter adapter = new ContactsAdapter(ContactsSelectActivity.this, contactsList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        Log.d("Life", "[ContactsSelectActivity]: onStart()");
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Life", "[ContactsSelectActivity]: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_select);

        /* Hide default title. */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private void readContacts() {
        Log.d("110", "Call function readContacts().");
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    , null, null, null, ContactsContract.Contacts.SORT_KEY_PRIMARY);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String displayNumber =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    contactsList.add(new Contacts(displayName, displayNumber));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case READ_CONTACTS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("110", "Request is successful, and I'll read contacts.");
                    readContacts();
                } else {
                    Log.d("110", "Request is failed.");
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
