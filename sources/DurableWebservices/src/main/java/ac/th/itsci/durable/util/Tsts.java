package ac.th.itsci.durable.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tsts {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		String date = "31 ธันวาคม 2563";
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormatThai = new SimpleDateFormat("dd MMMM yyyy", new Locale("th", "TH"));
		Date dateStart = null;
		String dateStirngStartNew = null;

		Date dateStartNew = null;
		
		
		dateStart = simpleDateFormatThai.parse(date);
		dateStirngStartNew = simpledate.format(dateStart);
		dateStartNew = simpledate.parse(dateStirngStartNew);
		System.out.println((dateStartNew.getMonth()+1) + " " +(dateStartNew.getYear()+1900));
	}

}
