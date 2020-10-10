package com.example.manishaagro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DurationCalculate {

    public static String findDifference(String start_date,
                                        String end_date)
    {

        SimpleDateFormat sdf
                = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");

        String RetrunDiff="";

        // Try Class
        try {


            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);


            long difference_In_Time
                    = d2.getTime() - d1.getTime();


            long difference_In_Seconds
                    = TimeUnit.MILLISECONDS
                    .toSeconds(difference_In_Time)
                    % 60;

            long difference_In_Minutes
                    = TimeUnit
                    .MILLISECONDS
                    .toMinutes(difference_In_Time)
                    % 60;

            long difference_In_Hours
                    = TimeUnit
                    .MILLISECONDS
                    .toHours(difference_In_Time)
                    % 24;

            long difference_In_Days
                    = TimeUnit
                    .MILLISECONDS
                    .toDays(difference_In_Time)
                    % 365;

            long difference_In_Years
                    = TimeUnit
                    .MILLISECONDS
                    .toDays(difference_In_Time)
                    / 365l;


            System.out.print(
                    "Difference"
                            + " between two dates is: ");

            // Print result
            System.out.println(difference_In_Days
                    + " Days,"
                    + difference_In_Hours
                    + " Hrs,"
                    + difference_In_Minutes
                    + " Min,"
                    + difference_In_Seconds
                    + " Sec");
            RetrunDiff=difference_In_Days
                    + " Days,"
                    + difference_In_Hours
                    + " Hrs,"
                    + difference_In_Minutes
                    + " Min,"
                    + difference_In_Seconds
                    + " Sec";
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return  RetrunDiff;
    }
}
