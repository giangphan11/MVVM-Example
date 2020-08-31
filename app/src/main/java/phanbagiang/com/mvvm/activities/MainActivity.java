package phanbagiang.com.mvvm.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import phanbagiang.com.mvvm.Note;
import phanbagiang.com.mvvm.NoteViewModel;
import phanbagiang.com.mvvm.R;
import phanbagiang.com.mvvm.adapter.NoteAdapter;

public class MainActivity extends AppCompatActivity {
    MaterialToolbar toolbar;

    private NoteViewModel noteViewModel;
    private LiveData<List<Note>> getAllNotes;

    private RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    private FloatingActionButton btn_add_note;

    public static final int REQUEST_CODE=113;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        noteViewModel=ViewModelProviders.of(this).get(NoteViewModel.class);
        getAllNotes=noteViewModel.getAllNotes();
        getAllNotes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // update recyclerview
                noteAdapter.addNote(notes);
            }
        });
        addEvents();
    }
    private void addEvents(){
        btn_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }
    private void addControls(){
        recyclerView=findViewById(R.id.recycler_view);
        btn_add_note=findViewById(R.id.btn_add_note);
        noteAdapter=new NoteAdapter(getApplicationContext());
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==114 &&data!=null){
            String title=data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String des=data.getStringExtra(AddNoteActivity.EXTRA_DES);
            int pro=data.getIntExtra(AddNoteActivity.EXTRA_PRO,1);
            Note note=new Note(title,des,pro);
            noteViewModel.insertNote(note);
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Note not saved !", Toast.LENGTH_SHORT).show();
        }
    }
}