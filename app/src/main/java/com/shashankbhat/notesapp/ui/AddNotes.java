package com.shashankbhat.notesapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.shashankbhat.notesapp.R;
import com.shashankbhat.notesapp.databinding.ActivityAddNotesBinding;
import com.shashankbhat.notesapp.room.Note;
import com.shashankbhat.notesapp.viewmodel.AddNotesViewModel;

import java.util.Calendar;

import static com.shashankbhat.notesapp.utils.Constants.PRIORITY_HIGH;
import static com.shashankbhat.notesapp.utils.Constants.PRIORITY_LOW;
import static com.shashankbhat.notesapp.utils.Constants.PRIORITY_MED;

public class AddNotes extends AppCompatActivity {

    private int priority = 1;
    private Note note;
    private Calendar todayCalender = Calendar.getInstance();
    private ActivityAddNotesBinding binding;
    private AddNotesViewModel addNotesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_notes);
        binding.setLifecycleOwner(this);

        addNotesViewModel = ViewModelProviders.of(this).get(AddNotesViewModel.class);

        note = (Note) getIntent().getSerializableExtra("Note");

        if(note !=null) updateUI();
        else clearUI();

        binding.setPriorityListener(view -> setPriority(view.getId()));
        binding.setSaveListener(view -> saveNote());

    }

    private void clearUI() {
        note = new Note(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), "","", priority);
        binding.priority1.setChecked(true);
        binding.setTitle("");
        binding.setDescription("");
        priority = 1;
    }

    private void updateUI() {
        binding.setTitle(note.getTitle());
        binding.setDescription(note.getDescription());
        priority = note.getPriority();

        todayCalender.setTimeInMillis(note.getFinishBefore().getTime());
        binding.datePicker.updateDate(todayCalender.get(Calendar.YEAR), todayCalender.get(Calendar.MONTH), todayCalender.get(Calendar.DAY_OF_MONTH));

        switch (priority){
            case 2: binding.priority2.setChecked(true);
                break;
            case 3: binding.priority3.setChecked(true);
                break;
            default: binding.priority1.setChecked(true);
        }
    }

    private void saveNote(){
        String title = binding.getTitle();
        String description = binding.getDescription();
        int year = binding.datePicker.getYear();
        int month = binding.datePicker.getMonth();
        int day = binding.datePicker.getDayOfMonth();

        Calendar date = Calendar.getInstance();
        date.set(year, month, day, 0, 0, 0);

        if(title!=null && description!=null && !title.isEmpty() && !description.isEmpty()){
            note.setTitle(title);
            note.setDescription(description);
            note.setFinishBefore(date.getTime());
            note.setPriority(priority);
            note.setUpdatedDate(Calendar.getInstance().getTime());

            addNotesViewModel.saveNote(note);

            Snackbar.make(binding.linearLayout, "Notes saved", Snackbar.LENGTH_SHORT).show();
            clearUI();
        }else{
            Snackbar.make(binding.linearLayout, "Some fields are empty", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setPriority(int priority){
        switch (priority){
            case R.id.priority1:
                this.priority = PRIORITY_LOW;
                break;
            case R.id.priority2:
                this.priority = PRIORITY_MED;
                break;
            case R.id.priority3:
                this.priority = PRIORITY_HIGH;
                break;
        }
    }

}