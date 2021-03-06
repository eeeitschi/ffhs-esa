package com.example.notizbloq_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    static NoteViewAdapter noteViewAdapter = null;
    ListView noteListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Notizbloq"); // Titel ändern

        noteListView = findViewById(R.id.listView);

        // Eine Liste mit allen gespeicherten Notizen abrufen
        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);

        // Wenn keine Notizen, Toast zeigen, sonst Listview mit dem Adapter füllen
        if (notes == null || notes.size() == 0) {
            Toast.makeText(this, "You have no notes yet.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // Adapter initialisieren mit der Liste von Notes
            noteViewAdapter = new NoteViewAdapter(this, notes);
            // Adapter mit der ListView verknüpfen
            noteListView.setAdapter(noteViewAdapter);
        }

        // Listener für einen ClickEvent zur Liste hinzufügen
        noteListView.setOnItemClickListener((parent, view, position, id) -> {
            // Den Filenamen der angeglickten Note speichern
            String fileName = ((Note) noteListView.getItemAtPosition(position)).getFileName();
            // NoteViewer Activity öffnen
            Intent viewNoteIntent = new Intent(getApplicationContext(), NoteViewer.class);
            // Die angeklickte Note mitgeben
            viewNoteIntent.putExtra("NOTE_FILE", fileName);
            startActivity(viewNoteIntent);
        });
    }

    @Override
    protected void onResume() {
        // ListView aktualisieren
        super.onResume();
        ArrayList<Note> notes = Utilities.getAllSavedNotes(this);
        if (notes == null || notes.size() == 0) {
            Toast.makeText(this, "You have no notes yet.", Toast.LENGTH_SHORT).show();
        } else {
            noteViewAdapter = new NoteViewAdapter(this, notes);
            noteListView.setAdapter(noteViewAdapter);
        }
    }

    @SuppressWarnings("unused")
    public void addNote(View view) {
        // Neue Notiz hinzufügen (bei Klick auf Button)
        Intent newNote = new Intent(this, NoteViewer.class);
        startActivity(newNote);
    }
}