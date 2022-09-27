package ac.th.itsci.durable.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "InspectionSchedule")
public class InspectionSchedule {

	@Id
	@Column(columnDefinition = "varchar(20)")
	private String InspectionSchedule_ID;
	@Column(name = "Years")
	private String Years;
	@Column(name = "DateStart")
	private Date DateStart;
	@Column(name = "DateEnd")
	private Date DateEnd;
	
	

	public InspectionSchedule(String inspectionSchedule_ID, String years, Date dateStart, Date dateEnd) {
		super();
		InspectionSchedule_ID = inspectionSchedule_ID;
		Years = years;
		DateStart = dateStart;
		DateEnd = dateEnd;
	}

	public InspectionSchedule() {
		super();
	}

	public String getInspectionSchedule_ID() {
		return InspectionSchedule_ID;
	}


	public void setInspectionSchedule_ID(String inspectionSchedule_ID) {
		InspectionSchedule_ID = inspectionSchedule_ID;
	}


	public String getYears() {
		return Years;
	}

	public void setYears(String years) {
		Years = years;
	}

	public Date getDateStart() {
		return DateStart;
	}

	public void setDateStart(Date dateStart) {
		DateStart = dateStart;
	}

	public Date getDateEnd() {
		return DateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		DateEnd = dateEnd;
	}

		
	
}
