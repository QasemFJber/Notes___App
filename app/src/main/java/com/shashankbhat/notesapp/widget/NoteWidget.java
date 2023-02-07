package com.shashankbhat.notesapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.shashankbhat.notesapp.MainActivity;
import com.shashankbhat.notesapp.R;
import com.shashankbhat.notesapp.room.Note;
import com.shashankbhat.notesapp.room.NoteDao;
import com.shashankbhat.notesapp.room.NotesDatabase;
import com.shashankbhat.notesapp.utils.DateFormatUtil;
import com.shashankbhat.notesapp.view.CustomPriorityView;

import java.lang.ref.WeakReference;
import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class NoteWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        new GetOneNote(context, appWidgetManager, appWidgetId).execute();
    }

    static class GetOneNote extends AsyncTask<Void, Void, Note>{
        RemoteViews views;
        WeakReference<Context> contextWeakReference;
        AppWidgetManager appWidgetManager;
        int appWidgetId;

        public GetOneNote(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
            views = new RemoteViews(context.getPackageName(), R.layout.note_widget);
            this.contextWeakReference = new WeakReference<>(context);
            this.appWidgetManager = appWidgetManager;
            this.appWidgetId = appWidgetId;
        }

        @Override
        protected Note doInBackground(Void... voids) {
            NoteDao noteDao = NotesDatabase.getInstance(contextWeakReference.get()).getNoteDao();
            return noteDao.getNearestDatedNotes(Calendar.getInstance().getTime());
        }

        @Override
        protected void onPostExecute(Note note) {
            super.onPostExecute(note);

            if(note!=null){
                views.setTextViewText(R.id.title, note.getTitle());
                views.setTextViewText(R.id.desc, note.getDescription());
                views.setTextViewText(R.id.date_text_view, DateFormatUtil.getStandardDate(note.getFinishBefore()));
                views.setImageViewBitmap(R.id.priorityImage, getPriorityBitmap(contextWeakReference, note));
                views.setOnClickPendingIntent(R.id.widget_layout, getPendingIntent(contextWeakReference.get()));
            }else{
                views.setTextViewText(R.id.title, "No notes found");
            }

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


    private static Bitmap getPriorityBitmap(WeakReference<Context> context, Note note){
        CustomPriorityView myView = new CustomPriorityView(context.get());
        myView.setPriority(note.getPriority());
        myView.measure(70, 70);
        myView.layout(0, 0, 70, 70);
        Bitmap bitmap = Bitmap.createBitmap(70, 70, Bitmap.Config.ARGB_8888);
        myView.draw(new Canvas(bitmap));

        return bitmap;
    }

    private static PendingIntent getPendingIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

