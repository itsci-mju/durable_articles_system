package ac.th.itsci.durable.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.*;

public interface ReceiveOrderDocumentRepository extends CrudRepository<ReceiveOrderDocument, Long> {

	@Transactional
	@Modifying
	@Query(value = "{call insert_receive_order_document(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)}", nativeQuery = true)
	int add_ReceiveOrderDocument(String date, String invoice_number, String doc_describe, String doc_from,
			String receive_orderDocID, String doc_id, String receive_order_1, String receive_order_2);
	
	@Transactional
	@Modifying
	@Query(value = "{call edit_receive_order_document(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)}", nativeQuery = true)
	int edit_ReceiveOrderDocument(String date, String invoice_number, String doc_describe, String doc_from,
			String receive_orderDocID, String doc_id, String receive_order_1, String receive_order_2);
	
	@Query("From ReceiveOrderDocument where doc_id = ?1")
	ReceiveOrderDocument get_ReceiveOrderDocument(String doc_id);
	
	@Query("From ReceiveOrderDocument where receiveOrderDocument_id = ?1")
	ReceiveOrderDocument get_ReceiveOrderDocumentByRodID(String receiveOrderDocument_id);
	
	@Query("From ReceiveOrderDocument")
	List<ReceiveOrderDocument> get_receiveOrderDocumentAll();
//	
//	@Query(value="{call get_receive_document(?1) }", nativeQuery = true)
//	List<String> get_ReceiveOrderDocumentByRodID(String receiveOrderDocument_id);
	
//	@Query(value="{call get_receiveOrderDocument(?1)}", nativeQuery = true)
//	ReceiveOrderDocument get_ReceiveOrderDocument(String doc_id);
	
}
