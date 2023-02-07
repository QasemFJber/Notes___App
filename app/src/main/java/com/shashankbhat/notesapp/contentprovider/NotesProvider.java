package com.shashankbhat.notesapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shashankbhat.notesapp.room.NoteDao;
import com.shashankbhat.notesapp.room.NotesDatabase;

import java.util.Objects;

import static com.shashankbhat.notesapp.utils.Constants.NOTES_TABLE;

/**
 * Created by SHASHANK BHAT on 08-Sep-20.
 */
public class NotesProvider extends ContentProvider {

    private static final String AUTHORITY = "com.shashankbhat.notesapp";
    public static final String CONTENT_URI = String.valueOf(Uri.parse("content://" + AUTHORITY + "/" + NOTES_TABLE));

//    content://com.shashankbhat.notesapp/note_table

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private NoteDao noteDao;

    static {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.

         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        uriMatcher.addURI(AUTHORITY, NOTES_TABLE, 1);

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.example.app.provider/table3/3" matches, but
         * "content://com.example.app.provider/table3 doesn't.
         */
    }


    @Override
    public boolean onCreate() {
        noteDao = NotesDatabase.getInstance(getContext()).getNoteDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projetion, @Nullable String selection, @Nullable String[] selectionArg, @Nullable String order) {

        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case 1:
                cursor = noteDao.getAllNotesCursor();
                ContentResolver contentResolver = Objects.requireNonNull(getContext()).getContentResolver();
                cursor.setNotificationUri(contentResolver, uri);
                return cursor;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 1:
                return "vnd.android.cursor.dir/"+NOTES_TABLE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

}
