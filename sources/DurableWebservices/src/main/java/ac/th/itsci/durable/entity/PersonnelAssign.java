package ac.th.itsci.durable.entity;

import javax.persistence.*;

@Entity
@Table(name="personnelAssign")
public class PersonnelAssign {
	
	@EmbeddedId
	private PersonnelAssignID personnelAssign = new PersonnelAssignID();

	public PersonnelAssign() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonnelAssign(PersonnelAssignID personnelAssign) {
		super();
		this.personnelAssign = personnelAssign;
	}

	public PersonnelAssignID getPersonnelAssign() {
		return personnelAssign;
	}

	public void setPersonnelAssign(PersonnelAssignID personnelAssign) {
		this.personnelAssign = personnelAssign;
	}
	
	
}
