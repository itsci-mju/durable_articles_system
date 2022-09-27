package ac.th.itsci.durable.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ac.th.itsci.durable.entity.*;
import java.util.*;

import javax.transaction.Transactional;
public interface CompanyRepository  extends CrudRepository<Company, Long> {
	
	@Query("Select count(company_id) From Company")
	int get_countCompanyID();
	
	@Query("Select MAX(company_id) From Company")
	int get_lastestCompanyID();
	
	@Query("Select companyname from Company")
	List<String> get_allCompany();
	
	@Query("From Company")
	List<Company> list_company();
	
	@Transactional
	@Modifying
	@Query(value="{call update_company(?1, ?2, ?3, ?4)}", nativeQuery = true)
	int update_company(int company_id, String companyname, String address, String tell);
	
	@Query("From Company where companyname = ?1")
	Company company_duplicate_check(String company_name);
	
}
