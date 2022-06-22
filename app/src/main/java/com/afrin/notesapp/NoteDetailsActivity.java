package com.afrin.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailsActivity extends AppCompatActivity {

    private TextView titleofNoDetails, contentofNoteDetails;
    FloatingActionButton gotoeditNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        titleofNoDetails = findViewById(R.id.titleofNoDetails);
        contentofNoteDetails = findViewById(R.id.contentofNoteDetails);
        gotoeditNote = findViewById(R.id.gotoeditNote);
        Toolbar toolbar = findViewById(R.id.toolbarofNodetails);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        gotoeditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),EditNoteActivity.class);
                intent.putExtra("title",data.getStringExtra("title"));
                intent.putExtra("content",data.getStringExtra("content"));
                intent.putExtra("noteId",data.getStringExtra("noteId"));
                v.getContext().startActivity(intent);

            }
        });

        contentofNoteDetails.setText(data.getStringExtra("content"));
        titleofNoDetails.setText(data.getStringExtra("title"));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }
}