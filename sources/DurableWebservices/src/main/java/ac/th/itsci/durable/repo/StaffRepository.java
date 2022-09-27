package ac.th.itsci.durable.repo;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ac.th.itsci.durable.entity.Staff;

@Repository
public interface StaffRepository  extends CrudRepository<Staff, Long> {
	
	@Query(value="{call staff_detail(?1)}", nativeQuery = true)
	String[] StaffDetailByUsername(String username);
	
	@Query(value="{call major_detail(?1)}", nativeQuery = true)
	String[] MajorDetailByID(int id_major);
	
	@Query("from Staff where Id_card = ?1")
	Staff StaffDetailByIDcard(String id_card);

	@Transactional
	@Modifying
	@Query("UPDATE Staff s SET s.Image_staff = ?1 WHERE s.Id_card = ?2")
	void UpdateStaffImage(String Image_staff,String Id_card);	

	@Transactional
	@Modifying
	@Query("UPDATE Staff s SET s.Staff_name = ?1 ,s.Staff_lastname = ?2 ,s.Email = ?3 ,s.Brithday = ?4,s.Phone_number =?5  WHERE s.Id_card = ?6")
	int updateProfile(String staff_name,String staff_lastname,String email,String Brithday,String phone_number,String idcard);	
	
	//for_web
	@Query("From Staff s inner join Major m on m.ID_Major = s.major inner join Login l on l.Username = s.login where s.login.Username = ?1")
	Staff get_staff_detail_by_username(String username);
	
}
