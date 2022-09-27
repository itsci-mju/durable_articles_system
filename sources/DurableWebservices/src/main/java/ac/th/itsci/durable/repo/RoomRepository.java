package ac.th.itsci.durable.repo;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.*;

public interface RoomRepository extends CrudRepository<Room, Long> {
	
	//crash
//	@Query(value="{call room_alldetail()}", nativeQuery = true)
//	List<Room> getRoom();
	
	@Query(value="{call Room_DetailByRoomNumber(?1)}", nativeQuery = true)
	Room RoomDetailByRoomID(String roomID);
	
	
	@Query(value="{call room_getAllRoomByMajor(?1)}", nativeQuery = true)
	List<Room> room_getAllRoomByMajor(int majorid);
	
	//add at 11 july 2020
	@Transactional
	@Modifying
	@Query(value="{call room_addRoom(?1, ?2, ?3, ?4, ?5) }", nativeQuery = true)
	int add_RoomDetail(String room_number, String build, String room_name, String floor,int id_major);
	
	@Query("From Room")
	List<Room> getAllRoom();
	
	//add at 12 july 2020
	@Query("select d.room.Room_number from Durable d where d.major.ID_Major = ?1 and d.room.Room_number IS NOT NULL")
	List<String> RoomAllByIdmajor(int major_id);

	//add at 13 july 2020
	@Query("From Durable d where d.room.Room_number = ?1")
	List<Room> durable_by_room_number(String room_number);
	
	@Query("From Room where major.ID_Major = ?1")
	List<Room> get_allRoomByMajor(int major_id);

	
	
}
