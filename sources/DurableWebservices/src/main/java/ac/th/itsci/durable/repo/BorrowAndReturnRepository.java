package ac.th.itsci.durable.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Borrowing;

public interface BorrowAndReturnRepository extends CrudRepository<Borrowing, Long> {

	
	@Query(value="{call Borrow_DetailByDurableCode(?1)}", nativeQuery = true)
	Borrowing Borrow_DetailByDurableCode(String idcard);
	
//	@Query(value="{call Borrow_checkstatusBorrow(?1,?2)}", nativeQuery = true)
//	String Borrow_checkstatusBorrow(String durablecode,String idcard);
	
	@Transactional
	@Modifying
	@Query("UPDATE Borrowing SET Borrow_status = ?1, Return_date = ?2 WHERE Durable_code = ?3")
	int UpdateStatusBorrow(String borrow_status,String trturndate,String Durablecode);	
	
	@Query(value="{call Borrow_listBorrow(?1)}", nativeQuery = true)
	List<Borrowing> listborrow(int idmanajor);
	
	@Query(value="{call Borrow_listBorrowByIdcard(?1)}", nativeQuery = true)
	List<Borrowing> listBorrowByIdcard(String idCard);
	
	@Query(value="{call BorrowCheckStatus(?1)}", nativeQuery = true)
	Borrowing checkBorrowStatus(String durableCode);
	
}
