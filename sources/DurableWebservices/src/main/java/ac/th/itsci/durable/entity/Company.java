package ac.th.itsci.durable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Company")
public class Company {

	@Id
	private int company_id;
	@Column(name = "companyname", columnDefinition = "varchar(255)")
	private String companyname;
	@Column(name = "address", columnDefinition = "varchar(255)")
	private String address;
	@Column(name = "tell", columnDefinition = "varchar(11)")
	private String tell;

	public Company() {
		super();
	}
 
	public Company(int company_id, String companyname) {
		super();
		this.company_id = company_id;
		this.companyname = companyname;
	}
	
	public Company(int company_id, String companyname, String address, String tell) {
		super();
		this.company_id = company_id;
		this.companyname = companyname;
		this.address = address;
		this.tell = tell;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

}
