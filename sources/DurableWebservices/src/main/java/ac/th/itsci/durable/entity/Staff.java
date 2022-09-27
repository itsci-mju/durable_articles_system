package ac.th.itsci.durable.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity 
@Table(name = "Staff")
public class Staff {
				
		@Id
		@GeneratedValue
		private int id_staff;
		
		@Column(name = "Id_card", columnDefinition = "varchar(13)")
		private String Id_card;
		@Column(name = "Staff_name", columnDefinition = "varchar(100)")
		private String Staff_name;
		@Column(name = "Staff_lastname", columnDefinition = "varchar(100)")
		private String Staff_lastname;
		@Column(name = "Staff_status", columnDefinition = "varchar(100)")
		private String Staff_status;
		@Column(name = "Email", columnDefinition = "varchar(100)")
		private String Email;
		@Column(name = "Brithday", columnDefinition = "varchar(100)")
		private String Brithday;
		@Column(name = "Phone_number", columnDefinition = "varchar(10)")
		private String Phone_number;
		@Column(name = "Image_staff", columnDefinition = "varchar(100)")
		private String Image_staff;
		
	
		@OneToMany(mappedBy = "pk.staff", cascade = CascadeType.ALL)
		@JsonProperty("VerifyDurable")
		private Set<VerifyDurable> VerifyDurable = new HashSet<VerifyDurable>();
		public Set<VerifyDurable> getVerifyDurable() {
			return VerifyDurable;
		}

		public void setVerifyDurable(Set<VerifyDurable> verifyDurable) {
			VerifyDurable = verifyDurable;
		}

		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name="ID_Major")
		private Major major;
		
		public Major getMajor() {
			return major;
		}

		public void setMajor(Major major) {
			this.major = major;
		}

		@OneToOne(cascade = CascadeType.ALL)
		@JoinColumn(name="Username")
		private Login login;
		
		public Login getLogin() {
			return login;
		}
		
		public void setLogin(Login login) {
			this.login = login;
		}

		
		public Staff() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Staff(int Id_staff,String id_card, String staff_name, String staff_lastname, String staff_status, String email,
				String brithday, String phone_number, String image_staff) {
			super();
			id_staff = Id_staff;
			Id_card = id_card;
			Staff_name = staff_name;
			Staff_lastname = staff_lastname;
			Staff_status = staff_status;
			Email = email;
			Brithday = brithday;
			Phone_number = phone_number;
			Image_staff = image_staff;
		}
		public Staff(String id_card, String staff_name, String staff_lastname, String staff_status, String email,
				String brithday, String phone_number, String image_staff) {
			super();
			Id_card = id_card;
			Staff_name = staff_name;
			Staff_lastname = staff_lastname;
			Staff_status = staff_status;
			Email = email;
			Brithday = brithday;
			Phone_number = phone_number;
			Image_staff = image_staff;
		}
	

		public Staff(int id_staff, String id_card, String staff_name, String staff_lastname, String staff_status,
				String email, String brithday, String phone_number, String image_staff, Major major, Login login) {
			super();
			this.id_staff = id_staff;
			Id_card = id_card;
			Staff_name = staff_name;
			Staff_lastname = staff_lastname;
			Staff_status = staff_status;
			Email = email;
			Brithday = brithday;
			Phone_number = phone_number;
			Image_staff = image_staff;
			this.major = major;
			this.login = login;
		}

		public int getId_staff() {
			return id_staff;
		}

		public void setId_staff(int id_staff) {
			this.id_staff = id_staff;
		}

		public String getId_card() {
			return Id_card;
		}
		
		public void setId_card(String id_card) {
			Id_card = id_card;
		}
		
		public String getStaff_name() {
			return Staff_name;
		}
		
		public void setStaff_name(String staff_name) {
			Staff_name = staff_name;
		}
		
		public String getStaff_lastname() {
			return Staff_lastname;
		}
		
		public void setStaff_lastname(String staff_lastname) {
			Staff_lastname = staff_lastname;
		}
		public String getStaff_status() {
			return Staff_status;
		}
		
		public void setStaff_status(String staff_status) {
			Staff_status = staff_status;
		}
		
		public String getEmail() {
			return Email;
		}
		
		public void setEmail(String email) {
			Email = email;
		}
		
		public String getBrithday() {
			return Brithday;
		}
		
		public void setBrithday(String brithday) {
			Brithday = brithday;
		}
		
		public String getPhone_number() {
			return Phone_number;
		}
		
		public void setPhone_number(String phone_number) {
			Phone_number = phone_number;
		}
		
		public String getImage_staff() {
			return Image_staff;
		}
		
		public void setImage_staff(String image_staff) {
			Image_staff = image_staff;
		}
}
