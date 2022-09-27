package ac.th.itsci.durable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "Major")
public class Major {
	
	@Id
	private int ID_Major;
	@Column(name = "Major_Name")
	private String major_Name;
	
	public Major() {
		super();
	}

	public Major(int iD_Major, String major_Name) {
		super();
		ID_Major = iD_Major;
		this.major_Name = major_Name;
	}

	public int getID_Major() {
		return ID_Major;
	}

	public void setID_Major(int iD_Major) {
		ID_Major = iD_Major;
	}

	public String getMajor_Name() {
		return major_Name;
	}

	public void setMajor_Name(String major_Name) {
		this.major_Name = major_Name;
	}

	
	

	
	

}
