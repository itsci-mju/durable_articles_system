package ac.th.itsci.durable.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import ac.th.itsci.durable.entity.*;

public interface PersonnelRepository extends CrudRepository<Personnel, Long> {
	
	@Query("From Personnel where personnel_id = ?1")
	Personnel get_personnelByID(String personnel_id);

	@Query("From Personnel order by personnel_firstName ")
	List<Personnel> get_allPersonnel();

	@Query("From Personnel where personnel_firstName = ?1 and personnel_lastName = ?2")
	Personnel get_personnelByName(String personnel_firstName, String personnel_lastName);
	
	@Query("From Personnel where major.ID_Major = ?1  order by personnel_firstName ")
	List<Personnel> get_personnelByMajor(int major_id);
	
//	@Query("From Personnel where assignType.assignType_id is null and major.ID_Major = 999 order by personnel_firstName ")
//	List<Personnel> get_personnelRequisition();

//	@Query("From Personnel where assignType.assignType_id = ?1 and major.ID_Major = ?2")
//	List<Personnel> get_personnelByAssignType(String assignType_id, int id_major);
	
//	@Query("From Personnel where assignType.assignType_id = ?1  order by personnel_firstName ")
//	List<Personnel> get_personnelByAssignType(String assignType_id);
	
//	@Query("From Personnel where position.position_id = ?1  order by personnel_firstName ")
//	List<Personnel> get_personnelByPosition(String position_id);
	
//	@Query("From Personnel where assignType.assignType_id = ?1 and major.ID_Major = ?2  order by personnel_firstName ")
//	List<Personnel> get_personnelByAssignTypeMajor(String assignType_id, int major_id);
//	
//	@Query("From Personnel where major.ID_Major = ?1  order by personnel_firstName ")
//	List<Personnel> get_personnelByMajor(int major_id);
//
//	@Query("From Personnel where personnel_id = ?1  order by personnel_firstName ")
//	Personnel get_PersonnelByPersonnelId(String personnel_id);
//	
//	@Query("From Personnel where assignType.assignType_id is null or assignType.assignType_id = '-' order by personnel_firstName ")
//	List<Personnel> get_personnel_not_have_position();
//	
//	@Transactional
//	@Modifying
//	@Query(value = "{call add_assignType(?1, ?2)}", nativeQuery = true)
//	int addAssignPersonnel(String assign_type_id, String personnel_id);
//	
//	@Transactional
//	@Modifying
//	@Query(value="{call remove_assignType(?1)}", nativeQuery = true)
//	int remove_assignType(String personnel_id);

	
	//not use
	@Transactional
	@Modifying
	@Query(value = "{call insert_personnel_procedure(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)}", nativeQuery = true)
	int insert_personnel(String personnel_id, String personnel_first_name, String personnel_last_name,
			String personnel_position, String personnel_prefix, String assigntype_id, String position_id, int major);

	@Query("Select count(personnel_id) From Personnel")
	int get_countPersonnel();

	@Query(value = "{call get_request_purchase_order_document_person(?1)}", nativeQuery = true)
	List<Personnel> list_purchase_order_document_person(String doc_id);

}
