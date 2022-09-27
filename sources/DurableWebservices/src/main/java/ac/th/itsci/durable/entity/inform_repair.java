package ac.th.itsci.durable.entity;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "inform_repair")
public class inform_repair {
	@Column(name = "Informid", length = 20)
	@Id
	private String Informid;
	
	@Column(name = "Informtype", length = 45)
	private String Informtype;
	
	@Column(name = "dateinform")
	private Calendar dateinform;
	
	@Column(name = "details", length = 255)
	private String details;
	
	@Column(name = "picture_inform", length = 255)
	private String picture_inform;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_staff")
	private Staff staff = new Staff();
	
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @JoinColumn(name = "durable_code")
    private Durable durable = new Durable();
	
	public inform_repair(String informid, String informtype, Calendar dateinform, String details, String picture_inform) {
		super();
		Informid = informid;
		Informtype = informtype;
		this.dateinform = dateinform;
		this.details = details;
		this.picture_inform = picture_inform;
	}
	

	public inform_repair(String informid, String informtype, Calendar dateinform, String details, String picture_inform,
			Staff staff, Durable durable) {
		super();
		Informid = informid;
		Informtype = informtype;
		this.dateinform = dateinform;
		this.details = details;
		this.picture_inform = picture_inform;
		this.staff = staff;
		this.durable = durable;
	}


	public inform_repair() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getInformid() {
		return Informid;
	}

	public void setInformid(String informid) {
		Informid = informid;
	}

	public String getInformtype() {
		return Informtype;
	}

	public void setInformtype(String informtype) {
		Informtype = informtype;
	}

	public Calendar getDateinform() {
		return dateinform;
	}

	public void setDateinform(Calendar dateinform) {
		this.dateinform = dateinform;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getPicture_inform() {
		return picture_inform;
	}

	public void setPicture_inform(String picture_inform) {
		this.picture_inform = picture_inform;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Durable getDurable() {
		return durable;
	}

	public void setDurable(Durable durable) {
		this.durable = durable;
	}
	
	
	
}
