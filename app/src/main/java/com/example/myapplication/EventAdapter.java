package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<EventModel> events;
    private Context context;
    private EventClickListener listener;

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

        EventViewHolder(View itemView) {
            super(itemView);
            eventNameText = itemView.findViewById(R.id.eventNameText);
            dateText = itemView.findViewById(R.id.dateText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
