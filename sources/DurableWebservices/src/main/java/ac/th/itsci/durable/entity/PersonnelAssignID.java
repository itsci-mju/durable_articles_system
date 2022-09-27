package ac.th.itsci.durable.entity;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class PersonnelAssignID implements Serializable {

	@ManyToOne
	private Personnel personnel;
	@ManyToOne
	private AssignType assignType;

	public PersonnelAssignID() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonnelAssignID(Personnel personnel, AssignType assignType) {
		super();
		this.personnel = personnel;
		this.assignType = assignType;
	}

	public Personnel getPersonnel() {
		return personnel;
	}

	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}

	public AssignType getAssignType() {
		return assignType;
	}

	public void setAssignType(AssignType assignType) {
		this.assignType = assignType;
	}

}
