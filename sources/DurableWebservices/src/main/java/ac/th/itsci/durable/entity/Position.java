package ac.th.itsci.durable.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="position")
public class Position {
	@Id
	@Column(columnDefinition = "varchar(50)")
	private String position_id;
	@Column(name = "position_name", columnDefinition = "varchar(255)")
	private String position_name;
	@OneToMany(mappedBy="position")
	List<Personnel> personnelList = new ArrayList<>();
	
	
	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Position(String position_id, String position_name) {
		super();
		this.position_id = position_id;
		this.position_name = position_name;
	}


	public String getPosition_id() {
		return position_id;
	}


	public void setPosition_id(String position_id) {
		this.position_id = position_id;
	}


	public String getPosition_name() {
		return position_name;
	}


	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}


	public List<Personnel> getPersonnelList() {
		return personnelList;
	}


	public void setPersonnelList(List<Personnel> personnelList) {
		this.personnelList = personnelList;
	}


	
	
	

}
