package com.shashankbhat.notesapp.utils;

import android.text.Spanned;

import androidx.core.text.HtmlCompat;

import java.util.Date;

/**
 * Created by SHASHANK BHAT on 07-Sep-20.
 */
public class DateFormatUtil {

    static String []months = new String[]{"January", "February", "March", "April","May", "Jun", "July", "August", "September", "October", "November", "December"};

    public static Spanned getStandardDate(Date date){
        int day = date.getDate();
        int month = date.getMonth();
        return HtmlCompat.fromHtml("<b><i>"+months[month]+" "+day+"</i></b>", HtmlCompat.FROM_HTML_MODE_COMPACT);
    }
}
