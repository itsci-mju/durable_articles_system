package ac.th.itsci.durable.entity;

import javax.persistence.*;


@Entity
@Table(name="committee")
public class Committee {
	
	@EmbeddedId
	private CommitteeID committee = new CommitteeID();
	@Column(name="committee_status")
	private String committee_status;

	public Committee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Committee(CommitteeID committee) {
		super();
		this.committee = committee;
	}

	public CommitteeID getCommittee() {
		return committee;
	}

	public void setCommittee(CommitteeID committee) {
		this.committee = committee;
	}

	public String getCommittee_status() {
		return committee_status;
	}

	public void setCommittee_status(String committee_status) {
		this.committee_status = committee_status;
	}
	
	
	
}
