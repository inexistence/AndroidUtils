package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * 对Cursor进行排序
 */

public class SortCursor extends CursorWrapper implements Comparator<SortEntry> {
	public static int DESC = -1;
	public static int ASC = 1;
	int mSort = ASC;

	/**
	 * 
	 * @param cursor
	 * @param sort
	 *            {@link #ASC} or {@link #DESC}
	 */
	public SortCursor(Cursor cursor, String columnName, int sort) {
		super(cursor);
		mSort = sort;
		mCursor = cursor;
		if (mCursor != null && mCursor.getCount() > 0) {
			int i = 0;
			int column = cursor.getColumnIndexOrThrow(columnName);
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor
					.moveToNext(), i++) {
				SortEntry sortKey = new SortEntry();
				sortKey.key = cursor.getString(column);
				sortKey.order = i;
				sortList.add(sortKey);
			}
		}
		Collections.sort(sortList, this);
	}

	public SortCursor(Cursor cursor) {
		super(cursor);
	}

	Cursor mCursor;
	ArrayList<SortEntry> sortList = new ArrayList<SortEntry>();
	int mPos = 0;

	public static class SortEntry {
		public String key;
		public int order;
	}

	public int compare(SortEntry entry1, SortEntry entry2) {
		int resut = entry1.key.compareToIgnoreCase(entry2.key);
		if (mSort == ASC)
			return resut;
		return -resut;
	}

	public SortCursor(Cursor cursor, String columnName) {
		this(cursor, columnName, ASC);
	}

	public boolean moveToPosition(int position) {
		if (position >= 0 && position < sortList.size()) {
			mPos = position;
			int order = sortList.get(position).order;
			return mCursor.moveToPosition(order);
		}
		if (position < 0) {
			mPos = -1;
		}
		if (position >= sortList.size()) {
			mPos = sortList.size();
		}
		return mCursor.moveToPosition(position);
	}

	public boolean moveToFirst() {
		return moveToPosition(0);
	}

	public boolean moveToLast() {
		return moveToPosition(getCount() - 1);
	}

	public boolean moveToNext() {
		return moveToPosition(mPos + 1);
	}

	public boolean moveToPrevious() {
		return moveToPosition(mPos - 1);
	}

	public boolean move(int offset) {
		return moveToPosition(mPos + offset);
	}

	public int getPosition() {
		return mPos;
	}
}
