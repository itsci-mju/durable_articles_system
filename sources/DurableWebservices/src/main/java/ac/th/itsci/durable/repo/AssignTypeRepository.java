package ac.th.itsci.durable.repo;

import java.util.*;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.*;

public interface AssignTypeRepository extends CrudRepository<AssignType, Long> {
	
	@Query("From AssignType")
	List<AssignType> get_allAssignType();
	
}
