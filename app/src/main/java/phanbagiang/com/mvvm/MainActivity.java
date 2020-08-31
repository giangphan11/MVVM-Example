package phanbagiang.com.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import phanbagiang.com.mvvm.adapter.NoteAdapter;

public class MainActivity extends AppCompatActivity {
    private NoteViewModel noteViewModel;
    private LiveData<List<Note>> getAllNotes;

    private RecyclerView recyclerView;
    NoteAdapter noteAdapter;

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
    }
    private void addControls(){
        recyclerView=findViewById(R.id.recycler_view);
        noteAdapter=new NoteAdapter(getApplicationContext());
        recyclerView.setAdapter(noteAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
    }
}