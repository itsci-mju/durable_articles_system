package ac.th.itsci.durable.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.RepairDurable;

public interface RepairRepository extends CrudRepository<RepairDurable, Long> {
	
	@Query(value="{call Repair_getIDrepair(?1,?2)}", nativeQuery = true)
	String  getIdRepair(String Durablecode,String date);
	
	@Query(value="{call Repair_check(?1)}", nativeQuery = true)
	RepairDurable  Repair_check(String Durablecode);
	
	@Transactional
	@Modifying
	@Query("UPDATE RepairDurable d SET d.picture_repair = ?1 WHERE d.repair_id = ?2")
	void updateRepairImage(String repairImage,int repairID);	
	
	@Query(value="{call Repair_listRepair(?1)}", nativeQuery = true)
	List<RepairDurable>  getListRepair(String Durablecode);
	
	//add at 13 july 2020 
	@Query("From RepairDurable rd inner join Durable d on rd.durable = d.Durable_code ")
	List<RepairDurable> getAllRepairDurable();
	
	
	//new sql or in use
	@Query(value="{call repair_getAllRepairByMajor(?1)}", nativeQuery = true)
	List<RepairDurable> repair_getAllRepairByMajor(int id_major);
	
	@Query("Select DISTINCT rd.durable.Durable_code ,rd.durable.Durable_name from RepairDurable rd right join rd.durable where rd.durable.Durable_code in (rd.durable.Durable_code)")
	List<RepairDurable> repair_getAllMaintenanceDurable();
	
	@Query("From RepairDurable rd where rd.durable.major.ID_Major = ?1")
	List<RepairDurable> repair_getAllMaintenanceDurableByMajor(int major_id);
	
	@Query("From RepairDurable rd where rd.durable.Durable_code = ?1")
	List<RepairDurable> getMaintenanceDurable_detail(String durable_code);
	
	@Query("From RepairDurable where repair_id = ?1")
	RepairDurable getMaintenanceDetailById(int repair_id);
	
	@Transactional
	@Modifying
	@Query(value  = "{call update_maintenance(?1, ?2, ?3, ?4, ?5, ?6) }",nativeQuery = true)
	int update_maintenance(int repair_id, String date_of_repair, String repair_charges, String repair_detail, int company_id, String durable_code);
	
	@Transactional
	@Modifying
	@Query(value  = "{call add_maintenace(?1, ?2, ?3, ?4, ?5, ?6) }",nativeQuery = true)
	int add_maintenance(int repair_id, String date_of_repair, String repair_charges, String repair_detail, int company_id, String durable_code);
	
	@Query("select count(repair_id) from RepairDurable")
	int count_repair();
	
}
