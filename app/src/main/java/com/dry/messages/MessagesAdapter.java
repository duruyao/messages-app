package com.dry.messages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import top.gpg2.messages.R;

import java.util.List;

/**
 * A adapter class that connects RecyclerView and List of messages.
 *
 * @author DuRuYao
 * Create 19/03/27
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Messages> messagesList;
    private Context context;
    private final int TYPE_RECEIVED = 1;
    private final int TYPE_SENT = 2;

    /**
     * An internal class to cache instance of controls.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        View messagesView;
        TextView smsDate;
        TextView smsBodyLeft;
        TextView smsBodyRight;
        CardView layoutLeft;
        CardView layoutRight;

        /**
         * Constructor of ViewHolder class.
         *
         * @param view The outermost layout of item of RecyclerView.
         */
        private ViewHolder(View view) {
            super(view);
            messagesView = view;
            smsDate = (TextView) view.findViewById(R.id.sms_date);
            smsBodyLeft = (TextView) view.findViewById(R.id.sms_body_left);
            smsBodyRight = (TextView) view.findViewById(R.id.sms_body_right);
            layoutLeft = (CardView) view.findViewById(R.id.layout_left);
            layoutRight = (CardView) view.findViewById(R.id.layout_right);
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
        holder.smsDate.setText(messages.getDate());
        if (messages.getType() == TYPE_RECEIVED) {
            holder.smsBodyLeft.setText(messages.getBody());
            holder.layoutLeft.setVisibility(View.VISIBLE);
            holder.layoutRight.setVisibility(View.GONE);
        } else if (messages.getType() == TYPE_SENT) {
            holder.smsBodyRight.setText(messages.getBody());
            holder.layoutRight.setVisibility(View.VISIBLE);
            holder.layoutLeft.setVisibility(View.GONE);
        }

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
