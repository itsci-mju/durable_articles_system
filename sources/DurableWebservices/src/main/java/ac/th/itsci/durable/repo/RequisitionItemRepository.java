package ac.th.itsci.durable.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.*;

public interface RequisitionItemRepository extends CrudRepository<RequisitionItem, Long> {

//	@Query("From RequisitionItem where pk.document.doc_id = ?1")
//	List<RequisitionItem> getAllRequisitionItemByDoc_id(String doc_id);
////	
//	@Query("From RequisitionItem where pk.document.doc_id = ?1 and pk.item.item_id = ?2 order by requisition_total_balance DESC")
//	List<RequisitionItem> getRequisitionItemDetail(String doc_id, String item_id);
//	
//	@Query("Select min(requisition_total_balance) From RequisitionItem where pk.document.doc_id = ?1 and pk.item.item_id = ?2")
//	String getLowestItemBalance(String doc_id, String item_id);

//	@Query("From RequisitionItem where pk.requestOrderItemList.id.document.status = ?1 and pk.requestOrderItemList.id.document.depart_name = ?2 and year(pk.requestOrderItemList.id.document.doc_date) <= ?3 and pk.requestOrderItemList.amount_balance != 0 and pk.requestOrderItemList.id.document.vat_check != 'repair' order by year(pk.requestOrderItemList.id.document.doc_date), pk.requestOrderItemList.id.item.item_name")
//	List<RequisitionItem> get_listRequisition_item(String status, String depart_name, int year);

	@Transactional
	@Modifying
	@Query(value = "{call insert_requisition_item(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)}", nativeQuery = true)
	int insert_requisition(String requisition_id, String requisition_date, int requisition_total,
			Double total_price_balance, Double total_price_purchase, String item_id, String personnel_id, String doc_id,
			int requisition_total_balance, String requisition_noteÏ);

	// @Query("Select i.item_name, sum(rl.amount_receive), sum(rl.total_price),
	// sum(ri.requisition_total)")

}
