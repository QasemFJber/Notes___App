package com.shashankbhat.notesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.shashankbhat.notesapp.room.Note;
import com.shashankbhat.notesapp.room.NoteRepository;

/**
 * Created by SHASHANK BHAT on 19-Jul-20.
 */
public class MainActivityViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;

    public MainActivityViewModel(@NonNull Application application, int randomValue) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public void vmDelete(Note note) {
        noteRepository.repoDelete(note);
    }

    public void vmDeleteAllNotes() {
        noteRepository.repoDeleteAllNotes();
    }

    public LiveData<PagedList<Note>> getAllNotes() {
        return noteRepository.getAllNotes();
    }
}
