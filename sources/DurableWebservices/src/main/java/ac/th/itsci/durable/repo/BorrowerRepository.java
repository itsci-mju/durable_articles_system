package ac.th.itsci.durable.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.Borrower;

public interface BorrowerRepository extends CrudRepository<Borrower, Long> {
	
	@Query(value="{call borrower_Check(?1)}", nativeQuery = true)
	Borrower checkBorrower(String durable);
	
	@Transactional
	@Modifying
	@Query("UPDATE Borrower b SET b.Borrower_picture = ?1 WHERE b.Idcard_borrower = ?2")
	int updateBorrower_picture(String Borrower_picture,String idcard);	
	
}
