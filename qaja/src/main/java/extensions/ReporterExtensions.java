package extensions;

import org.testng.Reporter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class ReporterExtensions {
    public static void log(String message){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        message = dateFormat.format(date) + " " + message;
        Reporter.log(message, true);
    }
}
