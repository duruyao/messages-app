package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import top.gpg2.messages.R;

import java.util.List;

/**
 * An adapter that connects RecyclerView and List of sessions(that contain of all the messages about one phone number).
 *
 * @author DuRuyao
 * Create 19/03/30
 */
public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {
    private List<List<Messages>> sessionsList;
    private Context context;
    private Activity activity;

    /**
     * An internal class to cache instance of controls.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        View sessionsView;
        CircleImageView contactsImage;
        TextView smsAddress;
        TextView smsDate;
        TextView smsBody;

        /**
         * Constructor of ViewHolder class.
         *
         * @param view The outermost layout of item of RecyclerView.
         */
        private ViewHolder(View view) {
            super(view);
            sessionsView = view;
            contactsImage = (CircleImageView) view.findViewById(R.id.contacts_image);
            smsAddress = (TextView) view.findViewById(R.id.sms_address);
            smsDate = (TextView) view.findViewById(R.id.sms_date);
            smsBody = (TextView) view.findViewById(R.id.sms_body);
        }
    }

    /**
     * Constructor of MessagesAdapter class to import data list.
     *
     * @param context      Current context.
     * @param sessionsList List of sessions(that contain of all the messages about one phone number).
     */
    public SessionsAdapter(Context context, List<List<Messages>> sessionsList) {
        this.sessionsList = sessionsList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sessions_item,
                parent, false);
        /* Create instance of ViewHolder by passing the view parameter. */
        final ViewHolder holder = new ViewHolder(view);

        holder.sessionsView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, MessagesReadActivity.class);
                Log.d("110", "Create a Intent.");
                int position = holder.getAdapterPosition();
                Messages messages = sessionsList.get(position).get(0);
                // Toast.makeText(v.getContext(), "Select " + messages.getAddress(), Toast.LENGTH_SHORT).show();
                intent1.putExtra("addressKey", messages.getAddress());
                activity.startActivity(intent1);

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
        Messages messages = sessionsList.get(position).get(0);
        holder.smsAddress.setText(messages.getContactName(this.context));
        holder.smsBody.setText(messages.getBody());
    }

    /**
     * Get number of item of RecyclerView.
     *
     * @return A number.
     */
    @Override
    public int getItemCount() {
        return sessionsList.size();
    }

}
