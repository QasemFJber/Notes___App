package com.shashankbhat.notesapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shashankbhat.notesapp.R;
import com.shashankbhat.notesapp.adapters.MainRecyclerAdapter;
import com.shashankbhat.notesapp.room.Note;
import com.shashankbhat.notesapp.viewmodel.MainActivityViewModel;
import com.shashankbhat.notesapp.viewmodel.MainViewModelFactory;

import java.util.Objects;

import static com.shashankbhat.notesapp.ui.SettingsFragment.KEY_TABLE_MODE;


public class ShowAllNotes extends Fragment {

    private MainActivityViewModel viewModel;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_all_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();
        FloatingActionButton fab = view.findViewById(R.id.floating_action_button);
        RecyclerView recycler = view.findViewById(R.id.main_rv);

        fab.setOnClickListener(this::moveToAddNotes);

//        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        MainViewModelFactory factory = new MainViewModelFactory(requireActivity().getApplication(), 10);
        viewModel = new ViewModelProvider(this, factory).get(MainActivityViewModel.class);

        setupRecyclerView(recycler);

//        val action = TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment(taskId)
//        findNavController().navigate(action)
    }

    private void moveToAddNotes(View view) {
        Intent intent = new Intent(view.getContext(), AddNotes.class);
        startActivity(intent);
    }

    private boolean isTabletModeEnabled() {

        PreferenceManager.setDefaultValues(context, R.xml.root_preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        return sharedPref.getBoolean(KEY_TABLE_MODE, false);
    }

    private void setupRecyclerView(RecyclerView recycler){

        /**
         * Checks whether it's tablet or standard mobile device
         * if table 2 column grid view
         * if mobile just a leaner layout
         */
        if(getResources().getBoolean(R.bool.isTablet) || isTabletModeEnabled())
            recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        else
            recycler.setLayoutManager(new LinearLayoutManager(context));

        MainRecyclerAdapter adapter = new MainRecyclerAdapter();
        recycler.setAdapter(adapter);

        setItemTouchHelper(recycler, adapter);
        viewModel.getAllNotes().observe(requireActivity(), adapter::submitList);
    }

    private void setItemTouchHelper(RecyclerView recyclerView, MainRecyclerAdapter adapter) {

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder v, @NonNull RecyclerView.ViewHolder v1) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Note note = Objects.requireNonNull(adapter.getCurrentList()).get(viewHolder.getAdapterPosition());
                viewModel.vmDelete(note);
//                deleteAlertDialog(note, false);
            }
        }).attachToRecyclerView(recyclerView);
    }


}