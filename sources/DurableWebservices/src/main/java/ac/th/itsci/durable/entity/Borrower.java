package ac.th.itsci.durable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Borrower")
public class Borrower {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String Idcard_borrower;
	@Column(name = "Borrower_name", columnDefinition = "varchar(255)")
	private String Borrower_name;
	@Column(name = "Borrower_picture", columnDefinition = "varchar(255)")
	private String Borrower_picture;
	@Column(name = "telephonenumber", columnDefinition = "varchar(11)")
	private String telephonenumber;
	
	
	public Borrower() {
		super();

	}
	
	public Borrower(String idcard_borrower, String borrower_name, String borrower_picture, String telephonenumber) {
		super();
		Idcard_borrower = idcard_borrower;
		Borrower_name = borrower_name;
		Borrower_picture = borrower_picture;
		this.telephonenumber = telephonenumber;
	}


	public Borrower(int id, String idcard_borrower, String borrower_name, String borrower_picture,
			String telephonenumber) {
		super();
		this.id = id;
		Idcard_borrower = idcard_borrower;
		Borrower_name = borrower_name;
		Borrower_picture = borrower_picture;
		this.telephonenumber = telephonenumber;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getIdcard_borrower() {
		return Idcard_borrower;
	}

	public void setIdcard_borrower(String idcard_borrower) {
		Idcard_borrower = idcard_borrower;
	}

	public String getBorrower_name() {
		return Borrower_name;
	}

	public void setBorrower_name(String borrower_name) {
		Borrower_name = borrower_name;
	}

	public String getBorrower_picture() {
		return Borrower_picture;
	}

	public void setBorrower_picture(String borrower_picture) {
		Borrower_picture = borrower_picture;
	}

	public String getTelephonenumber() {
		return telephonenumber;
	}

	public void setTelephonenumber(String telephonenumber) {
		this.telephonenumber = telephonenumber;
	}
	
	
	
}
