package com.shashankbhat.notesapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.shashankbhat.notesapp.room.Note;
import com.shashankbhat.notesapp.room.NoteRepository;
/**
 * Created by SHASHANK BHAT on 21-Jul-20.
 */
public class AddNotesViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;

    public AddNotesViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public void saveNote(Note note){
        noteRepository.repoInsert(note);
    }
}
