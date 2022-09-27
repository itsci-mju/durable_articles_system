package ac.th.itsci.durable.entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "verifyinform")
public class verifyinform {
	@Id
	private int verify_id;
	
	@Column(name = "verify_date", length = 45)
	private Calendar verify_date;
	
	@Column(name = "verify_status", length = 255)
	private String verify_status;
	
	@Column(name = "verify_detail", length = 255)
	private String verify_detail;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Informid", referencedColumnName = "Informid")
    private inform_repair inform_repair = new inform_repair();



	public verifyinform(int verify_id, Calendar verify_date, String verify_status, String verify_detail) {
		super();
		this.verify_id = verify_id;
		this.verify_date = verify_date;
		this.verify_status = verify_status;
		this.verify_detail = verify_detail;

	}



	public verifyinform(int verify_id, Calendar verify_date, String verify_status, String verify_detail,
			ac.th.itsci.durable.entity.inform_repair inform_repair) {
		super();
		this.verify_id = verify_id;
		this.verify_date = verify_date;
		this.verify_status = verify_status;
		this.verify_detail = verify_detail;
		this.inform_repair = inform_repair;
	}



	public verifyinform() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getVerify_id() {
		return verify_id;
	}

	public void setVerify_id(int verify_id) {
		this.verify_id = verify_id;
	}

	public Calendar getVerify_date() {
		return verify_date;
	}

	public void setVerify_date(Calendar verify_date) {
		this.verify_date = verify_date;
	}

	public String getVerify_status() {
		return verify_status;
	}

	public void setVerify_status(String verify_status) {
		this.verify_status = verify_status;
	}

	public String getVerify_detail() {
		return verify_detail;
	}

	public void setVerify_detail(String verify_detail) {
		this.verify_detail = verify_detail;
	}

	public inform_repair getInform_repair() {
		return inform_repair;
	}

	public void setInform_repair(inform_repair inform_repair) {
		this.inform_repair = inform_repair;
	}
	
	
	
}
