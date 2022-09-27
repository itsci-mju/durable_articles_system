package ac.th.itsci.durable.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Durable;

public interface DurableRepository extends CrudRepository<Durable, Long> {

	@Query(value = "{call Durable_CheckstatusBorrow(?1)}", nativeQuery = true)
	String Durable_CheckstatusBorrow(String durable);

	@Query(value = "{call Durable_DetailByDurableName(?1,?2)}", nativeQuery = true)
	List<Durable> getDetailDurableByName(String durablename, int id_major);

	@Query(value = "{call Durable_DetailByDurableStatus(?1,?2)}", nativeQuery = true)
	List<Durable> getDetailDurableByStatus(String durablestatus, int id_major);

	@Query(value = "{call Durable_DetailByResponsibleperson(?1)}", nativeQuery = true)
	List<Durable> getDetailDurableByResponsibles(String Responsibles);

	@Query(value = "{call Durable_Detail(?1)}", nativeQuery = true)
	Durable detaildurablebydurablecodes(String Durablecode);

	@Transactional
	@Modifying
	@Query("UPDATE Durable d SET d.Durable_statusnow = ?1 WHERE d.Durable_code = ?2")
	int UpdateDurableStatus(String durableStatusnow, String Durablecode);

	@Query("From Durable where durable_name like %?1%")
	List<Durable> getDetailByDurableName(String durable_name);

	@Transactional
	@Modifying
	@Query("UPDATE Durable d SET d.Durable_Borrow_Status = ?1 WHERE d.Durable_code = ?2")
	int UpdateStatusBorrow(String durable_status_borrow, String Durablecode);

//	@Transactional
//	@Modifying
//	@Query("UPDATE Durable d SET d.Durable_statusnow = ?1 WHERE d.Durable_code = ?2")
//	int UpdateStatusDurableNow(String Durable_statusnow,String Durablecode);	

	// add at 12 july 2020
	@Query("select distinct d.Responsible_person From Durable d where d.major.ID_Major = ?1 and d.Responsible_person IS NOT NULL")
	List<String> getAllResponsible_person(int major_id);

	@Transactional
	@Modifying
	@Query(value = "{call Durable_record(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14)}", nativeQuery = true)
	int recordDurable(String durable_code, String durable_borrow_status, String durable_brandname,
			String durable_entranceDate, String image, String model, String name, String number, String price,
			String statusnow, String responsePerson, String note, int id_major, String room_number);

	// add at 13 july 2020
	@Query("From Durable d where d.major.ID_Major = ?1")
	List<Durable> getAllDurableByIdMajor(int major_id);

	// new sql or in use
	@Query(value = "{call durable_getAllDurableBYMajorLimit(?1, ?2, ?3)}", nativeQuery = true)
	List<Durable> durable_getAllDurableBYMajorLimit(int major_id, int start, int end);

//	@Query(value = "{call durable_DetailByRoom(?1)}", nativeQuery = true)
//	List<Durable> getDurable_DetailByRoom(String roomname);

	@Query(value = "From Durable where room.Room_number = ?1")
	List<Durable> getDurable_DetailByRoom(String roomname);

	@Query("From Durable d where d.Durable_code = ?1")
	Durable get_durableByDurableCode(String durable_code);

//	@Transactional
//	@Modifying
//	@Query("UPDATE Durable SET Durable_name = ?1,Durable_number=?2,Durable_brandname=?3"
//			+ ",Durable_model=?4,Durable_price=?5,Durable_statusnow=?6,Responsible_person=?7"
//			+ ",Durable_entrancedate=?8,Durable_Borrow_Status=?9,room_number=?10,note=?11 WHERE Durable_code = ?12")
//	int UpdateDurableData(String Durable_name, String Durable_number, String Durable_brandname, String Durable_model,
//			String Durable_price, String Durable_statusnow, String Responsible_person, String Durable_entrancedate,
//			String Durable_Borrow_Status, String room_number,String note, String durablecode);

