package ac.th.itsci.durable.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.*;

public interface MajorRepository extends CrudRepository<Major, Long> {
	
	@Query(value="{call Major_alldetail()}", nativeQuery = true)
	List<Major> getAllMajor();
	
	@Query(value="{call Major_DetailByName(?1)}", nativeQuery = true)
	Major getMajorByName(String majorname);
	
	@Query("From Major where ID_Major = ?1")
	Major getMajorByName(int id_major);
	
	
}
