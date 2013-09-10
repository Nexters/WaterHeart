package nexters.waterheart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import nexters.waterheart.db.DBManager;
import nexters.waterheart.dto.Write;
import android.content.Context;

public class HeartManager {
	Context context;
	//Activity activity;
	DBManager db = null;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Calendar calendar = new GregorianCalendar(Locale.KOREA);
	
	public HeartManager(Context context) {
		this.context = context;
		db = new DBManager(context, DBManager.DATABASE_VERSION);
		db.open();
	}

	public void init() {
		Write write = new Write();
		Date date = new Date();

		if (db.getWritesCount() == 0) {
			write.setDate(dateFormat.format(date));
			write.setWater("0");
			write.setComplete("false");
			db.addWrite(write);
		}
	}

	public int mainHeartShow() {
		int no = db.getWritesCount();
		Date date = new Date();
		String s = String.valueOf(dateFormat.format(date));
		Write write = db.getWrite(no);

		return Integer.parseInt(write.getWater());
	}
	
	public void heartReset() {
		int no = db.getWritesCount();
		Date date = new Date();
		Write write = db.getWrite(no);
		
		write.setComplete("true");
		db.addWrite(write);

		write.setWater("0");
		write.setComplete("false");
		write.setDate(dateFormat.format(date));
		db.addWrite(write);
	}

	public int mainOnCupClicked(int cup) { // 한솔아 cup의 용량을 넘겨줘
		int no;
		int water = cup;
		Write write = new Write();

		no = db.getWritesCount();
		write = db.getWrite(no);
		water += Integer.parseInt(write.getWater());

		write.setComplete("false");
		write.setWater(String.valueOf(water));
		db.addWrite(write);

		no = db.getWritesCount();
		write = db.getWrite(no);
		water = Integer.parseInt(write.getWater());

		return water; // 현재까지 마신 물의 양 반환이니까 메인에서 양 출력할 때 사용하면 됨
	}

	public int mainOnBackClicked() {
		Write write = new Write();
		int no;
		int water = 0;

		no = db.getWritesCount();
		write = db.getWrite(no);

		if (write.getWater().equals("0")) {
			return water;
		} else {
			db.deleteWrite(no);
			no = db.getWritesCount();
			write = db.getWrite(no);
			water = Integer.parseInt(write.getWater());

		}
		return water;
	}

	public List<Write> onHistoryPage() {
		Date date = new Date();
		List<Write> writes = db.getCompleteWrites();
		String s;

//		calendar.add(Calendar.DATE, -7);
//		date = calendar.getTime();
//
//		s = String.valueOf(dateFormat.format(date));
//		for (Write w : writes) {
//			if (w.getDate().equals(s)) {
//				db.oldDatadelete(s);
//			}
//		}
		// 7일전 데이터베이스 삭제 필요

		return writes;
	}
}
