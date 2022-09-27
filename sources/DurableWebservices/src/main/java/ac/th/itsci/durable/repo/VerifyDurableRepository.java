package ac.th.itsci.durable.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Durable;
import ac.th.itsci.durable.entity.VerifyDurable;

public interface VerifyDurableRepository extends CrudRepository<VerifyDurable, Long> {
	
	@Query(value="{call verify_check(?1)}", nativeQuery = true)
	List<VerifyDurable> verifyCheck(String durablecode);
	
	@Query(value = "{call verify_getAllbyCondition(?1,?2,?3)}" , nativeQuery = true)
	List<VerifyDurable> verify_getAllbyCondition(String year,String durable_status,int major);
	
	@Query(value = "{call verify_getAlldata(?1,?2)}" , nativeQuery = true)
	List<VerifyDurable> verify_getAlldata(String year,int major);
	
//	@Query(value = "{call Verify_listVerifydDurableBYyear_idmajor(?1,?2)}" , nativeQuery = true)
//	List<VerifyDurable> verify_getListVerifyByYear_idmajor(String year,int major);
	
//	@Query("From VerifyDurable vf join durable d on d.durable_code = vf.verify_durable.durable_durable_code join staff on staff.id_staff = verify_durable.staff_id_staff where verify_years = ?")
//	List<VerifyDurable> verifyallByYear(String verify_year);
	
	//add at 13 july 2020
	@Query("From VerifyDurable vd where vd.pk.verify.Years = ?1")
	List<VerifyDurable> verify_by_year(String year);
	
	//add at 21 july 2020
	@Transactional
	@Modifying
	@Query(value="{call Verify_insert(?1, ?2, ?3, ?4, ?5, ?6) }",nativeQuery = true)
	List<VerifyDurable> addVerifyFromList(String durable_status, String save_date, String note, int staff_id, String year, String durable_code);
	
	@Transactional
	@Modifying
	@Query(value="{call verify_year_insert(?1)}", nativeQuery = true)
	int add_verify_year_insert(String year);

	@Query("From VerifyDurable v where v.pk.verify.Years = ?1")
	List<VerifyDurable> get_durableControllByYear(String year);	
	
	
	//sql new or in use now (nice)
	@Query(value="{call verify_getAllVerifyByYear(?1)}", nativeQuery = true)
	List<VerifyDurable> verify_getAllVerifyByYear(String year);
	
	@Query(value="{call verify_getAllVerifyByYear_Status(?1, ?2)}", nativeQuery = true)
	List<VerifyDurable> verify_getAllVerifyByYear_Status(String year,String statusdurable);
	
	@Query(value="{call verify_getAllVerifyByYear_Major(?1, ?2)}", nativeQuery = true)
	List<VerifyDurable> verify_getAllVerifyByYear_Major(String year,int id_major);
	
	@Query(value="{call verify_getAllVerifyByYear_Id_major_Room(?1, ?2, ?3)}", nativeQuery = true)
	List<VerifyDurable> verify_getAllVerifyByYear_Id_major_Room(String year,int id_major,String room_number);
	
	@Query(value="{call verify_getAllVerifyByYear_Id_major_Room_Status(?1, ?2, ?3, ?4)}", nativeQuery = true)
	List<VerifyDurable> verify_getAllVerifyByYear_Id_major_Room_Status(String year,int id_major,String room_number,String status);
	
	@Query(value="{call verify_getAllVerifyByYear_Room(?1, ?2)}", nativeQuery = true)
	List<VerifyDurable> verify_getAllVerifyByYear_Room(String year,String room_number);
	
	@Query(value="{call verify_getAllVerifyByYear_Major_StatusDurable(?1, ?2, ?3)}", nativeQuery = true)
	List<VerifyDurable> verify_getAllVerifyByYear_Major_StatusDurable(String year,int id_major,String statusDurable);
	
	@Query(value="{call verify_getAllVerifyByYear_Room_Status(?1, ?2, ?3)}", nativeQuery = true)
	List<VerifyDurable> verify_getAllVerifyByYear_Room_Status(String year,String room_number,String statusDurable);
	
	@Query(value="{call verify_getDetailByDurableCode_Staff_Year(?1, ?2, ?3)}", nativeQuery = true)
	VerifyDurable verify_getDetailByDurableCode_Staff_Year(String durableCode,int id_staff,String year);
	
	@Transactional
	@Modifying
	@Query(value="{call verify_updateVerifyDurable(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)}", nativeQuery = true)
	int verify_updateVerifyDurable(String year,int id_staff,String DurableCode,String statusDurable,String note,String saveDate, String room, String responsible_person);
	
	
	//add by fluke
//	@Query("SELECT vd From VerifyDurable vd right join vd.pk.durable d "
//			+ "where vd.pk.verify.Years is null or vd.pk.verify.Years not in (?1)")
//	List<VerifyDurable> getNotVerifyDurableByYear(String year);
	
	
	@Query("From VerifyDurable v where v.pk.verify.Years = ?1")
	List<VerifyDurable> getVerifyDurableByYear(String year);
	
	@Query("From VerifyDurable v where v.pk.verify.Years = ?1 and v.pk.durable.major.ID_Major = ?2")
	List<VerifyDurable> getVerifyDurableByYearAndMajor(String year, int major_id);
	
	@Query("From VerifyDurable v where v.pk.verify.Years = ?1 and v.pk.durable.major.ID_Major = ?2 and v.pk.durable.room.Room_number = ?3")
	List<VerifyDurable> getVerifyDurableByYearAndMajorAndRoom(String year, int major_id, String room_number);
	
	@Query("From VerifyDurable v where v.pk.verify.Years = ?1 and v.pk.durable.major.ID_Major = ?2 and v.pk.durable.room.Room_number = ?3 and v.Durable_status = ?4")
	List<VerifyDurable> getVerifyDurableByYearAndMajorAndRoomAndStatus(String year, int major_id, String room_number, String status);
	
	@Query("From VerifyDurable v where v.pk.verify.Years = ?1 and v.pk.durable.major.ID_Major = ?2 and v.Durable_status = ?3")
	List<VerifyDurable> getVerifyDurableByYearAndMajorAndStatus(String year, int major_id, String status);
	
	@Query("From VerifyDurable v where v.pk.verify.Years = ?1 and v.Durable_status = ?2")
	List<VerifyDurable> getVerifyDurableByYearAndStatus(String year, String status);
	
	@Transactional
	@Modifying
	@Query(value="{call insert_verify_durable(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8) }", nativeQuery = true)
	int insertVerifyDurable(String durable_status, String save_date, String note, int staff_id, String year, String durable_code, String room, String responsible_person);
	
	@Query("From VerifyDurable v where v.pk.durable.Durable_code = ?1 and v.pk.verify.Years = ?2")
	VerifyDurable getVerifyDurableById(String durable_code, String year);
	
//	@Transactional
//	@Query(value="{call get_not_verify_by_year(?1)}",nativeQuery = true)
//	List<VerifyDurable> getNotVerifyDurableByYear(String year);

}
