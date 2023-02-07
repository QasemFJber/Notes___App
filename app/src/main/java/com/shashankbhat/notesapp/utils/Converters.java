package com.shashankbhat.notesapp.utils;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by SHASHANK BHAT on 22-Jul-20.
 */
public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}