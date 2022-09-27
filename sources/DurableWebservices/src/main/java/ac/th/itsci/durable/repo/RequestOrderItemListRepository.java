package ac.th.itsci.durable.repo;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.RequestOrderItemList;

public interface RequestOrderItemListRepository extends CrudRepository<RequestOrderItemList, Long> {

	@Query("Select count(id.requestOrderItemList_id) From RequestOrderItemList")
	int get_countRequestOrderItem();
	
	@Query("From RequestOrderItemList where id.document.doc_id = ?1")
	List<RequestOrderItemList> get_allRequestItemByDocumentId(String doc_id);
	
	@Query("From RequestOrderItemList where id.item.item_id = ?1 and id.document.doc_id = ?2")
	RequestOrderItemList get_itemDetailInDocument(String item_id, String doc_id);
	
	@Query("From RequestOrderItemList where year(id.document.doc_date) <= ?1 and id.document.depart_name = ?2 and id.document.vat_check != ?3 and id.document.status = ?4 order by year(id.document.doc_date), id.item.item_name")
	List<RequestOrderItemList> get_listRequestOrderItemforRequisition(int year, String depart_name, String vat_check, String doc_status);
	
	@Transactional
	@Modifying
	@Query(value="{call insert_requestItem_firstDoc_procedure(?1, ?2, ?3, ?4, ?5, ?6, ?7) }", nativeQuery = true)
	int add_itemRequestItem_firstDoc(int item_amount, double total_price, String doc_id, String item_id, Double item_price, String request_order_item_id, int year);
	
	@Transactional
	@Modifying
	@Query("Delete From RequestOrderItemList where id.document.doc_id = ?1")
	int delete_RequestItem(String doc_id);
	
	@Transactional
	@Modifying
	@Query(value="{call insert_request_order_item_purchaseOrderDocument(?1, ?2, ?3, ?4)}",nativeQuery = true)
	int insert_requestItem_PurchaseOrderDocument(String note, String prescription, String doc_id, String item_id);
	
	@Transactional
	@Modifying
	@Query(value="{call insert_request_order_item_receiveOrderDocument(?1, ?2, ?3, ?4)}", nativeQuery = true)
	int insert_requestItem_ReceivedOrderDocument(int amount_in_invoice, int amount_received, String doc_id, String item_id);
	
	@Query("From RequestOrderItemList where id.item.item_id = ?1 and item_price = ?2 and amount_balance != ?3 and id.document.depart_name = ?4 order by id.document.doc_date ASC")
	List<RequestOrderItemList> get_request_order_item_by_amount(String item_id, Double item_price, int amount_balance, String depart_name);
}
