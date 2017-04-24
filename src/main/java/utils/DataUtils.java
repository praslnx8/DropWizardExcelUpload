package utils;

import core.ConsoleLog;

/**
 * Created by prasi on 21/4/17.
 */
public class DataUtils
{
    public static int getInt(String data)
    {
        int i = 0;
        try
        {
            i = Integer.parseInt(data);
        }
        catch (Exception e)
        {
            ConsoleLog.e(e);
        }

        return i;
    }

    public static String getString(String s)
    {
        if(s == null)
        {
            s = "";
        }

        return s;
    }
}
