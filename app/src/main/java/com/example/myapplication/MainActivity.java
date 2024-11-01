package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventAdapter.EventClickListener {
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private List<EventModel> eventList;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.eventsRecyclerView);
        searchEditText = findViewById(R.id.searchEditText);

        // Initialize event list
        eventList = new ArrayList<>();
        eventList.add(new EventModel("Battle of Bach Dang", "938",
                "Ngo Quyen defeated the Southern Han army on the Bach Dang River."));
        eventList.add(new EventModel("Tran Dynasty", "1225",
                "The Tran Dynasty was established, known for repelling Mongol invasions."));
        // Add more events as needed

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(this, eventList, this);
        recyclerView.setAdapter(adapter);

        // Setup search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterEvents(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterEvents(String query) {
        List<EventModel> filteredList = new ArrayList<>();
        for (EventModel event : eventList) {
            if (event.getEventName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(event);
            }
        }
        adapter.updateEvents(filteredList);
    }

    @Override
    public void onEditClick(EventModel event, int position) {
        // Show edit dialog
        showEditDialog(event, position);
    }

    @Override
    public void onDeleteClick(EventModel event, int position) {
        // Show delete confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Confirm deletion")
                .setMessage("Are you sure you want to delete this event?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    eventList.remove(position);
                    adapter.notifyItemRemoved(position);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditDialog(EventModel event, int position) {
        // Create dialog with edit form
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_event, null);
        EditText eventNameEdit = dialogView.findViewById(R.id.eventNameEdit);
        EditText dateEdit = dialogView.findViewById(R.id.dateEdit);
        EditText descriptionEdit = dialogView.findViewById(R.id.descriptionEdit);

        eventNameEdit.setText(event.getEventName());
        dateEdit.setText(event.getDate());
        descriptionEdit.setText(event.getDescription());

        new AlertDialog.Builder(this)
                .setTitle("Edit Event")
                .setView(dialogView)
                .setPositiveButton("Save", (dialog, which) -> {
                    event.setEventName(eventNameEdit.getText().toString());
                    event.setDate(dateEdit.getText().toString());
                    event.setDescription(descriptionEdit.getText().toString());
                    adapter.notifyItemChanged(position);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
