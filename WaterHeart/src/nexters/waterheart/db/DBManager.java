package nexters.waterheart.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nexters.waterheart.dto.Write;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "heartManager";

	// Contacts table name
	static final String DATABASE_TABLE = "waterHeartDB";

	// Contacts Table Columns names
	private static final String KEY_NO = "no";
	private static final String KEY_DATE = "date";
	private static final String KEY_REWARD_TEXT = "rewardText";
	private static final String KEY_REWARD_IMAGE = "rewardImage"; //왜 안만들어져 빡친다ㅡㅡ..

	public DBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_NO
				+ " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT NOT NULL,"
				+ KEY_REWARD_TEXT + " TEXT" + ")";
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

		// Create tables again
		onCreate(db);
	}

	// 새로운Write함수 추가
	public void addWrite(Write write) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		Date date = new Date(); 

		values.put(KEY_DATE, dateFormat.format(date));
		values.put(KEY_REWARD_TEXT, write.getRewardText());
//		values.put(KEY_REWARD_IMAGE, write.getRewardImage());

		// Inserting Row
		db.insert(DATABASE_TABLE, null, values);
		db.close(); // Closing database connection
	}

	// no값으로 Write가져오기
	public Write getWrite(int no) {
		return null;

	}

	// 모든 Write가져오기
	public List<Write> getAllWrite() {
		List<Write> writeList = new ArrayList<Write>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Write write = new Write();
				write.setNo(Integer.parseInt(cursor.getString(0)));
				write.setDate(cursor.getString(1));
				write.setRewardText(cursor.getString(2));
//				write.setRewardImage(cursor.getString(3));

				writeList.add(write);
			} while (cursor.moveToNext());
		}

		// return contact list
		return writeList;
	}

	//복붙하고 안고친부분
	// //Contact 정보 업데이트
	// public int updateContact(Write write) {
	// SQLiteDatabase db = this.getWritableDatabase();
	//
	// ContentValues values = new ContentValues();
	// values.put(KEY_DATE, "");
	// values.put(KEY_REWARD_TEXT, write.getRewardText());
	// values.put(KEY_REWARD_IMAGE, write.getRewardImage());
	//
	// // updating row
	// return db.update(DATABASE_TABLE, values, KEY_NO + " = ?",
	// new String[] { String.valueOf(write.getNo()) });
	// }
	//
	// // Contact 정보 삭제하기
	// public void deleteContact(Write write) {
	// SQLiteDatabase db = this.getWritableDatabase();
	// db.delete(DATABASE_TABLE, KEY_NO + " = ?",
	// new String[] { String.valueOf(write.getNo()) });
	// db.close();
	// }
	//
	// // Contact 정보 숫자
	// public int getContactsCount() {
	// String countQuery = "SELECT  * FROM " + DATABASE_TABLE;
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor cursor = db.rawQuery(countQuery, null);
	// cursor.close();
	//
	// // return count
	// return cursor.getCount();
	// }

}
