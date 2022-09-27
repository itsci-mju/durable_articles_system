package ac.th.itsci.durable.repo;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.RequisitionDocument;

public interface RequisitionDocumentRepository extends CrudRepository<RequisitionDocument, Long> {

	@Transactional
	@Modifying
	@Query(value="{call insert_requisition_doc(?1, ?2, ?3)}",nativeQuery = true)
	int insert_requisition(String requisition_id, Date requisition_date, int budget_year);
}
