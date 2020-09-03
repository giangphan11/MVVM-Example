package phanbagiang.com.mvvm;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase INSTANCE;
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"db_note")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        NoteDao noteDao;
        private populateDbAsyncTask(NoteDatabase noteDatabase){
            this.noteDao=noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            noteDao.insertNote(new Note("Title 1", "Description 1",1));
//            noteDao.insertNote(new Note("Title 2", "Description 2",2));
//            noteDao.insertNote(new Note("Title 3", "Description 3",3));
            return null;
        }
    }
}
