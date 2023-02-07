package com.shashankbhat.notesapp.utils;

import android.content.Context;

import androidx.annotation.NonNull;


import com.shashankbhat.notesapp.room.Note;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by SHASHANK BHAT on 23-Jul-20.
 */
public class FileReadHelper {

    private static String loadJSONFromAsset(@NonNull Context context, @NonNull String fileName) {
        String jsonString = null;

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];

            //noinspection ResultOfMethodCallIgnored
            inputStream.read(buffer);
            jsonString = new String(buffer, StandardCharsets.UTF_8);

            inputStream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("File read error :" +ex.getMessage());
        }
        return jsonString;
    }


    public static List<Note> readSongsFromJSON(Context context) {

        List<Note> songList = new ArrayList<>();

        String jsonString = FileReadHelper.loadJSONFromAsset(context, "notes.json");

        try {
            JSONArray songs = new JSONArray(jsonString);

            for (int index = 0; index < songs.length(); index++) {
                JSONObject song = songs.getJSONObject(index);

                String title = song.getString("title");
                String description = song.getString("description");
                int priority = song.getInt("priority");

                Date today = Calendar.getInstance().getTime();
                today.setYear(today.getYear()+1900);
                songList.add(new Note(today, today, title, description, priority));
            }

        } catch (Exception ex) {
            System.out.println("Json parse error :" + ex.getMessage());
        }
        return songList;
    }
}
