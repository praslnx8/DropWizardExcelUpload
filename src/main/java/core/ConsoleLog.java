package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Logger;

/**
 * Created by prasi on 22/2/17.
 */
public class ConsoleLog
{
    private static Logger getLogger(String TAG)
    {
        Logger logger =  Logger.getLogger(TAG);

        return logger;
    }

    public static void i(String TAG, String message)
    {
        getLogger(TAG).info(message);
    }

    public static void e(Exception e)
    {
        e.printStackTrace();
        try {
            File file = new File("test.log");
            PrintStream ps = null;
            try {
                ps = new PrintStream(file);
                ps.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace(ps);
        }catch (Exception se){}
    }

    public static void w(String TAG, String message)
    {
        getLogger(TAG).warning(message);
    }

    public static void s(String TAG, String message)
    {
        getLogger(TAG).severe(message);
    }
}
