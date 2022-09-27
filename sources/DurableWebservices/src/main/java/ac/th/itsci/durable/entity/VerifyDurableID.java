package ac.th.itsci.durable.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;


@Embeddable
public class VerifyDurableID implements Serializable {
	
	    private static final long serialVersionUID = 1L;
		
		@ManyToOne
		private Durable durable;

		@ManyToOne
		private Staff staff;
		
		@ManyToOne
		private Verify verify;
		
		
	public VerifyDurableID(Durable durable, Staff staff, Verify verify) {
			super();
			this.durable = durable;
			this.staff = staff;
			this.verify = verify;
		}
	
	/*	
		public VerifyDurableID(Staff staff, Room room, Durable durable) {
			super();
			this.staff = staff;
			this.room = room;
			this.durable = durable;
		}

		public VerifyDurableID() {
			super();
			// TODO Auto-generated constructor stub
		}
		*/

		public VerifyDurableID() {
		super();
		// TODO Auto-generated constructor stub
	}

		public Durable getDurable() {
			return durable;
		}

		public void setDurable(Durable durable) {
			this.durable = durable;
		}

		public Staff getStaff() {
			return staff;
		}

		public void setStaff(Staff staff) {
			this.staff = staff;
		}

     	public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public Verify getVerify() {
			return verify;
		}

		public void setVerify(Verify verify) {
			this.verify = verify;
		}


}
