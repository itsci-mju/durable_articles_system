package ac.th.itsci.durable.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.transaction.annotation.Transactional;

import ac.th.itsci.durable.entity.*;

public interface PersonnelAssignRepository extends CrudRepository<PersonnelAssign, Long> {
	
	@Query("From PersonnelAssign where personnelAssign.assignType.assignType_id = ?1 and personnelAssign.personnel.major.ID_Major = ?2")
	List<PersonnelAssign> get_personnelByAssignTypeAndMajor(String assign_type_id, int major_id);
	
	@Query("From PersonnelAssign where personnelAssign.assignType.assignType_id = ?1")
	List<PersonnelAssign> get_personnelByAssignType(String assign_type_id);
	
	@Transactional
	@Modifying
	@Query(value = "{call insert_PersonnelAssign(?1, ?2)}", nativeQuery = true)
	int insert_assignPersonnel(String assign_type_id, String personnel_id);
	
	@Transactional
	@Modifying
	@Query(value = "{call remove_PersonnelAssign(?1, ?2)}", nativeQuery = true)
	int remove_assignPersonnel(String assign_type_id, String personnel_id);
}
