package ac.th.itsci.durable.repo;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Verify;

public interface VerifyRepository extends CrudRepository<Verify, Long>{
	
	@Query(value="{call verify_getAllVerify()}", nativeQuery = true)
	List<Verify> verify_getAllVerify();
	
	@Transactional
	@Modifying
	@Query(value="{call verify_addVerify(?1, ?2, ?3) }", nativeQuery = true)
	int verify_addVerify(String year, Date dateEnd, Date dateStart);
	
	@Query(value="{call verify_getVerifyDetailByYears(?1)}", nativeQuery = true)
	Verify verify_getVerifyDetailByYears(String years);
	
}
