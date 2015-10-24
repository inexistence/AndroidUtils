package utils;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.TypedValue;

@SuppressLint("NewApi")
public class ThemeUtils {
	
	public static int getType(TypedArray array, int index){
        if(Build.VERSION.SDK_INT >= 21 /*Build.VERSION_CODES.LOLLIPOP*/)
            return array.getType(index);
        else{
            TypedValue value = array.peekValue(index);
            return value == null ? TypedValue.TYPE_NULL : value.type;
        }
    }

}
