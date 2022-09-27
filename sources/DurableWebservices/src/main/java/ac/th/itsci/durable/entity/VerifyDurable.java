package ac.th.itsci.durable.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class VerifyDurable {

	@EmbeddedId
	private VerifyDurableID pk = new VerifyDurableID();

	private String Save_date;
	private String Durable_status;
	private String note;
	private String picture_verify;
	public VerifyDurable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VerifyDurable( String save_date, String durable_status, String note,
			String picture_verify) {
		super();
		Save_date = save_date;
		Durable_status = durable_status;
		this.note = note;
		this.picture_verify = picture_verify;
	}
	
	

	public VerifyDurable( String save_date, String durable_status, String note,
			String picture_verify,VerifyDurableID pk) {
		super();
		Save_date = save_date;
		Durable_status = durable_status;
		this.note = note;
		this.picture_verify = picture_verify;
		this.pk = pk;
	}

	public String getSave_date() {
		return Save_date;
	}

	public void setSave_date(String save_date) {
		Save_date = save_date;
	}

	public String getDurable_status() {
		return Durable_status;
	}

	public void setDurable_status(String durable_status) {
		Durable_status = durable_status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public VerifyDurableID getPk() {
		return pk;
	}

	public void setPk(VerifyDurableID pk) {
		this.pk = pk;
	}

	public String getPicture_verify() {
		return picture_verify;
	}

	public void setPicture_verify(String picture_verify) {
		this.picture_verify = picture_verify;
	}
	

}
