package ac.th.itsci.durable.util;



public class VerifyDateString {

	private String Years;
	private String DateStart;
	private String DateEnd;
	
	public String getYears() {
		return Years;
	}
	public void setYears(String years) {
		Years = years;
	}
	public String getDateStart() {
		return DateStart;
	}
	public void setDateStart(String dateStart) {
		DateStart = dateStart;
	}
	public String getDateEnd() {
		return DateEnd;
	}
	public void setDateEnd(String dateEnd) {
		DateEnd = dateEnd;
	}
	public VerifyDateString(String years, String dateStart, String dateEnd) {
		super();
		Years = years;
		DateStart = dateStart;
		DateEnd = dateEnd;
	}
	
	
	public VerifyDateString() {
		super();
	
	}
	
}
