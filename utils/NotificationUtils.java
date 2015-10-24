package utils;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationUtils {
	private static class SingletonHolder {
        static final NotificationUtils INSTANCE = new NotificationUtils();
    }

    public static NotificationUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public static void showSimpleNotification(Context context, int id, int icon, String title, String text) {
        showSimpleNotification(context, id, icon, title, text, (PendingIntent) null);
    }

    public static void showSimpleNotification(Context context, int id, int icon, String title, String text, Class<?> clazz) {
        Intent intent = new Intent();
        if (clazz != null) {
            intent = new Intent(context, clazz);
        }
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        showSimpleNotification(context, id, icon, title, text, pi);
    }
    
    public static void showSimpleNotification(Context context, int id, int icon, String title, String text, PendingIntent pi) {
        NotificationManager mNotifMan = null;
        NotificationCompat.Builder mBuilder = null;
        if(mNotifMan == null) {
        	mNotifMan = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        	mBuilder = new NotificationCompat.Builder(context);
        	mBuilder.setAutoCancel(true)
        	.setWhen(System.currentTimeMillis())
        	.setDefaults(NotificationCompat.DEFAULT_ALL)
        	.setOnlyAlertOnce(true)
        	.setSmallIcon(icon)
        	.setContentTitle(title)
        	.setContentText(text)
        	.setTicker(title);
        }
        if(pi!=null){
        	mBuilder.setContentIntent(pi);
        }
        Notification mNotification = mBuilder.build(); 
        mNotifMan.notify(id, mNotification);
    }

    public static void cancelNotification(Context context, int id) {
        NotificationManager mNotifMan = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifMan.cancel(id);
    }
}
