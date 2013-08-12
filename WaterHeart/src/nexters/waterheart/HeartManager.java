package nexters.waterheart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import nexters.waterheart.db.DBManager;
import nexters.waterheart.dto.Write;
import android.app.Activity;

public class HeartManager {

	Activity activity;
	DBManager db = null;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = new GregorianCalendar(Locale.KOREA);

	public HeartManager(Activity activity) {
		this.activity = activity;
		db = new DBManager(this.activity, DBManager.DATABASE_VERSION);
		db.open();
	}

	public int mainOnCupClicked(int cup) { // cup의 용량을 넘겨줘
		int no;
		int water = cup;
		Write write = new Write();

		no = db.getWritesCount();
		// write = db.getWrite(no);
		// String log = "no : " + no + " date : " + write.getDate();
		// Log.d("no: ", log);
		write = db.getWrite(no);
		water += Integer.parseInt(write.getWater());

		write.setWater(String.valueOf(water));
		db.addWrite(write);

		no = db.getWritesCount();
		write = db.getWrite(no);
		water = Integer.parseInt(write.getWater());

		return water; // 현재까지 마신 물의 양 반환이니까 메인에서 양 출력할 때 사용하면 됨
	}

	public void init() {
		Write write = new Write();
		Date date = new Date();

		write.setDate(dateFormat.format(date));
		write.setWater("0");
		db.addWrite(write);
	}

	public int mainOnBackClicked() {
		Date date = new Date();
		Write write = new Write();
		int no;
		int water = 0;

		no = db.getWritesCount();
		write = db.getWrite(no);

//		calendar.add(Calendar.DATE, -1);
//		date = calendar.getTime();
//		if (write.getDate().equals(String.valueOf(dateFormat.format(date))))
//			return 0;
//		else {
//			db.deleteWrite(no--);
//			write = db.getWrite(no);
//			water = Integer.parseInt(write.getWater());
//		}
		if (write.getWater().equals("0")) {
			water = Integer.parseInt(write.getWater());
		}
		else {
			db.deleteWrite(no);
			no = db.getWritesCount();
			write = db.getWrite(no);
			water = Integer.parseInt(write.getWater());
			
		}
		return water;
	}

	public List<Write> onHistoryPage() {
		Date date = new Date();
		List<Write> writes = db.getAllWrites();
		String s;

		calendar.add(Calendar.DATE, -7);
		date = calendar.getTime();

		s = String.valueOf(dateFormat.format(date));
		for (Write w : writes) {
			if (w.getDate().equals(s)) {
				db.oldDatadelete(s);
			}
		}
		// 7일전 데이터베이스 삭제 필요
		return writes;
	}
}
