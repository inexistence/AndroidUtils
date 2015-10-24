package utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressLint("NewApi")
public class ViewUtils {
	
	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
	
	public static int generateViewId() {
        if (android.os.Build.VERSION.SDK_INT < 17 /*android.os.Build.VERSION_CODES.JELLY_BEAN_MR1*/) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue))
                    return result;                
            }
        } 
        else
            return android.view.View.generateViewId();
    }
	
	public static void setBackground(View v, Drawable drawable){
        if(android.os.Build.VERSION.SDK_INT >= 16 /*android.os.Build.VERSION_CODES.JELLY_BEAN*/)
            v.setBackground(drawable);
        else
            v.setBackgroundDrawable(drawable);
    }
	
	public static int dp2Px(Context context, int dp){
		return (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()) + 0.5f);
	}
	
	public static int sp2Px(Context context, int sp){
		return (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics()) + 0.5f);
	}
	
	public static ScreenInfo screenInfo(Activity activity){
		DisplayMetrics dm=new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return new ScreenInfo(dm .widthPixels, dm.heightPixels);
	}
	
	public static class ScreenInfo {
		public int widthPixels;
		public int heightPixels;
		public ScreenInfo() {
			
		}
		public ScreenInfo(int widthPixels, int heightPixels) {
			this.widthPixels = widthPixels;
			this.heightPixels = heightPixels;
		}
	}
}
