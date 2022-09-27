package ac.th.itsci.durable.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Item;

import java.util.*;

import javax.transaction.Transactional;
public interface ItemRepository extends CrudRepository<Item, Long> {
	
	/*Start Search*/
	@Query("From Item where item_category not in('หมวดบํารุงรักษาสินทรัพย์','หมวดวัสดุสินทรัพย์ถาวร')")
	List<Item> get_all_item();
	
	@Query("From Item where item_name like %?1% or item_category = ?2")
	List<Item> get_search_item(String item_name, String item_category);
	
	@Query("From Item where item_name like %?1% and item_category not in('หมวดบํารุงรักษาสินทรัพย','หมวดวัสดุสินทรัพย์ถาวร')")
	List<Item> get_search_item_byName(String item_name);
	
	@Query("From Item where item_category = ?1 and item_category not in('หมวดบํารุงรักษาสินทรัพย','หมวดวัสดุสินทรัพย์ถาวร')")
	List<Item> get_search_item_byCategory(String item_category);
	
	@Query("From Item where item_id = ?1")
	Item get_ItemByID(String item_id);
	/*End Search*/
	
	/*Start DDL*/
	@Transactional
	@Modifying
	@Query(value="{call insert_item_procedure(?1, ?2, ?3, ?4, ?5, ?6) }",nativeQuery = true)
	int insert_Item(String item_id, String item_name, double item_price, String item_unit, String note, String category);
	
	@Transactional
	@Modifying
	@Query(value="{call update_item_procedure(?1, ?2, ?3, ?4, ?5, ?6) }", nativeQuery = true)
	int update_Item(String item_id, String item_name, double item_price, String item_unit, String note, String category);
	
	
	@Query("select count(item_id) From Item")
	int count_Item();
	
	/*END DDL*/
}
