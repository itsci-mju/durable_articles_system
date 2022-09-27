package ac.th.itsci.durable.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.BillOfLading;

public interface BillOfLadingRepository extends CrudRepository<BillOfLading, Long> {

	@Transactional
	@Modifying
	@Query(value = "{ call insert_bill_of_lading(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)}", nativeQuery = true)
	int add_billOfLading(String bol_id, String receive_person_id, String representative_person, String doc_id,
			String bill_of_lading_1, String bill_of_lading_2, String bill_of_lading_4, String bill_of_lading_5);
	
	@Transactional
	@Modifying
	@Query(value = "{ call edit_bill_of_lading(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)}", nativeQuery = true)
	int edit_billOfLading(String bol_id, String receive_person_id, String representative_person, String doc_id,
			String bill_of_lading_1, String bill_of_lading_2, String bill_of_lading_4, String bill_of_lading_5);

	@Query("From BillOfLading where doc_id = ?1")
	BillOfLading get_billOfLading(String doc_id);
}
