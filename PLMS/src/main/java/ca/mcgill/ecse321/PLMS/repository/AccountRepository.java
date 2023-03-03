package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Account;

public interface AccountRepository extends CrudRepository<Account, String>{

  /**
	 * Find an account by email
	 * @param email
	 * @return Account found
	 */
    public Account findAccountByEmail(String email);


    
}
