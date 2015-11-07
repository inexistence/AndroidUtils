package utils;

import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

@SuppressLint("NewApi")
public class ViewUtils {

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

	/**
     * 创建快捷方式
     * 
     * @param cxt
     *            Context
     * @param icon
     *            快捷方式图标
     * @param title
     *            快捷方式标题
     * @param cls
     *            要启动的Activity类
     */
    public static void createDeskShortCut(Context cxt, int icon, String title,
            Class<?> cls) {
        // 创建快捷方式的Intent
        Intent shortcutIntent = new Intent(
                "com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重复创建
        shortcutIntent.putExtra("duplicate", false);
        // 需要现实的名称
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        // 快捷图片
        Parcelable ico = Intent.ShortcutIconResource.fromContext(
                cxt.getApplicationContext(), icon);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, ico);
        Intent intent = new Intent(cxt, cls);
        // 下面两个属性是为了当应用程序卸载时桌面上的快捷方式会删除
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        // 点击快捷图片，运行的程序主入口
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        // 发送广播。OK
        cxt.sendBroadcast(shortcutIntent);
    }

	public static int generateViewId() {
		if (android.os.Build.VERSION.SDK_INT < 17 /* android.os.Build.VERSION_CODES.JELLY_BEAN_MR1 */) {
			for (;;) {
				final int result = sNextGeneratedId.get();
				// aapt-generated IDs have the high byte nonzero; clamp to the
				// range under that.
				int newValue = result + 1;
				if (newValue > 0x00FFFFFF)
					newValue = 1; // Roll over to 1, not 0.
				if (sNextGeneratedId.compareAndSet(result, newValue))
					return result;
			}
		} else
			return android.view.View.generateViewId();
	}

	public static void setBackground(View v, Drawable drawable) {
		if (android.os.Build.VERSION.SDK_INT >= 16 /* android.os.Build.VERSION_CODES.JELLY_BEAN */)
			v.setBackground(drawable);
		else
			v.setBackgroundDrawable(drawable);
	}

	public static int dp2Px(Context context, int dp) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dp, context.getResources().getDisplayMetrics()) + 0.5f);
	}

	public static int sp2Px(Context context, int sp) {
		return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
				context.getResources().getDisplayMetrics()) + 0.5f);
	}

	/**
	 * Hides keyboard using currently focused view.<br/>
	 * Shortcat for
	 * {@link #hideSoftKeyboard(android.content.Context, android.view.View...)
	 * hideSoftKeyboard(activity, activity.getCurrentFocus())}.
	 */
	public static void hideSoftKeyboard(Activity activity) {
		hideSoftKeyboard(activity, activity.getCurrentFocus());
	}

	/**
	 * Uses given views to hide soft keyboard and to clear current focus.
	 * 
	 * @param context
	 *            Context
	 * @param views
	 *            Currently focused views
	 */
	public static void hideSoftKeyboard(Context context, View... views) {
		if (views == null)
			return;
		InputMethodManager manager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		for (View currentView : views) {
			if (null == currentView)
				continue;
			manager.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
			currentView.clearFocus();
		}
	}

	/**
	 * Shows soft keyboard and requests focus for given view.
	 */
	public static void showSoftKeyboard(Context context, View view) {
		if (view == null)
			return;
		InputMethodManager manager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		view.requestFocus();
		manager.showSoftInput(view, 0);
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T find(View parent, int viewId) {
		return (T) parent.findViewById(viewId);
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T find(Activity activity, int viewId) {
		return (T) activity.findViewById(viewId);
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T inflate(Context context, int layoutId) {
		return (T) LayoutInflater.from(context).inflate(layoutId, null, false);
	}

	public static <T extends View> T inflate(View root, int layoutId) {
		return inflateInternal(root, layoutId, false);
	}

	public static <T extends View> T inflateAndAttach(View root, int layoutId) {
		return inflateInternal(root, layoutId, true);
	}

	@SuppressWarnings("unchecked")
	private static <T extends View> T inflateInternal(View root, int layoutId,
			boolean attach) {
		if (root == null)
			throw new NullPointerException("Root view cannot be null");
		return (T) LayoutInflater.from(root.getContext()).inflate(layoutId,
				(ViewGroup) root, attach);
	}

	public static ScreenInfo screenInfo(Activity activity) {
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return new ScreenInfo(dm.widthPixels, dm.heightPixels);
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