	@Transactional
	@Modifying
	@Query(value = "{call updateDurableData(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12)}", nativeQuery = true)
	int UpdateDurableData(String Durable_name, String Durable_number, String Durable_brandname, String Durable_model,
			String Durable_price, String Durable_statusnow, String Responsible_person, String Durable_entrancedate,
			String Durable_Borrow_Status, String room_number, String note, String durablecode);

	@Transactional
	@Modifying
	@Query(value = "{call update_durable(?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11, ?12, ?13, ?14)}", nativeQuery = true)
	int update_durable(String durable_code, String durable_borrow_status, String durable_brandname,
			String durable_entranceDate, String image, String model, String name, String number, String price,
			String statusnow, String responsePerson, String note, int id_major, String room_number);

	@Transactional
	@Modifying
	@Query("UPDATE Durable d SET d.Durable_image = ?1 WHERE d.Durable_code = ?2")
	void UpdateDurableImage(String durable_image, String Durablecode);

	//close because execute error
//	@Query(value = "{call durable_DetailByMajor(?1)}", nativeQuery = true)
//	List<Durable> getDurable_DetailByIDmajor(int idmajor);
	
	@Query("From Durable where major.ID_Major = ?1")
	List<Durable> getDurable_DetailByIDmajor(int idmajor);

	@Query(value = "{call Responsibleperson_Detail(?1)}", nativeQuery = true)
	List<String> getResponsibleperson_Detail(int id_major);

	@Query(value = "{call durable_DetailByDurableCode(?1,?2)}", nativeQuery = true)
	Durable getDetailDurableByMajor(String durable, int id_major);
	
	@Query("From Durable where Durable_code = ?1")
	Durable getDurableByCode(String durble_code);

	// Search
	@Query("From Durable where Durable_code = ?1 or Durable_name like %?2% or room.Room_number = ?3")
	List<Durable> getSearchDurable(String durable_code, String durable_name, String room);
//	@Query(value = "{call durable_getSearchDurableDetail(?1, ?2, ?3)}", nativeQuery = true)
//	List<Durable> getSearchDurable(String durable_code, String durable_name, String room);

	@Query("From Durable where Durable_code LIKE ?1% and major.ID_Major = ?2")
	List<Durable> getSearchDurableByDurableCodeNoRoom(String durable_code, int major_id);

	@Query("From Durable where Durable_code LIKE ?1% or room.Room_number = ?2")
	List<Durable> getSearchDurableByDurableCodeAndRoom(String durable_code, String room_number);

	@Query("From Durable where Durable_name LIKE %?1% and major.ID_Major = ?2 ")
	List<Durable> getSearchDurableByNameNoRoom(String durable_name, int major_id);

	@Query("From Durable where Durable_name LIKE %?1% or room.Room_number = ?2 ")
	List<Durable> getSearchDurableByNameAndRoom(String durable_name, String room_number);

	// Not verify durable

	@Query("SELECT d From Durable d left join d.VerifyDurable vd where vd.pk.verify.Years is null or vd.pk.verify.Years not in (?1)")
	List<Durable> getNotVerifyDurableByYear(String years);
	
	@Query("SELECT d From Durable d left join d.VerifyDurable vd where d.room.Room_number = ?2 and vd.pk.verify.Years is null or vd.pk.verify.Years not in (?1)")
	List<Durable> getNotVerifyDurableByYearAndRoom(String years, String room_id);
	
	@Query("SELECT d From Durable d left join d.VerifyDurable vd where  d.major.ID_Major = ?2 and vd.pk.verify.Years is null or vd.pk.verify.Years not in (?1)")
	List<Durable> getNotVerifyDurableByYearAndMajor(String years, int major_id);

	@Query("SELECT d From Durable d left join d.VerifyDurable vd where d.room.Room_number = ?2 and d.major.ID_Major = ?3 and vd.pk.verify.Years is null or vd.pk.verify.Years not in (?1)")
	List<Durable> getNotVerifyDurableByYearAndRoomAndMajor(String years, String room_id, int major_id);
	


	
}
