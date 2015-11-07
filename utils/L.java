package utils;

import android.util.Log;

/**
 * 全局log开关
 * 自动设置TAG为当前类名
 */
public class L {
	public static boolean LOG_OPEN = true;
	
	public void setLogOpen(boolean open) {
		LOG_OPEN = open;
	}
	
	private static String getTag() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];

        String className = stackTrace.getClassName();
        String tag = className.substring(className.lastIndexOf('.') + 1)
                + "." + stackTrace.getMethodName() + "#" + stackTrace.getLineNumber();
        return tag;
    }

    public static void d(String message, Object... args) {
    	if(!LOG_OPEN) return ;
        message = formatMessage(message, args);
        Log.d(getTag(), message);
    }

    public static void i(String message, Object... args) {
    	if(!LOG_OPEN) return ;
        message = formatMessage(message, args);
        Log.i(getTag(), message);

    }

    public static void w(String message, Object... args) {
    	if(!LOG_OPEN) return ;
        message = formatMessage(message, args);
        Log.w(getTag(), message);
    }

    public static void w(Throwable e) {
    	if(!LOG_OPEN) return ;
        e.printStackTrace();
    }

    public static void e(String message, Object... args) {
    	if(!LOG_OPEN) return ;
        message = formatMessage(message, args);
        Log.e(getTag(), message);
    }

    public static void e(Throwable e) {
    	if(!LOG_OPEN) return ;
        e.printStackTrace();
    }

    public static void v(String message, Object... args) {
    	if(!LOG_OPEN) return ;
        message = formatMessage(message, args);
        Log.v(getTag(), message);
    }

    private static String formatMessage(String message, Object... args) {
        if (message == null) {
            return "";
        }
        if (args != null && args.length > 0) {
            try {
                return String.format(message, args);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}
