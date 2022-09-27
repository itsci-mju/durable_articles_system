package ac.th.itsci.durable.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

import java.util.*;

import ac.th.itsci.durable.entity.*;

public interface DurableControllRepository extends CrudRepository<DurableControll, Long> {

//	@Query("Form DurableControll")
//	DurableControll get_durableControll();

//	@Transactional
//	@Modifying
//	@Query(value="{call insert_durableControll_procedure()}", nativeQuery = true)
//	int insert_durableControll();

	@Query("From DurableControll where Durable_code = ?1")
	DurableControll get_durableControll(String durable_code);


	@Transactional
	@Modifying
	@Query(value="{call insert_durable_controll(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)}", nativeQuery = true)
	int insert_durableControll(int durable_life_time, String durable_money_type, String durable_optain_type,
			String durable_petition_number, String durable_serial_number, String v_durable_type, String durable_code,
			int company_id, String budget_year, double depreciation_rate);
	
	@Transactional
	@Modifying
	@Query(value="{call insert_durable_controll_with_out_company(?1, ?2) }", nativeQuery = true)
	int insert_durableControll_with_out_company(String durable_code, String budget_year);

	@Transactional
	@Modifying
	@Query(value = "{call update_durableControll(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)}", nativeQuery = true)
	int update_durableControll(String durable_code, String durable_money_type, String durable_optain_type,
			String durable_petition_number, String durable_serial_number, String durable_type,
			int company_id, int v_durable_life_time, String year, double depreciation_rate);
	
	@Query("From DurableControll where budget_year = ?1 and major.ID_Major = ?2")
	List<DurableControll> get_durableControllByYearAndMajor(String year, int id_major);
	
	@Query("From DurableControll where budget_year = ?1")
	List<DurableControll> get_durableControllByYear(String year);
	
	@Query("From DurableControll where budget_year = ?1 and room.Room_number = ?2 and major.ID_Major = ?3")
	List<DurableControll> get_durableControllByYearAndRoomAndMajor(String year, String room_number, int major_id);
	
	@Query("SELECT distinct d From DurableControll d left join d.repairDurable rd where d.Durable_code in (rd.durable.Durable_code) and d.major.ID_Major = ?1")
	List<DurableControll> get_MaintenanceDurableByMajor(int major_id);
	
	@Query("SELECT distinct d From DurableControll d left join d.repairDurable rd where d.Durable_code in (rd.durable.Durable_code)")
	List<DurableControll> get_MaintenanceDurable();

}
