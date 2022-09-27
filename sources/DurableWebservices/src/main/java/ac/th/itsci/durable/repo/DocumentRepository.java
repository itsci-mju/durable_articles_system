package ac.th.itsci.durable.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Document;
import ac.th.itsci.durable.entity.PurchaseOrderDocument;

import java.util.*;

import javax.transaction.Transactional;

public interface DocumentRepository extends CrudRepository<Document, Long> {

	@Query("From Document  order by doc_date DESC")
	List<Document> get_allDocument();

	@Query("From Document where doc_id = ?1 order by doc_date DESC")
	Document get_purchaseOrderRequestDocument(String doc_id);
	
	@Query("From Document where depart_name = ?1 order by doc_date DESC")
	List<Document> get_DocumentByDepartName(String depart_name);
	

//	@Query("From Document where  = ?1")
//	List<Document> get_listDocumentByMajor(int major_id);

	@Query("From Document where doc_id like ?1% order by doc_date DESC")
	List<Document> searchDocumentById(String doc_id);

	@Query("From Document where doc_date = ?1")
	List<Document> searchDocumentByDate(Date date);

	@Query("From Document where status = ?1 order by doc_date DESC")
	List<Document> searchDocumentByStatus(String status);

	@Query("From Document where status = ?1 or doc_id like ?2% order by doc_date DESC")
	List<Document> searchDocumentByStatusAndDocId(String status, String doc_id);

//	@Query(value="{call search_document_by_date(?1)}",nativeQuery = true)
//	List<Document>  searchDocumentByDate(String date);

//	@Query("From Document where doc_date = ?1")
//	List<Document> searchDocumentByDate(Date date);

//	@Transactional
//	@Query(value="{call searchDocumentByDate(?1)}",nativeQuery=true)
//	List<Document> searchDocumentByDate(Date date);

	@Transactional
	@Modifying
	@Query(value = "{call insert_document_procedure(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27) }", nativeQuery = true)
	int add_Document(String doc_id, String accounting_officer, String budget, String chief_of_procurement,
			String doc_date, String depart_id, String depart_name, String doc_dear, String doc_reason_describe,
			String doc_reason_title, String doc_title, String doc_title_describe, String fund_id, String fund_name,
			String money_used, String plan_id, String plan_name, String price_txt, String request_order_person,
			String secretary, String doc_status, String work_name, Double total_price, Double price_with_out_tax,
			Double tax, String store_name, String vat_check);

	@Transactional
	@Modifying
	@Query(value = "{call update_document_procedure(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14, ?15, ?16, ?17, ?18, ?19, ?20, ?21, ?22, ?23, ?24, ?25, ?26, ?27) }", nativeQuery = true)
	int update_Document(String doc_id, String accounting_officer, String budget, String chief_of_procurement,
			String doc_date, String depart_id, String depart_name, String doc_dear, String doc_reason_describe,
			String doc_reason_title, String doc_title, String doc_title_describe, String fund_id, String fund_name,
			String money_used, String plan_id, String plan_name, String price_txt, String request_order_person,
			String secretary, String doc_status, String work_name, Double total_price, Double price_with_out_tax,
			Double tax, String store_name, String var_check);
	
	@Transactional
	@Modifying
	@Query("update Document set status = ?1 where doc_id = ?2")
	int report_document(String status, String doc_id);

}
