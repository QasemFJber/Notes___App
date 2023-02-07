package com.shashankbhat.notesapp.room;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.shashankbhat.notesapp.utils.Converters;
import com.shashankbhat.notesapp.utils.FileReadHelper;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.shashankbhat.notesapp.utils.Constants.DATABASE_NAME;

/**
 * Created by SHASHANK BHAT on 19-Jul-20.
 */
@Database(entities = {Note.class}, exportSchema = false, version = 1)
@TypeConverters({Converters.class})
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();
    public static NotesDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized NotesDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context, NotesDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(getCallback(context))
                    .build();
        }

        return instance;
    }

    public static Callback getCallback(Context context){

        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                databaseWriteExecutor.execute(()->{
                    for(Note note : FileReadHelper.readSongsFromJSON(context)){
                        instance.getNoteDao().insert(note);
                    }
                });
            }
        };
    }

}
