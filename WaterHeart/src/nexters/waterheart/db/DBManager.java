package nexters.waterheart.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import nexters.waterheart.dto.Write;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {
	// All Static variables
	// Database Version private
	private final Context context;
	private DBOpenHelper dbHelper;
	private SQLiteDatabase db;
	public static int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "heartManager";

	// Contacts table name
	static final String DATABASE_TABLE_MAIN_NAME = "waterHeartDB";
	static final String DATABASE_TABLE_SUB_NAME = "completeDB";

	// Contacts Table Columns names
	private static final String KEY_NO = "no";
	private static final String KEY_DATE = "date";
	private static final String KEY_WATER = "water";
	private static final String KEY_COMPLETE = "complete";

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = null;

	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String CREATE_TABLE_MAIN = "CREATE TABLE "
					+ DATABASE_TABLE_MAIN_NAME + "(" + KEY_NO
					+ " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT NOT NULL,"
					+ KEY_WATER + " TEXT," + KEY_COMPLETE + " TEXT" + ")";
			String CREATE_TABLE_SUB = "CREATE TABLE " + DATABASE_TABLE_SUB_NAME
					+ "(" + KEY_DATE + " TEXT PRIMARY KEY," + KEY_COMPLETE
					+ " TEXT" + ")";
			db.execSQL(CREATE_TABLE_MAIN);
			// db.execSQL(CREATE_TABLE_SUB);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			// Drop older table if existed
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_MAIN_NAME);
			// db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SUB_NAME);
			// Create tables again
			onCreate(db);
		}
	}

	public DBManager(Context context, int version) {
		this.context = context;
		DATABASE_VERSION = version;
		dbHelper = new DBOpenHelper(context);
	}

	public void open() throws SQLiteException {
		try {
			db = dbHelper.getWritableDatabase(); // Write mode Open
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase(); // Read mode Open
		}
	}

	// close DB
	public void close() {
		db.close();
	}

	// insert new write
	public void addWrite(Write write) {
		ContentValues values = new ContentValues();
		date = new Date();

		values.put(KEY_DATE, dateFormat.format(date));
		values.put(KEY_WATER, write.getWater());
		values.put(KEY_COMPLETE, write.getComplete());

		// Inserting Row
		db.insert(DATABASE_TABLE_MAIN_NAME, null, values);
	}

	// select a write by no
	public Write getWrite(int no) {
		Cursor cursor = db.query(DATABASE_TABLE_MAIN_NAME, new String[] {
				KEY_NO, KEY_DATE, KEY_WATER, KEY_COMPLETE }, KEY_NO + "=?",
				new String[] { String.valueOf(no) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Write write = new Write();
		write.setNo(Integer.parseInt(cursor.getString(0)));
		write.setDate(cursor.getString(1));
		write.setWater(cursor.getString(2));
		write.setComplete(cursor.getString(3));
		return write;

	}

	public void oldDatadelete(String s) {

		Calendar calendar = new GregorianCalendar(Locale.KOREA);
		calendar.add(Calendar.DATE, -7);
		date = calendar.getTime();

		db.delete(DATABASE_TABLE_MAIN_NAME, KEY_DATE + " = ?",
				new String[] { s });
	}

	// select all Write
	public List<Write> getCompleteWrites() {
		List<Write> writeList = new ArrayList<Write>();

		// Select All Query
		String selectQuery = "SELECT * FROM " + DATABASE_TABLE_MAIN_NAME
				+ " WHERE " + KEY_COMPLETE + " = ?";
		Cursor cursor = db.rawQuery(selectQuery, new String[] {"true"});

		// looping through all rows and adding to list
		if (cursor.moveToLast()) {
			do {
				Write write = new Write();
				write.setNo(Integer.parseInt(cursor.getString(0)));
				write.setDate(cursor.getString(1));
				write.setWater(cursor.getString(2));
				write.setComplete(cursor.getString(3));

				writeList.add(write);
			} while (cursor.moveToPrevious());
		}

		// return contact list
		return writeList;
	}

	public void deleteWrite(int no) {
		DBManager manager = new DBManager(null, DATABASE_VERSION);
		db.delete(DATABASE_TABLE_MAIN_NAME, KEY_NO + " = ?",
				new String[] { String.valueOf(no) });
	}

	public int getWritesCount() {
		String countQuery = "SELECT * FROM " + DATABASE_TABLE_MAIN_NAME;
		Cursor cursor = db.rawQuery(countQuery, null);
		return cursor.getCount();
	}

	// public void updatedb() {
	// int version = db.getVersion();
	// String log = "version" + version;
	// Log.d("version: ", log);
	// version += 1;
	// DBManager dbincrement = new DBManager(context, version);
	// }
	// no use?..
	// // update
	// public int updateWrite(Write write) {
	// ContentValues values = new ContentValues();
	// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// Date date = new Date();
	//
	// values.put(KEY_DATE, dateFormat.format(date));
	// values.put(KEY_REWARD_TEXT, write.getRewardText());
	// values.put(KEY_REWARD_IMAGE, write.getRewardImage());
	//
	// // updating row
	// return db.update(DATABASE_TABLE, values, KEY_NO + " = ?",
	// new String[] { String.valueOf(write.getNo()) });
	//

}
