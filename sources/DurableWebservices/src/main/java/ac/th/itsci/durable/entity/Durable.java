package ac.th.itsci.durable.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "Durable")
@Inheritance(strategy = InheritanceType.JOINED)
public class Durable {

	@Id
	@Column(columnDefinition = "varchar(60)")
	private String Durable_code;
	@Column(name = "Durable_name", columnDefinition = "varchar(1000)")
	private String Durable_name;
	@Column(name = "Durable_number", columnDefinition = "varchar(255)")
	private String Durable_number;
	@Column(name = "Durable_brandname", columnDefinition = "varchar(1000)")
	private String Durable_brandname;
	@Column(name = "Durable_model", columnDefinition = "varchar(1000)")
	private String Durable_model;
	@Column(name = "Durable_price", columnDefinition = "varchar(255)")
	private String Durable_price;
	@Column(name = "Durable_statusnow", columnDefinition = "varchar(255)")
	private String Durable_statusnow;
	@Column(name = "Responsible_person", columnDefinition = "varchar(255)")
	private String Responsible_person;
	@Column(name = "Durable_image", columnDefinition = "varchar(255)")
	private String Durable_image;
	@Column(name = "Durable_Borrow_Status", columnDefinition = "varchar(255)")
	private String Durable_Borrow_Status;
	@Column(name = "Durable_entrancedate", columnDefinition = "varchar(255)")
	private String Durable_entrancedate;
	@Column(name = "note", columnDefinition = "varchar(255)")
	private String note;

	@OneToMany(mappedBy = "pk.durable", cascade = CascadeType.ALL)
	private Set<VerifyDurable> VerifyDurable = new HashSet<VerifyDurable>();

	public Set<VerifyDurable> getVerifyDurable() {
		return VerifyDurable;
	}

	public void setVerifyDurable(Set<VerifyDurable> verifyDurable) {
		VerifyDurable = verifyDurable;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_Major")
	private Major major;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Room_number")
	private Room room;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "durable")
	private List<RepairDurable> repairDurable = new ArrayList<>();

	public Durable(String durable_code, String durable_name, String durable_number,
			String durable_brandname, String durable_model, String durable_price, String durable_statusnow,
			String responsible_person, String durable_image, String durable_Borrow_Status,
			String durable_entrancedate) {
		super();
		Durable_code = durable_code;
		Durable_name = durable_name;
		Durable_number = durable_number;
		Durable_brandname = durable_brandname;
		Durable_model = durable_model;
		Durable_price = durable_price;
		Durable_statusnow = durable_statusnow;
		Responsible_person = responsible_person;
		Durable_image = durable_image;
		Durable_Borrow_Status = durable_Borrow_Status;
		Durable_entrancedate = durable_entrancedate;
	}
	
	
		public Durable(String durable_code, String durable_name, String durable_number, String durable_brandname,
			String durable_model, String durable_price, String durable_statusnow, String responsible_person,
			String durable_image, String durable_Borrow_Status, String durable_entrancedate, String note, Major major,
			Room room) {
		super();
		Durable_code = durable_code;
		Durable_name = durable_name;
		Durable_number = durable_number;
		Durable_brandname = durable_brandname;
		Durable_model = durable_model;
		Durable_price = durable_price;
		Durable_statusnow = durable_statusnow;
		Responsible_person = responsible_person;
		Durable_image = durable_image;
		Durable_Borrow_Status = durable_Borrow_Status;
		Durable_entrancedate = durable_entrancedate;
		this.note = note;
		this.major = major;
		this.room = room;
	}

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="durableControll_id")
//	private DurableGood durableGood;

//	@OneToOne(mappedBy = "durablee")
//	private CompanyAddress comanyAdd;

//	@OneToOne(mappedBy="Durable", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//	private DurableGood durableGood;

	
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn()
//	private DurableControll durableControll;

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDurable_code() {
		return Durable_code;
	}

	public void setDurable_code(String durable_code) {
		Durable_code = durable_code;
	}

	public void setDurable_name(String durable_name) {
		Durable_name = durable_name;
	}

	public void setDurable_number(String durable_number) {
		Durable_number = durable_number;
	}

	public void setDurable_brandname(String durable_brandname) {
		Durable_brandname = durable_brandname;
	}

	public void setDurable_model(String durable_model) {
		Durable_model = durable_model;
	}

	public void setDurable_price(String durable_price) {
		Durable_price = durable_price;
	}

	public void setDurable_statusnow(String durable_statusnow) {
		Durable_statusnow = durable_statusnow;
	}

	public void setResponsible_person(String responsible_person) {
		Responsible_person = responsible_person;
	}

	public void setDurable_image(String durable_image) {
		Durable_image = durable_image;
	}

	public void setDurable_Borrow_Status(String durable_Borrow_Status) {
		Durable_Borrow_Status = durable_Borrow_Status;
	}

	public void setDurable_entrancedate(String durable_entrancedate) {
		Durable_entrancedate = durable_entrancedate;
	}

	public String getDurable_name() {
		return Durable_name;
	}

	public String getDurable_number() {
		return Durable_number;
	}

	public String getDurable_brandname() {
		return Durable_brandname;
	}

	public String getDurable_model() {
		return Durable_model;
	}

	public String getDurable_price() {
		return Durable_price;
	}

	public String getDurable_statusnow() {
		return Durable_statusnow;
	}

	public String getResponsible_person() {
		return Responsible_person;
	}

	public String getDurable_image() {
		return Durable_image;
	}

	public String getDurable_Borrow_Status() {
		return Durable_Borrow_Status;
	}

	public String getDurable_entrancedate() {
		return Durable_entrancedate;
	}

	public List<RepairDurable> getRepairDurable() {
		return repairDurable;
	}

	public void setRepairDurable(List<RepairDurable> repairDurable) {
		this.repairDurable = repairDurable;
	}

	
	public Durable() {
		super();
		// TODO Auto-generated constructor stub
	}
}
