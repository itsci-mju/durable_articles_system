package ac.th.itsci.durable.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.*;

public interface DurableTypeRepository extends CrudRepository<DurableType, Long> {
	
	@Query("From DurableType")
	List<DurableType> listDurable_type();

}
