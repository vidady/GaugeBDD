package utilities;

import com.thoughtworks.gauge.ExecutionContext;

public class CommonUtils {


    public static synchronized String getStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String stringStackTrace = "<font color=" + "orange>" + "Stack Trace: " + "<br>" + "</font>";
        for (StackTraceElement st : stackTrace) {
            stringStackTrace = stringStackTrace + st + "<br>";
        }
        return stringStackTrace;
    }

    public static synchronized String getException(Throwable throwable) {
        String stringException = "<font color=" + "orange>" + "Exception: " + "<br>" + "</font>" + "<br>" + throwable.getMessage();

        return stringException;
    }

    public static synchronized String getStepErrorMessage(ExecutionContext context) {
        String stringException = "<font color=" + "orange>" + "Error Message: " + "<br>" + "</font>" + context.getCurrentStep().getErrorMessage();
        return stringException;
    }
}
