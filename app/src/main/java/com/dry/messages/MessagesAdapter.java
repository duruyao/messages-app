package com.dry.messages;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
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
 * A adapter class that connects RecyclerView and List of messages.
 *
 * @author DuRuYao
 * Create 19/03/27
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Messages> messagesList;
    private Context context;

    /**
     * An internal class to cache instance of controls.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        View messagesView;
        TextView messagesAddress;
        TextView messagesBody;

        /**
         * Constructor of ViewHolder class.
         *
         * @param view The outermost layout of item of RecyclerView.
         */
        private ViewHolder(View view) {
            super(view);
            messagesView = view;
            messagesAddress = (TextView) view.findViewById(R.id.messages_address);
            messagesBody = (TextView) view.findViewById(R.id.messages_body);
        }
    }

    /**
     * Constructor of MessagesAdapter class to import data list.
     *
     * @param context      Current context.
     * @param messagesList List of messages.
     */
    public MessagesAdapter(Context context, List<Messages> messagesList) {
        this.messagesList = messagesList;
        this.context = context;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_item,
                parent, false);
        /* Create instance of ViewHolder by passing the view parameter. */
        final ViewHolder holder = new ViewHolder(view);

        holder.messagesView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Messages messages = messagesList.get(position);
                String toastText = "Address: " + messages.getAddress() + "\nPerson:  " + messages.getPerson() + "\nDate: " + messages.getDate() + "\nType: "
                        + messages.getType() + "\nRead: " + messages.getRead() + "\nBody: " + messages.getBody();
                Toast.makeText(v.getContext(), toastText, Toast.LENGTH_SHORT).show();

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
        Messages messages = messagesList.get(position);
        holder.messagesAddress.setText(messages.getContactName(this.context));
        holder.messagesBody.setText(messages.getBody());
    }

    /**
     * Get number of item of RecyclerView.
     *
     * @return A number.
     */
    @Override
    public int getItemCount() {
        return messagesList.size();
    }

}
