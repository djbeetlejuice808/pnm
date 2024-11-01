package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<EventModel> events;
    private Context context;
    private EventClickListener listener;

    // Định nghĩa màu sắc
    private final int COLOR_RED = Color.parseColor("#F44336");    // Màu đỏ
    private final int COLOR_BLUE = Color.parseColor("#2196F3");   // Màu xanh dương

    public interface EventClickListener {
        void onEditClick(EventModel event, int position);
        void onDeleteClick(EventModel event, int position);
    }

    public EventAdapter(Context context, List<EventModel> events, EventClickListener listener) {
        this.context = context;
        this.events = events;
        this.listener = listener;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        EventModel event = events.get(position);
        holder.eventNameText.setText(event.getEventName());
        holder.dateText.setText("Date: " + event.getDate());
        holder.descriptionText.setText(event.getDescription());

        // Set màu nền xen kẽ
        holder.itemBackground.setBackgroundColor(position % 2 == 0 ? COLOR_RED : COLOR_BLUE);

        holder.editButton.setOnClickListener(v -> {
            if (listener != null) listener.onEditClick(event, position);
        });

        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClick(event, position);
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateEvents(List<EventModel> newEvents) {
        events = newEvents;
        notifyDataSetChanged();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameText, dateText, descriptionText;
        Button editButton, deleteButton;
        LinearLayout itemBackground;

        EventViewHolder(View itemView) {
            super(itemView);
            itemBackground = itemView.findViewById(R.id.itemBackground);
            eventNameText = itemView.findViewById(R.id.eventNameText);
            dateText = itemView.findViewById(R.id.dateText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
