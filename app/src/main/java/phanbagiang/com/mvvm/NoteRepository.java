package phanbagiang.com.mvvm;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>>allNotes;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase= NoteDatabase.getInstance(application.getApplicationContext());
        noteDao=noteDatabase.noteDao();
        allNotes=noteDao.getAllNotes();
    }
    public void insertNote(Note note){
        new NoteAsyncTask(noteDao).execute(note);
    }
    public void updateNote(final Note note){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.updateNote(note);
            }
        }).start();
    }

    public void deleteNote(final Note note){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteNote(note);
            }
        }).start();
    }

    public void deleteAllNotes(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                noteDao.deleteAllNotes();
            }
        }).start();
    }

    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    private class NoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        public NoteAsyncTask(NoteDao noteDao){
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(notes[0]);
            return null;
        }
    }
}
