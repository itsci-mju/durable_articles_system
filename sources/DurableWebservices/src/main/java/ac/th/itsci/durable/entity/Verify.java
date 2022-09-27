package ac.th.itsci.durable.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity 
@Table(name = "Verify")
public class Verify {
	
		@Id 
		@Column(columnDefinition = "varchar(5)")
		private String Years;
		@Column(name = "DateStart")
		private Date DateStart;
		@Column(name = "DateEnd")
		private Date DateEnd;
		
		/*@OneToMany(mappedBy = "pk.verify", cascade = CascadeType.ALL)
		private Set<VerifyDurable> VerifyDurable = new HashSet<VerifyDurable>();

		public Set<VerifyDurable> getVerifyDurable() {
			return VerifyDurable;
		}

		public void setVerifyDurable(Set<VerifyDurable> verifyDurable) {
			VerifyDurable = verifyDurable;
		}*/
		
		public Verify(String years, Date dateStart, Date dateEnd) {
			super();
			Years = years;
			DateStart = dateStart;
			DateEnd = dateEnd;
		}

		public Verify() {
			super();
			// TODO Auto-generated constructor stub
		}

		public String getYears() {
			return Years;
		}


		public void setYears(String years) {
			Years = years;
		}

		public Date getDateStart() {
			return DateStart;
		}

		public void setDateStart(Date dateStart) {
			DateStart = dateStart;
		}

		public Date getDateEnd() {
			return DateEnd;
		}

		public void setDateEnd(Date dateEnd) {
			DateEnd = dateEnd;
		}

		
		
}
