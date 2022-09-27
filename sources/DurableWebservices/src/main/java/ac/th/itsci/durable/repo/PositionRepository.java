package ac.th.itsci.durable.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import ac.th.itsci.durable.entity.*;

import java.util.*;
public interface PositionRepository extends CrudRepository<Position, Long> {
	
	@Query("From Position")
	List<Position> get_allPosition();
	
	@Query("From Position where position_name = ?1")
	Position get_positionByName(String position_name);
	
	@Transactional
	@Modifying
	@Query(value="{call insert_position_procedure(?1, ?2) }",nativeQuery = true)
	int insert_position(String position_id, String position_name);
	
	@Query("select count(Position_id) from Position")
	int get_countPosition();
	
	
}
