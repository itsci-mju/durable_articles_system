package ac.th.itsci.durable.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Login")
public class Login {

	@Id
	@Column(name = "username",columnDefinition = "varchar(50)")
	private String Username;
	@Column(name = "password")
	private String Password;
	@Column(name = "status")
	private String status;
	
	public Login() {
		super();
	}
	
	public Login(String username, String password, String status) {
		super();
		Username = username;
		Password = password;
		this.status = status;
	}
	
	public String getUsername() {
		return Username;
	}
	
	public void setUsername(String username) {
		Username = username;
	}
	
	public String getPassword() {
		return Password;
	}
	
	public void setPassword(String password) {
		Password = password;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
