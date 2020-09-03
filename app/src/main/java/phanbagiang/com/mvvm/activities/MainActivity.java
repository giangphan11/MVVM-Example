package phanbagiang.com.mvvm.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import phanbagiang.com.mvvm.Note;
import phanbagiang.com.mvvm.NoteViewModel;
import phanbagiang.com.mvvm.R;
import phanbagiang.com.mvvm.adapter.NoteAdapter;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Toolbar toolbar;

    private NoteViewModel noteViewModel;
    private LiveData<List<Note>> getAllNotes;

    private RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    private FloatingActionButton btn_add_note;

    public static final int ADD_REQUEST_CODE=113;
    public static final int EDIT_REQUEST_CODE=11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addControls();
        noteViewModel=ViewModelProviders.of(this).get(NoteViewModel.class);
        getAllNotes=noteViewModel.getAllNotes();
        getAllNotes.observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // update recyclerview
                noteAdapter.submitList(notes);
            }
        });
        addEvents();
    }
    private void addEvents(){
        btn_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent,ADD_REQUEST_CODE);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.deleteNote(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted !", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent=new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID,note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRO,note.getPriority());
                intent.putExtra(AddEditNoteActivity.EXTRA_DES,note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_COLOR,note.getColor());
                intent.putExtra(AddEditNoteActivity.EXTRA_TEXT_COLOR,note.getText_color());
                startActivityForResult(intent,EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_deleteAllNote:
                deleteAllNote();
                return true;
        }
        return false;
    }
    private void deleteAllNote(){
        noteViewModel.deleteAllNotes();
        Toast.makeText(this, "deleted all notes!", Toast.LENGTH_SHORT).show();
    }

    private void addControls(){
        recyclerView=findViewById(R.id.recycler_view);
        btn_add_note=findViewById(R.id.btn_add_note);
        noteAdapter=new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_REQUEST_CODE && resultCode== RESULT_OK ){
            String title=data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String des=data.getStringExtra(AddEditNoteActivity.EXTRA_DES);
            int pro=data.getIntExtra(AddEditNoteActivity.EXTRA_PRO,1);
            int color=data.getIntExtra(AddEditNoteActivity.EXTRA_COLOR,-1);
            int text_color=data.getIntExtra(AddEditNoteActivity.EXTRA_TEXT_COLOR,-16777216);
            Note note=new Note(title,des,pro);
            note.setColor(color);
            if(text_color==0){
                note.setText_color(-16777216);
            }
            else{
                note.setText_color(text_color);
            }
            noteViewModel.insertNote(note);
           Toast.makeText(this, R.string.note_added, Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==EDIT_REQUEST_CODE && resultCode==RESULT_OK){
            int id= data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);


            if(id==-1){
                //Toast.makeText(this, "update failed !!!", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                String title=data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                String des=data.getStringExtra(AddEditNoteActivity.EXTRA_DES);
                int pro=data.getIntExtra(AddEditNoteActivity.EXTRA_PRO,1);
                int color=data.getIntExtra(AddEditNoteActivity.EXTRA_COLOR,0xffffffff);
                int text_color=data.getIntExtra(AddEditNoteActivity.EXTRA_TEXT_COLOR,0x00000000);
                Log.d(TAG, "onActivityResult2: "+text_color);
                Note note=new Note(title,des,pro);
                note.setColor(color);
                note.setText_color(text_color);
                note.setId(id);
                noteViewModel.updateNote(note);
                Toast.makeText(this, R.string.note_updated, Toast.LENGTH_SHORT).show();
            }
        }
        else{
            //Toast.makeText(this, "Note not saved !", Toast.LENGTH_SHORT).show();
        }
    }
}