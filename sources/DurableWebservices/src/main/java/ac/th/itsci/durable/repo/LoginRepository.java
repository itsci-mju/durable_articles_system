package ac.th.itsci.durable.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Login;
import ac.th.itsci.durable.util.PasswordUtil;

public interface LoginRepository extends CrudRepository<Login, Long>{
	public static String SALT = "123456";
	
	@Transactional
	@Modifying
	@Query("update Login set status = ?1 WHERE username = ?2 and password = ?3")
	int logins(String status, String username,String password);
	
	@Transactional
	@Modifying
	@Query("update Login set status = ?1 WHERE username = ?2")
	int logout(String status, String username);
	
	@Query(value="{call Login_CheckUsername(?1)}", nativeQuery = true)
	Login CheckUsername(String username);
	
	@Transactional
	@Modifying
	@Query("update Login set password = ?1 WHERE username = ?2 ")
	int updatepassword(String newpassword, String username);
	
	//add at 11 july 2020
	@Query("From Login where Username = ?1 and Password = ?2")
	Login login_by_username_password(String username, String password);
	
	
	
}
