package utilities;

import java.util.Date;

public class DateUtils {


    public static String getTimeStamp(){
        Date date = new Date();
        final String timeStamp=date.toString().replaceAll(":","_").replaceAll(" ","_");
        return timeStamp;
    }
}
