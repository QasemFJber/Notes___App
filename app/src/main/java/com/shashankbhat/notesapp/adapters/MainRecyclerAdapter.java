package com.shashankbhat.notesapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shashankbhat.notesapp.R;
import com.shashankbhat.notesapp.room.Note;
import com.shashankbhat.notesapp.ui.AddNotes;
import com.shashankbhat.notesapp.utils.DateFormatUtil;
import com.shashankbhat.notesapp.view.CustomPriorityView;

/**
 * Created by SHASHANK BHAT on 19-Jul-20.
 */
public class MainRecyclerAdapter extends PagedListAdapter<Note, MainRecyclerAdapter.MyViewHolder> {

    private Context context;

    public MainRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)  {
        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.layout_main_card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Note note = getItem(position);
        if(note!=null)
            holder.bindData(note);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, desc, date;
        ImageButton editButton;

        //Custom component
        CustomPriorityView priorityView;

        LinearLayout layoutBackground;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            editButton = itemView.findViewById(R.id.edit);
            editButton.setOnClickListener(this);
            date = itemView.findViewById(R.id.date_text_view);
            layoutBackground = itemView.findViewById(R.id.layout_background);

            //Custom component
            priorityView = itemView.findViewById(R.id.customPriorityView);
        }

        private void bindData(Note note) {
            title.setText(note.getTitle());
            desc.setText(note.getDescription());
            layoutBackground.getBackground().setTint(getPriorityCardColor(note.getPriority()));

            date.setText(DateFormatUtil.getStandardDate(note.getFinishBefore()));
            //Custom component
            priorityView.setPriority(note.getPriority());
        }

        @Override
        public void onClick(View v) {

            Pair<View, String> pair1 = Pair.create(title, context.getResources().getString(R.string.title));
            Pair<View, String> pair2 = Pair.create(desc, context.getResources().getString(R.string.description));

            //noinspection unchecked
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, pair1, pair2);

            Intent intent = new Intent(v.getContext(), AddNotes.class);
            intent.putExtra("Note", getItem(getAdapterPosition()));
            context.startActivity(intent, options.toBundle());
        }
    }

    private int getPriorityCardColor(int priority){

        switch (priority){
            case 2: return ContextCompat.getColor(context, R.color.random_color_2);
            case 3: return ContextCompat.getColor(context, R.color.random_color_3);
            default: return ContextCompat.getColor(context, R.color.random_color_1);
        }

    }

    public static DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getPriority();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);
        }
    };
}
