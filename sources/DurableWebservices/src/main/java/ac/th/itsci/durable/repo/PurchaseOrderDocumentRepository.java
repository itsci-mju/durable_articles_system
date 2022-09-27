package ac.th.itsci.durable.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

import javax.transaction.Transactional;

import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.PurchaseOrderDocument;
import ac.th.itsci.durable.entity.ReceiveOrderDocument;

public interface PurchaseOrderDocumentRepository extends CrudRepository<PurchaseOrderDocument, Long> {

	@Transactional
	@Modifying
	@Query(value = "{call insert_purchase_order_document(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11)}", nativeQuery = true)
	int add_purchaseOrderDocument(String making_date, String dean, String purchaseOrderDocDescribe,
			String purchaseOrderDocId, String purchaseOrderType, String purchaseOrderPerson,
			String purchaseOrderRequestID, String purchase_order_1, String purchase_order_3, String purchase_order_4, String purchase_order_5);
	
	@Transactional
	@Modifying
	@Query(value = "{call edit_purchase_order_document(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11)}", nativeQuery = true)
	int edit_purchaseOrderDocument(String making_date, String dean, String purchaseOrderDocDescribe,
			String purchaseOrderDocId, String purchaseOrderType, String purchaseOrderPerson,
			String purchaseOrderRequestID, String purchase_order_1, String purchase_order_3, String purchase_order_4, String purchase_order_5);

	@Query("From PurchaseOrderDocument where doc_id = ?1")
	PurchaseOrderDocument get_purchaseOrderDocument(String doc_id);

	@Query(value = "{call get_faculty_dean(?1)}", nativeQuery = true)
	String get_faculty_dean(String doc_id);

	@Query(value = "{call get_requisition_person(?1)}", nativeQuery = true)
	String get_requisition_person(String doc_id);

}
