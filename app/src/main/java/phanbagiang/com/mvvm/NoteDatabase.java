package phanbagiang.com.mvvm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase INSTANCE;
    public NoteDao noteDaol;

    public static synchronized NoteDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"db_note")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
