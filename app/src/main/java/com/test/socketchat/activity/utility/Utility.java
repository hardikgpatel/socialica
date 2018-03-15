package com.test.socketchat.activity.utility;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lcom151-two on 3/13/2018.
 */

public class Utility {
    private Context context;
    private static final String TAG=Utility.class.getSimpleName();


    public Utility(Context context) {
        this.context = context;
    }

    public String convetDate(String timeStamp){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Format dateFormat = android.text.format.DateFormat.getLongDateFormat(context);
            Format timeFormat = android.text.format.DateFormat.getTimeFormat(context);
            return dateFormat.format(sdf.parse(timeStamp))+" "+timeFormat.format(sdf.parse(timeStamp));
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    public long payTimeDefrance(String createdAt){
        try{
            DateFormat userDateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            DateFormat dateFormatNeeded = new SimpleDateFormat("yyyy/MM/dd HH:MM:SS");
            Date date = null;
            date = new Date(createdAt);
            String crdate1 = dateFormatNeeded.format(date);

            // Date Calculation
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            crdate1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);

            // get current date time with Calendar()
            Calendar cal = Calendar.getInstance();
            String currenttime = dateFormat.format(cal.getTime());

            Date CreatedAt = null;
            Date current = null;
            try {
                CreatedAt = dateFormat.parse(crdate1);
                current = dateFormat.parse(currenttime);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // Get msec from each, and subtract.
            long diff = current.getTime() - CreatedAt.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.d("diffrance day",""+diffDays);
            return diffDays;

        }catch (Exception e){e.printStackTrace();}
        return 0;
    }
}
