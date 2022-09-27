package ac.th.itsci.durable.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "RepairDurable")
public class RepairDurable {
	
	@Id
	@GeneratedValue
	private int repair_id;
	@Column(name = "repair_date", columnDefinition = "varchar(100)")
	private String repair_date;
	@Column(name = "repair_title", columnDefinition = "varchar(100)")
	private String repair_title;
	@Column(name = "repair_charges", columnDefinition = "varchar(100)")
	private String repair_charges;
	@Column(name = "repair_detail", columnDefinition = "varchar(100)")
	private String repair_detail;
	@Column(name = "picture_invoice", columnDefinition = "varchar(100)")
	private String picture_invoice;
	@Column(name = "picture_repairreport", columnDefinition = "varchar(100)")
	private String picture_repairreport;
	@Column(name = "picture_quatation", columnDefinition = "varchar(100)")
	private String picture_quatation;
	@Column(name = "picture_repair", columnDefinition = "varchar(100)")
	private String picture_repair;
	@Column(name = "Date_of_repair", columnDefinition = "varchar(100)")
	private String Date_of_repair;
	@Column(name = "Repair_status", columnDefinition = "varchar(100)")
	private String Repair_status;
	
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "Durable_code")
	private Durable durable;
	public Durable getDurable() {
		return durable;
	}
	public void setDurable(Durable durable) {
		this.durable = durable;
	}
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "company_id")
	private Company company;
	public Company getCompany() {
		return company;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "verify_id", referencedColumnName = "verify_id")
    private verifyinform verifyinform = new verifyinform();

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public RepairDurable() {
		super();
	}


	public RepairDurable(int repair_id, String repair_date, String repair_title, String repair_charges,
			String repair_detail, String picture_invoice, String picture_repairreport, String picture_quatation,
			String picture_repair, String date_of_repair, String repair_status) {
		super();
		this.repair_id = repair_id;
		this.repair_date = repair_date;
		this.repair_title = repair_title;
		this.repair_charges = repair_charges;
		this.repair_detail = repair_detail;
		this.picture_invoice = picture_invoice;
		this.picture_repairreport = picture_repairreport;
		this.picture_quatation = picture_quatation;
		this.picture_repair = picture_repair;
		Date_of_repair = date_of_repair;
		Repair_status = repair_status;
	}
	
	
	
	public RepairDurable(int repair_id, String repair_date, String repair_title, String repair_charges,
			String repair_detail, String picture_invoice, String picture_repairreport, String picture_quatation,
			String picture_repair, String date_of_repair, String repair_status, Durable durable, Company company,
			ac.th.itsci.durable.entity.verifyinform verifyinform) {
		super();
		this.repair_id = repair_id;
		this.repair_date = repair_date;
		this.repair_title = repair_title;
		this.repair_charges = repair_charges;
		this.repair_detail = repair_detail;
		this.picture_invoice = picture_invoice;
		this.picture_repairreport = picture_repairreport;
		this.picture_quatation = picture_quatation;
		this.picture_repair = picture_repair;
		Date_of_repair = date_of_repair;
		Repair_status = repair_status;
		this.durable = durable;
		this.company = company;
		this.verifyinform = verifyinform;
	}
	public String getRepair_status() {
		return Repair_status;
	}
	public void setRepair_status(String repair_status) {
		Repair_status = repair_status;
	}
	public String getPicture_repair() {
		return picture_repair;
	}
	
	public verifyinform getVerifyinform() {
		return verifyinform;
	}
	public void setVerifyinform(verifyinform verifyinform) {
		this.verifyinform = verifyinform;
	}
	public void setPicture_repair(String picture_repair) {
		this.picture_repair = picture_repair;
	}
	
	public int getRepair_id() {
		return repair_id;
	}

	public void setRepair_id(int repair_id) {
		this.repair_id = repair_id;
	}

	public String getRepair_date() {
		return repair_date;
	}

	public void setRepair_date(String repair_date) {
		this.repair_date = repair_date;
	}

	public String getRepair_title() {
		return repair_title;
	}

	public void setRepair_title(String repair_title) {
		this.repair_title = repair_title;
	}

	public String getRepair_charges() {
		return repair_charges;
	}

	public void setRepair_charges(String repair_charges) {
		this.repair_charges = repair_charges;
	}

	public String getRepair_detail() {
		return repair_detail;
	}

	public void setRepair_detail(String repair_detail) {
		this.repair_detail = repair_detail;
	}

	public String getPicture_invoice() {
		return picture_invoice;
	}

	public void setPicture_invoice(String picture_invoice) {
		this.picture_invoice = picture_invoice;
	}

	public String getPicture_repairreport() {
		return picture_repairreport;
	}

	public void setPicture_repairreport(String picture_repairreport) {
		this.picture_repairreport = picture_repairreport;
	}

	public String getPicture_quatation() {
		return picture_quatation;
	}

	public void setPicture_quatation(String picture_quatation) {
		this.picture_quatation = picture_quatation;
	}
	public String getDate_of_repair() {
		return Date_of_repair;
	}
	public void setDate_of_repair(String date_of_repair) {
		Date_of_repair = date_of_repair;
	}
	
	
	
}
