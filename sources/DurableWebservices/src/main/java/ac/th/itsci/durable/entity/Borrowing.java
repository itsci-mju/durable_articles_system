package ac.th.itsci.durable.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Borrowing")
public class Borrowing {
	
	@Id
	@GeneratedValue
	private int Borrowing_ID;
	@Column(name = "Borrow_status", columnDefinition = "varchar(25)")
	private String Borrow_status;
	@Column(name = "Borrow_date", columnDefinition = "varchar(255)")
	private String Borrow_date;
	@Column(name = "Schedulereturn", columnDefinition = "varchar(255)")
	private String Schedulereturn;
	@Column(name = "Return_date", columnDefinition = "varchar(255)")
	private String Return_date;
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "Idcard_borrower")
	private Borrower Borrower;
	
	public Borrower getBorrower() {
		return Borrower;
	}
	public void setBorrower(Borrower borrower) {
		Borrower = borrower;
	}
	
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "Id_staff")
	private Staff staff;
	
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public Borrowing() {
		super();
	}

	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "Durable_code")
	private Durable durable;
	
	public Durable getDurable() {
		return durable;
	}
	public void setDurable(Durable durable) {
		this.durable = durable;
	}

	
	public Borrowing(int borrowing_ID, String borrow_status, String borrow_date, String schedulereturn,
			String return_date) {
		super();
		Borrowing_ID = borrowing_ID;
		Borrow_status = borrow_status;
		Borrow_date = borrow_date;
		Schedulereturn = schedulereturn;
		Return_date = return_date;
	}
	public int getBorrowing_ID() {
		return Borrowing_ID;
	}

	public void setBorrowing_ID(int borrowing_ID) {
		Borrowing_ID = borrowing_ID;
	}

	public String getBorrow_status() {
		return Borrow_status;
	}

	public void setBorrow_status(String borrow_status) {
		Borrow_status = borrow_status;
	}
	public String getBorrow_date() {
		return Borrow_date;
	}
	public void setBorrow_date(String borrow_date) {
		Borrow_date = borrow_date;
	}
	public String getSchedulereturn() {
		return Schedulereturn;
	}
	public void setSchedulereturn(String schedulereturn) {
		Schedulereturn = schedulereturn;
	}
	public String getReturn_date() {
		return Return_date;
	}
	public void setReturn_date(String return_date) {
		Return_date = return_date;
	}


	
}
