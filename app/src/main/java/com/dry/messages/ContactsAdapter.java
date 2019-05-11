package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import top.gpg2.messages.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * An adapter class that connects RecyclerView and List of contacts.
 *
 * @author DuRuyao
 * Create 19/03/15
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Contacts> contactsList;
    private Context context;
    private Activity activity;

    /**
     * An internal class to cache instance of controls.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        View contactsView;
        TextView contactsName;
        TextView contactsPhoneNum;

        /**
         * Constructor of ViewHolder class.
         *
         * @param view The outermost layout of item of RecyclerView.
         */
        private ViewHolder(View view) {
            super(view);
            contactsView = view;
            contactsName = (TextView) view.findViewById(R.id.contacts_name);
            contactsPhoneNum = (TextView) view.findViewById(R.id.contacts_phone_num);
        }
    }

    /**
     * Constructor of MessagesAdapter class to import data list.
     *
     * @param context      Current context.
     * @param contactsList List of contracts.
     */
    public ContactsAdapter(Context context, List<Contacts> contactsList) {
        this.contactsList = contactsList;
        this.context = context;
        this.activity = ActivityController.getActivity(this.context);
    }

    /**
     * Get a ViewHolder that contains of the item of RecyclerLayout.
     *
     * @return A instance of ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* Load the item of RecyclerLayout. */
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item,
                parent, false);
        /* Create instance of ViewHolder by passing the view parameter. */
        final ViewHolder holder = new ViewHolder(view);

        holder.contactsView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                Log.d("110", "Create a Intent.");
                int position = holder.getAdapterPosition();
                Contacts contacts = contactsList.get(position);
                intent1.putExtra("phoneKey", contacts.getPhoneNumber());
                intent1.putExtra("nameKey", contacts.getName());
                Toast.makeText(v.getContext(),
                        "Select " + contacts.getName() + " " + contacts.getPhoneNumber(),
                        Toast.LENGTH_SHORT).show();
//                Activity activity = getActivity(context);
                Log.d("110", "Get current Activity");
                activity.setResult(RESULT_OK, intent1);
                Log.d("110", "Return a Intent.");
                activity.finish();

            }
        });
        /* Return the instance of ViewHolder. */
        return holder;
    }

    /**
     * The method will be called when item of RecyclerView appear on the screen, and set somethings for item.
     *
     * @param holder   The holder of item of RecyclerView.
     * @param position The position of item in RecyclerView.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contacts contacts = contactsList.get(position);
        holder.contactsName.setText(contacts.getName());
        holder.contactsPhoneNum.setText(contacts.getPhoneNumber());
    }

    /**
     * Get number of item of RecyclerView.
     *
     * @return A number.
     */
    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}
