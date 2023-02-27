package ca.mcgill.ecse321.PLMS.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.PLMS.model.Account;

public interface AccountRepository extends CrudRepository<Account, String>{

     /**
	 * Find an account by email
	 * @param email
	 * @return Account found
	 */
    Account findAccountByEmail(String email);

    /**
	 * @return all accounts
	 */
    List<Account> findAll();

    /**
	 * Delete the account with the given email
	 * @param email
	 */
    void deleteAccountByEmail(String email);

    
}
