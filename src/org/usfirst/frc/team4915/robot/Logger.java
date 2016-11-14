package org.usfirst.frc.team4915.robot;

// Logger:
//  a simple class to "bunghole" all logging through. 
//  future enhancement:
//      * support logging to file
//      * runtime inference of logging level (practice vs competition)
//  usage:
//      Logger.getInstance().logDebug("here's my message");
public class Logger 
{
    private static Logger s_logger = new Logger();
    
    public static Logger getInstance() { return s_logger; }
    
    private enum level
    {
        DEBUG,
        INFO,
        NOTICE,
        WARNING,
        ERROR
    };
    private static int s_loglevel = level.DEBUG.ordinal();
    
    private Logger() {}

    public void logDebug(String msg)
    {
        if(s_loglevel <= level.DEBUG.ordinal())
        {
            logMsg("DEBUG  ", msg);
        }
    }
    
    public void logInfo(String msg)
    {
        if(s_loglevel <= level.INFO.ordinal())
        {
            logMsg("INFO   ", msg);
        }
    }
    
    public void logNotice(String msg)
    {
        if(s_loglevel <= level.NOTICE.ordinal())
        {
            logMsg("NOTICE ", msg);
        }
    }
    
    public void logWarning(String msg)
    {
        if(s_loglevel <= level.WARNING.ordinal())
        {
            logMsg("WARNING", msg);
        }
    }
    
    public void logError(String msg)
    {
        logMsg("ERROR  ", msg);
    }
    
    public void logException(Exception e)
    {
        logMsg("EXCEPT ", e.getMessage());
        e.printStackTrace();
    }

    private void logMsg(String lvl, String msg)
    {
        System.out.println(lvl + ": " + msg);
    }
 
}
