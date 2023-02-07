package com.shashankbhat.notesapp.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.Calendar;


/**
 * Created by SHASHANK BHAT on 19-Jul-20.
 */
public class NoteRepository {

    private NoteDao mNoteDao;
    public LiveData<PagedList<Note>> allNotes;

    public NoteRepository(Application application) {
        NotesDatabase instance = NotesDatabase.getInstance(application);
        mNoteDao = instance.getNoteDao();
        allNotes = new LivePagedListBuilder<>(mNoteDao.getAllNotes(), /* Page Size */5).build();
    }

    public void repoInsert(Note note){
        new InsertTask(note).execute();
    }

    public void repoDelete(Note note){
        new DeleteTask(note).execute();
    }

    public void repoUpdate(Note note){
        new UpdateTask(note).execute();
    }

    public void repoDeleteAllNotes(){
        new DeleteAllFromDatabase().execute();
    }

    public Note repoListDateBasedNotes(){
        try {
            return new NearestDateTask().execute().get();
        }catch (Exception ex){}
        return null;
    }

    public LiveData<PagedList<Note>> getAllNotes(){
        return allNotes;
    }

    private class InsertTask extends AsyncTask<Void,Void,Void>{

        private Note note;
        public InsertTask(Note note) {
            this.note = note;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.insert(note);
            return null;
        }
    }

    private class DeleteTask extends AsyncTask<Void,Void,Void>{

        private Note note;
        public DeleteTask(Note note) {
            this.note = note;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.delete(note);
            return null;
        }
    }

    private class UpdateTask extends AsyncTask<Void,Void,Void>{

        private Note note;
        public UpdateTask(Note note) {
            this.note = note;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.update(note);
            return null;
        }
    }


    private class DeleteAllFromDatabase extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.deleteAllNotes();
            return null;
        }

    }

    public class NearestDateTask extends AsyncTask<Void,Void,Note>{

        @Override
        protected Note doInBackground(Void... voids) {
            return mNoteDao.getNearestDatedNotes(Calendar.getInstance().getTime());
        }
    }


}
