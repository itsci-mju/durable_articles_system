package ac.th.itsci.durable.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Committee;

import java.util.*;

import javax.transaction.Transactional;

public interface CommitteeRepository extends CrudRepository<Committee, Long> {
	
	@Query("From Committee where id.document.doc_id = ?1")
	List<Committee> get_allCommitteeFromDocumentID(String doc_id);
	
	@Query("From Committee")
	List<Committee> get_allCommittee();
	
	@Query("From Committee where committee.personnel.personnel_id = ?1")
	Committee get_committeeByPersonnelID(String personnel_id);
	
	@Transactional
	@Modifying
	@Query(value="{call insert_committee_procedure(?1, ?2)}",nativeQuery = true)
	int add_Committee(String personnel_id, String document_id);
	
	@Transactional
	@Modifying
	@Query("Delete Committee where id.document.doc_id = ?1")
	int delete_CommitteeFromDocument(String doc_id);
	
	
}
